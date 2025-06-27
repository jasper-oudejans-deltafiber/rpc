package com.example.rpcrest.rpc.methods;

import io.swagger.v3.oas.annotations.media.Schema;

public class VersionMethod {
    
    @Schema(description = "Version request parameters (empty)")
    public static class VersionRequest {
        // No parameters needed for version
    }
    
    @Schema(description = "Application version information")
    public static class VersionResponse {
        @Schema(description = "Application name", example = "RPC-REST Service")
        private String application;
        
        @Schema(description = "Application version", example = "1.0.0")
        private String version;
        
        @Schema(description = "Build number", example = "20241127")
        private String build;
        
        @Schema(description = "Java version", example = "17.0.1")
        private String javaVersion;
        
        @Schema(description = "Spring Boot version", example = "3.2.0")
        private String springBootVersion;

        public VersionResponse(String application, String version, String build, String javaVersion, String springBootVersion) {
            this.application = application;
            this.version = version;
            this.build = build;
            this.javaVersion = javaVersion;
            this.springBootVersion = springBootVersion;
        }

        public String getApplication() { return application; }
        public String getVersion() { return version; }
        public String getBuild() { return build; }
        public String getJavaVersion() { return javaVersion; }
        public String getSpringBootVersion() { return springBootVersion; }
    }
}