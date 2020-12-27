package com.ncpi.bank.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Configuration
@ConfigurationProperties("ncpi.mongo")
public class MongoProperties {

    private String url="mongodb://localhost:27017";

    private String database="test";

    /**
     * user and password are needed in case the mongo is password protected ,
     */

    private String user;

    private String password;

    /**
     * Needed for conversion of class into bson directly
     */

    private CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );

    /**
      * Represents preferred replica set members to which a query or command can be sent.
      */
    @NotEmpty
    private String readPreference = "primaryPreferred";

    /**
     * This methods overrides the default MongoClientSettings to the custom one as required by user in case if the
     * variables are not defined takes the default value and return the mongoClientOptions
     *
     * @return MongoClientSettings
     */
    public MongoClientSettings toMongoClientSettings() {
        return MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .readPreference(ReadPreference.secondaryPreferred())
                .applyConnectionString(new ConnectionString(url))
                .build();
    }

    public MongoClientSettings toMongoClientSettingsWithAuthentication() {
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        return MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .credential(credential)
                .readPreference(ReadPreference.secondaryPreferred())
                .applyConnectionString(new ConnectionString(url))
                .build();
    }

}
