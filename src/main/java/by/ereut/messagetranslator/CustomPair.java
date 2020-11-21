package by.ereut.messagetranslator;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.io.Serializable;

@Document
@Data
@AllArgsConstructor
public class CustomPair implements Serializable {
    @Id
    private String clientId;
    @Field
    private Double balance;
}
