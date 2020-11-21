package by.ereut.messagetranslator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.couchbase.CouchbaseContainer;

@Configuration
@EnableCouchbaseRepositories
@Profile("test")
@TestPropertySource("classpath:application-test.properties")
public class CouchBaseTestConfig extends AbstractCouchbaseConfiguration {

    @Value("spring.data.couchbase.bucket-name")
    private String bucketName;

    @Autowired
    CouchbaseContainer couchbaseContainer;

    @Override
    public String getConnectionString() {
        return couchbaseContainer.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseContainer.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseContainer.getPassword();
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

}
