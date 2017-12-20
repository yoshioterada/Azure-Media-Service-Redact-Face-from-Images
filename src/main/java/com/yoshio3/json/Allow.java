/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import javax.json.bind.annotation.JsonbProperty;

public class Allow {
    @JsonbProperty("Name")
    private String name;
    @JsonbProperty("Address")
    private String address;
    @JsonbProperty("SubnetPrefixLength")
    private long subnetPrefixLength;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public long getSubnetPrefixLength() { return subnetPrefixLength; }
    public void setSubnetPrefixLength(long value) { this.subnetPrefixLength = value; }
}