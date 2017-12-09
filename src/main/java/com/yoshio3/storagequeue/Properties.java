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

public class Properties {

    //"NotificationEndPointId":"nb:nepid:UUID:1d611083-c026-4db0-9028-88e5784a2806",
    @JsonbProperty("NotificationEndPointId")
    private String notificationEndPointId;
    //"State":"Registered",
    @JsonbProperty("State")
    private String state;
    //"Name":"9a690ffc-18de-4e53-9cf2-6710fc5ee33d",
    @JsonbProperty("Name")
    private String name;
    //"Created":"2017-12-07T13:46:36",
    @JsonbProperty("Created")
    private Date created;
    //"AccountId":"7a74c06b-ae2d-4a7f-bb30-33155dde238c"
    @JsonbProperty("AccountId")
    private String accountId;
    //"JobId":"nb:jid:UUID:3ec35bff-0300-80c0-ce26-f1e7db553a95",
    @JsonbProperty("JobId")
    private String jobId;
    //"JobName":"Indexing Job for redacted_face_Microsoft-Hololens-Japan.mp4",
    @JsonbProperty("JobName")
    private String jobName;
    //"NewState":"Scheduled",
    @JsonbProperty("NewState")
    private String newState;
    //"OldState":"Queued",
    @JsonbProperty("OldState")
    private String oldState;
    //"AccountName":"yoterada",
    @JsonbProperty("AccountName")
    private String accountName;

    /**
     * @return the notificationEndPointId
     */
    public String getNotificationEndPointId() {
        return notificationEndPointId;
    }

    /**
     * @param notificationEndPointId the notificationEndPointId to set
     */
    public void setNotificationEndPointId(String notificationEndPointId) {
        this.notificationEndPointId = notificationEndPointId;
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
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return the jobName
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return the newState
     */
    public String getNewState() {
        return newState;
    }

    /**
     * @param newState the newState to set
     */
    public void setNewState(String newState) {
        this.newState = newState;
    }

    /**
     * @return the oldState
     */
    public String getOldState() {
        return oldState;
    }

    /**
     * @param oldState the oldState to set
     */
    public void setOldState(String oldState) {
        this.oldState = oldState;
    }

    /**
     * @return the accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @param accountName the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.notificationEndPointId);
        hash = 89 * hash + Objects.hashCode(this.accountId);
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
        final Properties other = (Properties) obj;
        if (!Objects.equals(this.notificationEndPointId, other.notificationEndPointId)) {
            return false;
        }
        return Objects.equals(this.accountId, other.accountId);
    }

    @Override
    public String toString() {
        return "Properties{" + "notificationEndPointId=" + notificationEndPointId + ", state=" + state + ", name=" + name + ", created=" + created + ", accountId=" + accountId + ", jobId=" + jobId + ", jobName=" + jobName + ", newState=" + newState + ", oldState=" + oldState + ", accountName=" + accountName + '}';
    }
}
