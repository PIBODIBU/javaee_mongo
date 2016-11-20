package model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerInfoModel {
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String collectionName;

    public ServerInfoModel(
            String dbHost,
            String dbPort,
            String dbName,
            String collectionName
    ) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.collectionName = collectionName;
    }

    @JsonProperty("db_host")
    public String getDbHost() {
        return dbHost;
    }

    @JsonProperty("db_port")
    public String getDbPort() {
        return dbPort;
    }

    @JsonProperty("db_name")
    public String getDbName() {
        return dbName;
    }

    @JsonProperty("collection_name")
    public String getCollectionName() {
        return collectionName;
    }
}
