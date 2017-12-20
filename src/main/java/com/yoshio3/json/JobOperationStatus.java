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
public class JobOperationStatus {
  /*
{
    "odata.metadata":"https://yoshio.restv2.japanwest.media.azure.net/api/$metadata#Operations/@Element",
    "Id":"nb:opid:UUID:04101753-bc8c-4973-a090-fc553f111f58",
    "State":"InProgress",
    "TargetEntityId":"nb:chid:UUID:3076cd20-890d-465f-9a76-661847561cd4",
    "ErrorCode":null,
    "ErrorMessage":null
}
    */  

    @JsonbProperty("odata.metadata")    
    private String metadata;

    @JsonbProperty("Id")        
    private String id;

    @JsonbProperty("State")        
    private String state;

    @JsonbProperty("TargetEntityId")        
    private String targetEntityId;

    @JsonbProperty("ErrorCode")        
    private String errorCode;

    @JsonbProperty("ErrorMessage")            
    private String errorMessage;

    /**
     * @return the metadata
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

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
     * @return the targetEntityId
     */
    public String getTargetEntityId() {
        return targetEntityId;
    }

    /**
     * @param targetEntityId the targetEntityId to set
     */
    public void setTargetEntityId(String targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    }
