package com.github.qingquanlv.testflow_service_api.entity;

public class Status {

    /**
     * feature Id
     */
    private Boolean success;

    /**
     * error code
     */
    private int errorCode;

    /**
     * messge
     */
    private String message;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
