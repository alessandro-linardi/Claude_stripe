package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Generic class for paginated Stripe API list responses.
 */
public class StripeList<T> {
    @SerializedName("object")
    private String object;

    @SerializedName("data")
    private List<T> data;

    @SerializedName("has_more")
    private Boolean hasMore;

    @SerializedName("url")
    private String url;

    // Getters and Setters
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StripeList{" +
                "object='" + object + '\'' +
                ", data=" + data +
                ", hasMore=" + hasMore +
                ", url='" + url + '\'' +
                '}';
    }
}
