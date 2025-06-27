package com.example.rpcrest.rpc.methods;

import io.swagger.v3.oas.annotations.media.Schema;

public class RebootMethod {
    
    @Schema(description = "Reboot request parameters")
    public static class RebootRequest {
        @Schema(description = "Reason for reboot", example = "Scheduled maintenance")
        private String reason;
        
        @Schema(description = "Force reboot without graceful shutdown", example = "false")
        private Boolean force;

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        
        public Boolean getForce() { return force; }
        public void setForce(Boolean force) { this.force = force; }
    }
    
    @Schema(description = "Reboot operation result")
    public static class RebootResponse {
        @Schema(description = "Operation status", example = "reboot_initiated")
        private String status;
        
        @Schema(description = "Reason for reboot", example = "Scheduled maintenance")
        private String reason;
        
        @Schema(description = "Whether force reboot was used", example = "false")
        private Boolean force;
        
        @Schema(description = "Timestamp of the operation", example = "2023-11-27T10:30:00")
        private String timestamp;
        
        @Schema(description = "Estimated downtime", example = "30 seconds")
        private String estimatedDowntime;

        public RebootResponse(String status, String reason, Boolean force, String timestamp, String estimatedDowntime) {
            this.status = status;
            this.reason = reason;
            this.force = force;
            this.timestamp = timestamp;
            this.estimatedDowntime = estimatedDowntime;
        }

        public String getStatus() { return status; }
        public String getReason() { return reason; }
        public Boolean getForce() { return force; }
        public String getTimestamp() { return timestamp; }
        public String getEstimatedDowntime() { return estimatedDowntime; }
    }
}