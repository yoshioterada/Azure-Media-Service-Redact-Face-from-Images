/**
 *
 * @author Yoshio Terada
 *
 *         Copyright (c) 2017 Yoshio Terada
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
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
