package org.keycloak.quickstart.jaxrs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;




/**
 * 
 * Oauth token representation of JSON request
 * 
 * @author <a href="nmajorov@redhat.com">Nikolaj Majorov</a>
 *
 */
@XmlRootElement
public class AccessToken {

    private int statusCode;

    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private int refreshExpiresIn;
    private String refreshToken;
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    @XmlElement(name="access_token")
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    @XmlElement(name="token_type")
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    @XmlElement(name="expires_in")
    public int getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
    
    @XmlElement(name="refresh_expires_in")
    public int getRefreshExpiresIn() {
        return refreshExpiresIn;
    }
    
    public void setRefreshExpiresIn(int refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }
    
    @XmlElement(name="refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
