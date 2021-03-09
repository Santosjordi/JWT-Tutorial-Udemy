package com.jwt.tutorial.domain;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int httpStatusCode; // 200, 404, 500
    private HttpStatus httpStatus; // enum with statuses
    private String reason; // phrases from httpStatus
    private String message;

    public HttpResponse(){}

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}