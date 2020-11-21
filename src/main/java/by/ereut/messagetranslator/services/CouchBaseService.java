package by.ereut.messagetranslator.services;

import by.ereut.messagetranslator.CustomPair;
import by.ereut.messagetranslator.CustomPairService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
@Profile({"test", "dev"})
@PropertySource("classpath:application-dev.properties")
public class CouchBaseService {

    @Value("${application.request.url}")
    private String requestUrl;

    @Value("${resources.xls-file-path}")
    private String xlsFilePath;

    private final CustomPairService service;

    @Autowired
    public CouchBaseService(CustomPairService service) {
        this.service = service;
    }

    private InputStream sendHttpRequest() throws IOException {
        HttpResponse httpResponse;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl);
        httpGet.addHeader("Accept", "application/xml");
        httpResponse = httpClient.execute(httpGet);
        log.info("Resplonse from app #1 was successfully received");
        return httpResponse.getEntity().getContent();
    }

    private String convertHttpResponseToXmlCustomPairs() throws TransformerException, IOException {
        TransformerFactory fac = TransformerFactory.newInstance();
        Source xslt= new StreamSource(new File(xlsFilePath));
        StreamSource xmlSource = new StreamSource(sendHttpRequest());
        Transformer trans = fac.newTransformer(xslt);
        StringWriter writer = new StringWriter();
        StreamResult sr = new StreamResult(writer);
        trans.transform(xmlSource, sr);
        return writer.toString();
    }

    @Scheduled(initialDelayString = "${scheduler.fixed-delay.http}",
            fixedDelayString = "${scheduler.fixed-delay.http}")
    public void saveToCouchbase () {

        try {
            String source = convertHttpResponseToXmlCustomPairs();
            String processedSource =
                    source.substring(source.indexOf(">") + 1).trim();
            if (processedSource.isEmpty()) {
                return;
            }
            log.info("Data was successfully translated with XSLT");
            service.saveAll(
                    Arrays.stream(processedSource.split("\n")).map(
                            item -> new CustomPair(item.split("/")[0].trim(),
                                    new Double(item.split("/")[1].trim()))).
                            collect(Collectors.toList())
            );
            log.info("Custom pairs was successfully saved in Couchbase database");
        } catch (TransformerException e) {
            log.warn("Failed to transform xml with xslt " + e.getMessage());
        } catch (IOException e) {
            log.warn("Failed to execute request to Application #1 " + e.getMessage());
        }
    }

}
