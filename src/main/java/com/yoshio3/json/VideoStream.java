/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class VideoStream {
    @JsonbProperty("Index")            
    private long index;
    @JsonbProperty("Name")            
    private String name;

    public long getIndex() { return index; }
    public void setIndex(long value) { this.index = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}