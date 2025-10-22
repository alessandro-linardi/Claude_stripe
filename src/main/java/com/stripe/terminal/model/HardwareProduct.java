package com.stripe.terminal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Terminal Hardware Product.
 */
public class HardwareProduct {
    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("status")
    private String status; // "available" or "unavailable"

    @SerializedName("unavailable_after")
    private Long unavailableAfter; // Unix timestamp

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUnavailableAfter() {
        return unavailableAfter;
    }

    public void setUnavailableAfter(Long unavailableAfter) {
        this.unavailableAfter = unavailableAfter;
    }

    @Override
    public String toString() {
        return "HardwareProduct{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", unavailableAfter=" + unavailableAfter +
                '}';
    }
}
