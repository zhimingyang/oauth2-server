package com.zhiming.oauth2.server.service;

public interface OAuthService {

    public boolean checkClientId(String clientId);

    public void addAuthCode(String uname,String authCode);

    public boolean checkClientSecret(String clientSecret,String clientId);

    public boolean checkAuthCode(String authcode);

    public void addAccessToken(String token,String uname);

    public boolean checkAccessToken(String token);

}
