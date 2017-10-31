package org.keycloak.quickstart.jaxrs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Message {

    private String message;

    public Message() {
        //this.message = message;
    }

   @XmlElement
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Message [message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }

}
