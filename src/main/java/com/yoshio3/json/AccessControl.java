/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;


public class AccessControl {
    @JsonbProperty("IP")
    private Ip ip;

    public Ip getIp() { return ip; }
    public void setIp(Ip value) { this.ip = value; }
}