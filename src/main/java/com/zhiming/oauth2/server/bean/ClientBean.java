package com.zhiming.oauth2.server.bean;

/**
 * Created by 58 on 2016/8/21.
 */
public class ClientBean {

    private int id;
    private String clientId;
    private String clientName;
    private String client_secret;

    public int getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
