package org.keycloak.quickstart.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MessageProvider implements MessageBodyReader<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProvider.class);   
    
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        return true;
    }

    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {

       
        try {
            JAXBContext jc = JAXBContext.newInstance(Message.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            // unmarshaller.setProperty("javax.xml.bind.context.factory","org.eclipse.persistence.jaxb.JAXBContextFactory");
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");

            // Set it to true if you need to include the JSON root element in
            // the
            // JSON input
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, "@");
            StreamSource streamSource = new StreamSource(entityStream);
         

            Message message =  unmarshaller.unmarshal(streamSource, Message.class).getValue();
          

            return message;
        } catch (JAXBException e) {
            LOG.error("can't unmarshall response",e);
            throw new RuntimeException("can't unmarshall response");
        }
        
    }

}
