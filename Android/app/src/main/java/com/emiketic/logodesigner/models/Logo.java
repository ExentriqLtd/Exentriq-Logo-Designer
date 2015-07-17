package com.emiketic.logodesigner.models;

/**
 * Created by stoufa on 08/07/15.
 */
public class Logo {

    private int resourceId;
    private String tag;

    public Logo(int resourceId, String tag) {
        this.resourceId = resourceId;
        this.tag = tag;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
