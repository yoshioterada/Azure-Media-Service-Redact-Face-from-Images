/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class Input {
    @JsonbProperty("StreamingProtocol")    
    private String streamingProtocol;
    @JsonbProperty("AccessControl")    
    private AccessControl accessControl;
    @JsonbProperty("Endpoints")    
    private Endpoints[] endpoints;

    @JsonbProperty("KeyFrameInterval")        
    private String keyFrameInterval;
    
    public String getStreamingProtocol() { return streamingProtocol; }
    public void setStreamingProtocol(String value) { this.streamingProtocol = value; }

    public AccessControl getAccessControl() { return accessControl; }
    public void setAccessControl(AccessControl value) { this.accessControl = value; }

    public Endpoints[] getEndpoints() { return endpoints; }
    public void setEndpoints(Endpoints[] value) { this.endpoints = value; }

    /**
     * @return the keyFrameInterval
     */
    public String getKeyFrameInterval() {
        return keyFrameInterval;
    }

    /**
     * @param keyFrameInterval the keyFrameInterval to set
     */
    public void setKeyFrameInterval(String keyFrameInterval) {
        this.keyFrameInterval = keyFrameInterval;
    }
}