/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class Slate {

    @JsonbProperty("InsertOnAdMarker")            
    private boolean insertOnAdMarker;
    @JsonbProperty("DefaultSlateAssetId")            
    private String defaultSlateAssetId;

    public boolean getInsertOnAdMarker() { return insertOnAdMarker; }
    public void setInsertOnAdMarker(boolean value) { this.insertOnAdMarker = value; }

    public String getDefaultSlateAssetId() { return defaultSlateAssetId; }
    public void setDefaultSlateAssetId(String value) { this.defaultSlateAssetId = value; }
}
