package com.example.hospitalapi.shared.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Error response DTO that follows the standardized format:
 * {
 *   "error": {
 *     "code": "ERROR_CODE",
 *     "message": "Error message",
 *     "details": [
 *       { "field": "fieldName", "issue": "Error description" }
 *     ],
 *     "timestamp": "2025-04-27T06:00:00Z",
 *     "traceId": "a3b4c5d6e7f8"
 *   }
 * }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private ErrorData error;

    public ErrorResponse() {
        this.error = new ErrorData();
    }

    public ErrorResponse(String code, String message) {
        this.error = new ErrorData(code, message);
    }

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }

    /**
     * Inner class representing the error data
     */
    public static class ErrorData {
        private String code;
        private String message;
        private List<ErrorDetail> details;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime timestamp;

        private String traceId;

        public ErrorData() {
            this.timestamp = LocalDateTime.now();
            this.traceId = UUID.randomUUID().toString().substring(0, 8);
            this.details = new ArrayList<>();
        }

        public ErrorData(String code, String message) {
            this();
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ErrorDetail> getDetails() {
            return details;
        }

        public void setDetails(List<ErrorDetail> details) {
            this.details = details;
        }

        public void addDetail(String field, String issue) {
            this.details.add(new ErrorDetail(field, issue));
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }
    }

    /**
     * Inner class representing an error detail
     */
    public static class ErrorDetail {
        private String field;
        private String issue;

        public ErrorDetail() {
        }

        public ErrorDetail(String field, String issue) {
            this.field = field;
            this.issue = issue;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }
    }
}
