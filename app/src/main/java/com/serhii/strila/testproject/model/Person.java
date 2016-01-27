package com.serhii.strila.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Serhii Strila on 1/26/16
 */
public class Person extends RealmObject {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("status")
    @Expose
    private String status;

    public enum Status {
        NONE,
        LIKE,
        HATE,
        REMOVED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
