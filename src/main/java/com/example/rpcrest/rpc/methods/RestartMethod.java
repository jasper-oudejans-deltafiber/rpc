package com.example.rpcrest.rpc.methods;

import io.swagger.v3.oas.annotations.media.Schema;

public class RestartMethod {
    
    @Schema(description = "Restart request parameters")
    public static class RestartRequest {
        @Schema(description = "Service to restart", example = "system")
        private String service;

        public String getService() { return service; }
        public void setService(String service) { this.service = service; }
    }
    
    @Schema(description = "Restart operation result")
    public static class RestartResponse {
        @Schema(description = "Operation status", example = "restart_initiated")
        private String status;
        
        @Schema(description = "Service being restarted", example = "system")
        private String service;
        
        @Schema(description = "Timestamp of the operation", example = "2023-11-27T10:30:00")
        private String timestamp;
        
        @Schema(description = "Estimated duration for restart", example = "10 seconds")
        private String estimatedDuration;

        public RestartResponse(String status, String service, String timestamp, String estimatedDuration) {
            this.status = status;
            this.service = service;
            this.timestamp = timestamp;
            this.estimatedDuration = estimatedDuration;
        }

        public String getStatus() { return status; }
        public String getService() { return service; }
        public String getTimestamp() { return timestamp; }
        public String getEstimatedDuration() { return estimatedDuration; }
    }
}