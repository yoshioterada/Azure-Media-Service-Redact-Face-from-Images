/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class Preview {
    @JsonbProperty("AccessControl")        
    private AccessControl accessControl;
    @JsonbProperty("Endpoints")        
    private Endpoints[] endpoints;

    public AccessControl getAccessControl() { return accessControl; }
    public void setAccessControl(AccessControl value) { this.accessControl = value; }

    public Endpoints[] getEndpoints() { return endpoints; }
    public void setEndpoints(Endpoints[] value) { this.endpoints = value; }
}