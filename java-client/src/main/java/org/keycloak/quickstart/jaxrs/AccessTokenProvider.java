package org.keycloak.quickstart.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * jaxb provider to map form JSON response to the AcessToken class
 * 
 * @author <a href="nmajorov@redhat.com">Nikolaj Majorov</a>
 *
 */
public class AccessTokenProvider  implements MessageBodyReader<AccessToken>{

    private static final Logger LOG = LoggerFactory.getLogger(AccessTokenProvider.class);
    
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
                return true;
    }

    public AccessToken readFrom(Class<AccessToken> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        try {
            JAXBContext jc = JAXBContext.newInstance(AccessToken.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");

            // Set it to true if you need to include the JSON root element in
            // the
            // JSON input
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            
            StreamSource streamSource = new StreamSource(entityStream);
         

            AccessToken accessToken =  unmarshaller.unmarshal(streamSource, AccessToken.class).getValue();
          

            return accessToken;
        } catch (JAXBException e) {
            LOG.error("can't unmarshall response",e);
            throw new RuntimeException("can't unmarshall response");
        }
       
    }

}
