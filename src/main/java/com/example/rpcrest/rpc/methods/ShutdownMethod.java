package com.example.rpcrest.rpc.methods;

import io.swagger.v3.oas.annotations.media.Schema;

public class ShutdownMethod {
    
    @Schema(description = "Shutdown request parameters")
    public static class ShutdownRequest {
        @Schema(description = "Reason for shutdown", example = "Planned maintenance")
        private String reason;
        
        @Schema(description = "Delay in seconds before shutdown", example = "30")
        private Integer delay;

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        
        public Integer getDelay() { return delay; }
        public void setDelay(Integer delay) { this.delay = delay; }
    }
    
    @Schema(description = "Shutdown operation result")
    public static class ShutdownResponse {
        @Schema(description = "Operation status", example = "shutdown_scheduled")
        private String status;
        
        @Schema(description = "Reason for shutdown", example = "Planned maintenance")
        private String reason;
        
        @Schema(description = "Delay in seconds", example = "30")
        private Integer delaySeconds;
        
        @Schema(description = "Timestamp of the operation", example = "2023-11-27T10:30:00")
        private String timestamp;

        public ShutdownResponse(String status, String reason, Integer delaySeconds, String timestamp) {
            this.status = status;
            this.reason = reason;
            this.delaySeconds = delaySeconds;
            this.timestamp = timestamp;
        }

        public String getStatus() { return status; }
        public String getReason() { return reason; }
        public Integer getDelaySeconds() { return delaySeconds; }
        public String getTimestamp() { return timestamp; }
    }
}