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
public class CrossSiteAccessPolicies {
    @JsonbProperty("ClientAccessPolicy")
    private String clientAccessPolicy;
    @JsonbProperty("CrossDomainPolicy")
    private String crossDomainPolicy;

    /**
     * @return the clientAccessPolicy
     */
    public String getClientAccessPolicy() {
        return clientAccessPolicy;
    }

    /**
     * @param clientAccessPolicy the clientAccessPolicy to set
     */
    public void setClientAccessPolicy(String clientAccessPolicy) {
        this.clientAccessPolicy = clientAccessPolicy;
    }

    /**
     * @return the crossDomainPolicy
     */
    public String getCrossDomainPolicy() {
        return crossDomainPolicy;
    }

    /**
     * @param crossDomainPolicy the crossDomainPolicy to set
     */
    public void setCrossDomainPolicy(String crossDomainPolicy) {
        this.crossDomainPolicy = crossDomainPolicy;
    }
    
}
