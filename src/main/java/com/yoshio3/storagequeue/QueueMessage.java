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
package com.yoshio3.storagequeue;

import java.util.Date;
import java.util.Objects;
import javax.json.bind.annotation.JsonbProperty;

public class QueueMessage {
    //"MessageVersion":"1.0"
    @JsonbProperty("MessageVersion")
    private String messageVersion;
    //"EventType":"NotificationEndPointRegistration"
    @JsonbProperty("EventType")
    private String eventType;
    //"ETag":"9f942202f5224e4876ea0072f30f650b9f4c246b40f4048d0e94732d327322e9",    
    @JsonbProperty("ETag")
    private String eTag;
    //"TimeStamp":"2017-12-07T13:46:37",
    @JsonbProperty("TimeStamp")
    private Date timeStamp;
    @JsonbProperty("Properties")
    private Properties properties;

    /**
     * @return the messageVersion
     */
    public String getMessageVersion() {
        return messageVersion;
    }

    /**
     * @param messageVersion the messageVersion to set
     */
    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eTag
     */
    public String geteTag() {
        return eTag;
    }

    /**
     * @param eTag the eTag to set
     */
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.messageVersion);
        hash = 71 * hash + Objects.hashCode(this.timeStamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueueMessage other = (QueueMessage) obj;
        if (!Objects.equals(this.messageVersion, other.messageVersion)) {
            return false;
        }
        return Objects.equals(this.timeStamp, other.timeStamp);
    }

    @Override
    public String toString() {
        return "QueueMessage{" + "messageVersion=" + messageVersion + ", eventType=" + eventType + ", eTag=" + eTag + ", timeStamp=" + timeStamp + ", properties=[" + properties.toString() + "]}";
    }
}
