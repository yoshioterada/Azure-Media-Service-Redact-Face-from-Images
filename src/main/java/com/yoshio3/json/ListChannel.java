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

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author yoterada
 */
public class ListChannel {
    //{
    //"odata.metadata":"https://yoshio.restv2.japanwest.media.azure.net/api/$metadata#Channels",
    //"value":[{"Id":"nb:chid:UUID:6b77b227-8d54-40b3-aafc-2fd78778fc8a","Name":"test","Description":"test","Created":"2017-12-18T08:35:29.5358674Z","LastModified":"2017-12-18T08:35:29.5358674Z","State":"Stopped","Input":{"KeyFrameInterval":null,"StreamingProtocol":"RTMP","AccessControl":{"IP":{"Allow":[{"Name":"\u65e2\u5b9a","Address":"0.0.0.0","SubnetPrefixLength":0}]}},"Endpoints":[{"Protocol":"RTMP","Url":"rtmp://test-yoshio.channel.mediaservices.windows.net:1935/live/d92010534b49423b9a19bc3a78c65f89"},{"Protocol":"RTMP","Url":"rtmp://test-yoshio.channel.mediaservices.windows.net:1936/live/d92010534b49423b9a19bc3a78c65f89"}]},"Preview":{"AccessControl":{"IP":{"Allow":[{"Name":"\u65e2\u5b9a","Address":"0.0.0.0","SubnetPrefixLength":0}]}},"Endpoints":[{"Protocol":"FragmentedMP4","Url":"http://test-yoshio.channel.mediaservices.windows.net/preview.isml/manifest"}]},"Output":null,"CrossSiteAccessPolicies":{"ClientAccessPolicy":null,"CrossDomainPolicy":null},"EncodingType":"None","Encoding":null,"Slate":null,"VanityUrl":true}]}
    @JsonbProperty("odata.metadata")    
    private String metadata;
    
    private List<ChannelValue> value;

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
     * @return the value
     */
    public List<ChannelValue> getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(List<ChannelValue> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ListChannel{" + "metadata=" + metadata + ", value=" + value + '}';
    }
 }
