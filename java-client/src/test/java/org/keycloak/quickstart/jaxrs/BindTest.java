package org.keycloak.quickstart.jaxrs;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * test jaxb json binding
 * 
 * @author <a href="nmajorov@redhat.com">Nikolaj Majorov</a>
 *
 */
public class BindTest {

    private static final Logger LOG = LoggerFactory.getLogger(BindTest.class);
    
    @Test
    public void testUnmarschallingMessage() throws Exception{
        JAXBContext jc = JAXBContext.newInstance(Message.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");

        // Set it to true if you need to include the JSON root element in
        // the
        // JSON input
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        String json = "{\"message\":\"success\"}";
        StreamSource stream = 
                new StreamSource(new StringReader(json));

        Message message = unmarshaller.unmarshal(stream, Message.class).getValue();
        Assert.assertNotNull(message);
        Assert.assertTrue(message.getMessage().equals("success"));
    }

    
    @Test
    public void testMarschallingMessage() throws Exception {
      
        JAXBContext jc = JAXBContext.newInstance(Message.class);
        
        // Create the Marshaller Object using the JaxB Context
        Marshaller marshaller = jc.createMarshaller();
        
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT,false);
        Message message = new Message();
        message.setMessage("success");
        // Marshal the employee object to JSON and print the output to console
        marshaller.marshal(message, System.out);
        
    }
    
    
    /**
     * test unmarschalling of AccessToken
     * 
     * @throws Exception
     */
    @Test 
    public void testUnmarschallingAccessToken() throws Exception {
        JAXBContext jc = JAXBContext.newInstance(AccessToken.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");

        // Set it to true if you need to include the JSON root element in
        // the
        // JSON input
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        String json = "{\"access_token\":\"eyJhbGciOiJSU\"" 
        
        + " ,\"expires_in\":60,\"refresh_expires_in\":600," +
        "\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lk\"," +
                "\"not-before-policy\":0,\"session_state\":\"a1f9f252-ff0f-4a45-bb9c-bec019eab79a\"}";
        
        LOG.info("ummarshall token:" + json);
        StreamSource stream = 
                new StreamSource(new StringReader(json));
        
        AccessToken accessToken = unmarshaller.unmarshal(stream, AccessToken.class).getValue();
        Assert.assertNotNull(accessToken);
    }
    
    @Test @Ignore
    public void testMarschallAcessToken() throws Exception {
      
        JAXBContext jc = JAXBContext.newInstance(AccessToken.class);
        
        // Create the Marshaller Object using the JaxB Context
        Marshaller marshaller = jc.createMarshaller();
        
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT,false);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken("eyJhbGciOiJSU");
        accessToken.setExpiresIn(600);
        accessToken.setExpiresIn(60);
        accessToken.setRefreshToken("_dqAGxefbg0u58JAkz4nBkNE");
        accessToken.setTokenType("token_type");
        
        // Marshal the employee object to JSON and print the output to console
        marshaller.marshal(accessToken, System.out);
        
    }
}
