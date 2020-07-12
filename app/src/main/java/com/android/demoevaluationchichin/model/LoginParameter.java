package com.android.demoevaluationchichin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginParameter {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("content")
    @Expose
    private Content content;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}
