package by.ereut.messagetranslator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@TestPropertySource("classpath:application-test.properties")
public class TestConfig {

    @Value("spring.data.couchbase.bucket-name")
    private String bucketName;

    @Value("spring.couchbase.docker-image-name")
    private String dockerImageName;

    @Profile("test")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public CouchbaseContainer container() {
        return new CouchbaseContainer(DockerImageName.parse("couchbase/server"))
                .withBucket(new BucketDefinition(bucketName));
    }





}
