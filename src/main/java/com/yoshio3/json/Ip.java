/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;


public class Ip {
    @JsonbProperty("Allow")    
    private List<Allow> allow;

    public List<Allow> getAllow() { return allow; }
    public void setAllow(List<Allow> value) { this.allow = value; }
}

