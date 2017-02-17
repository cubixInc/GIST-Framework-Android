package com.cubix.gist.models;

/**
 * Created by imran.pyarali on 5/24/2016.
 */
public class SyncResourceModel {

    private String updated_at;
    private ResourceModel sync_data;

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public ResourceModel getSyncData() {
        return sync_data;
    }

    public void setSyncData(ResourceModel sync_data) {
        this.sync_data = sync_data;
    }
}
