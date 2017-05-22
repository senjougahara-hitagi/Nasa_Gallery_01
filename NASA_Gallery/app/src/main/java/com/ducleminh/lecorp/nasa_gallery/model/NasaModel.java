package com.ducleminh.lecorp.nasa_gallery.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 02/05/2017.
 */
public class NasaModel implements Serializable {
    @SerializedName("date")
    private String mDate;
    @SerializedName("explanation")
    private String mExplanation;
    @SerializedName("hdurl")
    private String mHdUrl;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("media_type")
    private String mType;

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public void setExplanation(String explanation) {
        mExplanation = explanation;
    }

    public String getHdUrl() {
        return mHdUrl;
    }

    public void setHdUrl(String hdUrl) {
        mHdUrl = hdUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
