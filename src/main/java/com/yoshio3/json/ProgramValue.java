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
public class ProgramValue {
   /*
            "Id":"nb:pgid:UUID:0360e75c-996f-4789-af7c-b1a4f8c4a009",
            "Name":"generateFromJavaViaRESTAPI-20171219-151335",
            "Description":"",
            "Created":"2017-12-19T06:13:38.3046753Z",
            "LastModified":"2017-12-19T06:13:38.3046753Z",
            "ChannelId":"nb:chid:UUID:3076cd20-890d-465f-9a76-661847561cd4",
            "AssetId":"nb:cid:UUID:654e8ca5-bc2a-4234-aa85-4b9af7158b1a",
            "ArchiveWindowLength":"PT1H",
            "State":"Stopped",
            "ManifestName":"cc4b1d7b-0274-4a5f-8199-45ad1b6c73bd"
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

    @JsonbProperty("ChannelId")
    private String channelId;

    @JsonbProperty("AssetId")
    private String assetId;

    @JsonbProperty("ArchiveWindowLength")
    private String archiveWindowLength;

    @JsonbProperty("State")
    private String state;

    @JsonbProperty("ManifestName")
    private String manifestName;

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
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the assetId
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the archiveWindowLength
     */
    public String getArchiveWindowLength() {
        return archiveWindowLength;
    }

    /**
     * @param archiveWindowLength the archiveWindowLength to set
     */
    public void setArchiveWindowLength(String archiveWindowLength) {
        this.archiveWindowLength = archiveWindowLength;
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
     * @return the manifestName
     */
    public String getManifestName() {
        return manifestName;
    }

    /**
     * @param manifestName the manifestName to set
     */
    public void setManifestName(String manifestName) {
        this.manifestName = manifestName;
    }

    
    
    }
