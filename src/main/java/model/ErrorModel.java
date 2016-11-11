package model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorModel {
    private boolean errorStatus;

    public ErrorModel(boolean errorStatus) {
        this.errorStatus = errorStatus;
    }

    @JsonProperty("error")
    public boolean isErrorStatus() {
        return errorStatus;
    }
}
