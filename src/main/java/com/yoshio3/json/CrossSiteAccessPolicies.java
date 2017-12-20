/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author yoterada
 */
public class CrossSiteAccessPolicies {
    @JsonbProperty("ClientAccessPolicy")
    private String clientAccessPolicy;
    @JsonbProperty("CrossDomainPolicy")
    private String crossDomainPolicy;

    /**
     * @return the clientAccessPolicy
     */
    public String getClientAccessPolicy() {
        return clientAccessPolicy;
    }

    /**
     * @param clientAccessPolicy the clientAccessPolicy to set
     */
    public void setClientAccessPolicy(String clientAccessPolicy) {
        this.clientAccessPolicy = clientAccessPolicy;
    }

    /**
     * @return the crossDomainPolicy
     */
    public String getCrossDomainPolicy() {
        return crossDomainPolicy;
    }

    /**
     * @param crossDomainPolicy the crossDomainPolicy to set
     */
    public void setCrossDomainPolicy(String crossDomainPolicy) {
        this.crossDomainPolicy = crossDomainPolicy;
    }
    
}
