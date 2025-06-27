package com.example.rpcrest.rpc.methods;

import io.swagger.v3.oas.annotations.media.Schema;

public class StatusMethod {
    
    @Schema(description = "Status request parameters (empty)")
    public static class StatusRequest {
        // No parameters needed for status
    }
    
    @Schema(description = "System status information")
    public static class StatusResponse {
        @Schema(description = "System uptime", example = "2 days, 14 hours, 23 minutes")
        private String uptime;
        
        @Schema(description = "Memory usage percentage", example = "65%")
        private String memoryUsage;
        
        @Schema(description = "CPU usage percentage", example = "12%")
        private String cpuUsage;
        
        @Schema(description = "Disk usage percentage", example = "78%")
        private String diskUsage;
        
        @Schema(description = "Number of active connections", example = "42")
        private Integer activeConnections;
        
        @Schema(description = "Timestamp of the status check", example = "2023-11-27T10:30:00")
        private String timestamp;

        public StatusResponse(String uptime, String memoryUsage, String cpuUsage, String diskUsage, Integer activeConnections, String timestamp) {
            this.uptime = uptime;
            this.memoryUsage = memoryUsage;
            this.cpuUsage = cpuUsage;
            this.diskUsage = diskUsage;
            this.activeConnections = activeConnections;
            this.timestamp = timestamp;
        }

        public String getUptime() { return uptime; }
        public String getMemoryUsage() { return memoryUsage; }
        public String getCpuUsage() { return cpuUsage; }
        public String getDiskUsage() { return diskUsage; }
        public Integer getActiveConnections() { return activeConnections; }
        public String getTimestamp() { return timestamp; }
    }
}