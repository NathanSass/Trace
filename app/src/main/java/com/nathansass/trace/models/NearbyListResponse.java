package com.nathansass.trace.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
public class NearbyListResponse {
    private List<NearbyListData> data = new ArrayList<>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The data
     */
    public List<NearbyListData> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<NearbyListData> data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
