/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoshio3.json;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author yoterada
 */
public class ListPrograms {
    @JsonbProperty("odata.metadata")    
    private String metadata;

    private List<ProgramValue> value;

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
    public List<ProgramValue> getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(List<ProgramValue> value) {
        this.value = value;
    }

}
