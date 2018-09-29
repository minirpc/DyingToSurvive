package com.dyingtosurvive.rpcinterface.model;

import java.util.Date;

/**
 * trace log实体
 * Created by change-solider on 2018-9-18.
 */
public class TraceLog {
    private String requestUrl;
    private Object requestBody;
    private Object responseBody;
    private Date requestTimestamp;
    private Date responseTimestamp;
    private Long requestDuration;
    private String responseCode;
    private String requestId;
    private String fromProject;
    private String toProject;
    private String traceId;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public Date getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(Date responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public Long getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(Long requestDuration) {
        this.requestDuration = requestDuration;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getFromProject() {
        return fromProject;
    }

    public void setFromProject(String fromProject) {
        this.fromProject = fromProject;
    }

    public String getToProject() {
        return toProject;
    }

    public void setToProject(String toProject) {
        this.toProject = toProject;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override public String toString() {
        return "TraceLog{" +
            "requestUrl='" + requestUrl + '\'' +
            ", requestBody=" + requestBody +
            ", responseBody=" + responseBody +
            ", requestTimestamp=" + requestTimestamp +
            ", responseTimestamp=" + responseTimestamp +
            ", requestDuration=" + requestDuration +
            ", responseCode='" + responseCode + '\'' +
            ", requestId='" + requestId + '\'' +
            ", fromProject='" + fromProject + '\'' +
            ", toProject='" + toProject + '\'' +
            ", traceId='" + traceId + '\'' +
            '}';
    }
}
