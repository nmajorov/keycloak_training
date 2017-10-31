package ch.redhat.keycloak;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.quickstart.jaxrs.AccessToken;
import org.keycloak.quickstart.jaxrs.Message;
import org.keycloak.quickstart.jaxrs.MessageProvider;

/**
 * 
 * Client for simple RESTful communication over keycloak sso server
 *
 *
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    final static String CLIENT_ID="app-html5";
    
    public  static final void main (String[] args) throws Exception {
        
         
        
        String TOKEN_URL="http://10.42.0.5:8080/auth/realms/quickstart/protocol/openid-connect/token";
        //String TOKEN_URL="http://127.0.0.1:8081/auth/realms/quickstart/protocol/openid-connect/token";
        String url = args[0];
        

        LOG.info("run calls to server url: " + url);
        
        
        if (url== null || url.isEmpty()){
              throw new RuntimeException("server url is not specified.");
        }
        
        Client client = new ResteasyClientBuilder().register(MessageProvider.class).build();
        Invocation.Builder request = client.target(url).request();
        
        if (url.contains("secured")){
            OpenIDClient openIDclient = new OpenIDClient(TOKEN_URL,CLIENT_ID);
            AccessToken accessToken = openIDclient.getAccessToken("alice", "alice");
            String token = accessToken.getAccessToken();
            request.header("Authorization", "Bearer " +token);
        }
        
        
        Response response =request.get();
        int status = response.getStatus();
        LOG.info("response status: "  + status);
        
        LOG.info("response headers:");
        MultivaluedMap<String, Object> headers = response.getHeaders();
        
        for (Iterator<String> iterator = headers.keySet().iterator(); iterator.hasNext();) {
            String key= iterator.next();
            LOG.info(key + ": " + headers.get(key));
            
        }
        
        
        if (status == 200) {
            Message message = response.readEntity(Message.class);
            LOG.info("got response: "  + message);
            
        }
        
        response.close();
       
    }

    
    /**
    private OAuthClient.AccessTokenResponse retrieveAccessToken() {
        OAuthClient oauth = new OAuthClient(driver);
        oauth.doLogin("test-user@localhost", "password");
        String code = oauth.getCurrentQuery().get(OAuth2Constants.CODE);
        OAuthClient.AccessTokenResponse response = oauth.doAccessTokenRequest(code, "password");
        Assert.assertEquals(200, response.getStatusCode());
        return response;
    }
    **/

}
