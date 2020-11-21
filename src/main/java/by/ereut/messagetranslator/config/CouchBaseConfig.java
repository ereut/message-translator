package by.ereut.messagetranslator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.connection-string}")
    private String connectionStringProperty;
    @Value("${spring.couchbase.username}")
    private String userName;
    @Value("${spring.couchbase.password}")
    private String password;
    @Value("${spring.data.couchbase.bucket-name}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return connectionStringProperty;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

}
