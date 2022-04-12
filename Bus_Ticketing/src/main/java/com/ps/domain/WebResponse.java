package com.ps.domain;

public class WebResponse<T> {

    private boolean ok;

    private String message;

    private T responseObject;

    public WebResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
        this.responseObject = null;
    }

    public WebResponse(boolean ok, String message, T responseObject) {
        this.ok = ok;
        this.message = message;
        this.responseObject = responseObject;
    }

    @Override
    public String toString() {
        return "WebResponse{" +
                "ok=" + ok +
                ", message='" + message + '\'' +
                ", responseObject=" + responseObject +
                '}';
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }
}
