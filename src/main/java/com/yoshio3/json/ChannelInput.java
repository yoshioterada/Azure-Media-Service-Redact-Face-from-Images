/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import java.util.Date;
import javax.json.bind.annotation.JsonbProperty;

// ChannelInput.java
public class ChannelInput {

    @JsonbProperty("Id")
    private String id;
    @JsonbProperty("Name")
    private String name;
    @JsonbProperty("Description")
    private String description;
    @JsonbProperty("EncoderType")
    private String encoderType;
    @JsonbProperty("Created")
    private String created;
    @JsonbProperty("LastModified")
    private String lastModified;
    @JsonbProperty("State")
    private String state;
    @JsonbProperty("Input")
    private Input input;
    @JsonbProperty("Encoding")
    private Encoding encoding;
    @JsonbProperty("EncodingType")
    private String encodingType;
    @JsonbProperty("Slate")
    private Slate slate;
    @JsonbProperty("Preview")
    private Preview preview;

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getEncoderType() {
        return encoderType;
    }

    public void setEncoderType(String value) {
        this.encoderType = value;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String value) {
        this.created = value;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String value) {
        this.lastModified = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input value) {
        this.input = value;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding value) {
        this.encoding = value;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String value) {
        this.encodingType = value;
    }

    public Slate getSlate() {
        return slate;
    }

    public void setSlate(Slate value) {
        this.slate = value;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview value) {
        this.preview = value;
    }
}
