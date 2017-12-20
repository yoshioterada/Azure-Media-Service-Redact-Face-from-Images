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

import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author yoterada
 */
public class ChannelValue {

    /*"Id":"nb:chid:UUID:6b77b227-8d54-40b3-aafc-2fd78778fc8a",
        "Name":"test",
        "Description":"test",
        "Created":"2017-12-18T08:35:29.5358674Z",
        "LastModified":"2017-12-18T08:35:29.5358674Z",
        "State":"Stopped",    
     */
    @JsonbProperty("Id")
    private String id;
    @JsonbProperty("Name")
    private String name;

    @JsonbProperty("Description")
    private String description;
    @JsonbProperty("Created")
    private String created;
    @JsonbProperty("LastModified")
    private String lastModified;
    @JsonbProperty("State")
    private String state;
    @JsonbProperty("Input")
    private Input input;
    @JsonbProperty("Output")
    private String output;
    @JsonbProperty("CrossSiteAccessPolicies")
    private CrossSiteAccessPolicies crossSiteAccessPolicies;

    @JsonbProperty("EncodingType")
    private String encodingType;
    @JsonbProperty("Encoding")
    private String encoding;
    @JsonbProperty("Slate")
    private String slate;
    @JsonbProperty("VanityUrl")
    private boolean vanityUrl;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return the lastModified
     */
    public String getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the input
     */
    public Input getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(Input input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the crossSiteAccessPolicies
     */
    public CrossSiteAccessPolicies getCrossSiteAccessPolicies() {
        return crossSiteAccessPolicies;
    }

    /**
     * @param crossSiteAccessPolicies the crossSiteAccessPolicies to set
     */
    public void setCrossSiteAccessPolicies(CrossSiteAccessPolicies crossSiteAccessPolicies) {
        this.crossSiteAccessPolicies = crossSiteAccessPolicies;
    }

    /**
     * @return the encodingType
     */
    public String getEncodingType() {
        return encodingType;
    }

    /**
     * @param encodingType the encodingType to set
     */
    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the slate
     */
    public String getSlate() {
        return slate;
    }

    /**
     * @param slate the slate to set
     */
    public void setSlate(String slate) {
        this.slate = slate;
    }

    /**
     * @return the vanityUrl
     */
    public boolean isVanityUrl() {
        return vanityUrl;
    }

    /**
     * @param vanityUrl the vanityUrl to set
     */
    public void setVanityUrl(boolean vanityUrl) {
        this.vanityUrl = vanityUrl;
    }

    @Override
    public String toString() {
        return "ListValue{" + "id=" + id + ", name=" + name + ", description=" + description + ", created=" + created + ", lastModified=" + lastModified + ", state=" + state + ", input=" + input + ", output=" + output + ", crossSiteAccessPolicies=" + crossSiteAccessPolicies + ", encodingType=" + encodingType + ", encoding=" + encoding + ", slate=" + slate + ", vanityUrl=" + vanityUrl + '}';
    }

}
