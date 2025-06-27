package com.example.rpcrest.rpc;

import com.example.rpcrest.rpc.methods.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unified RPC request with discriminated params field based on method")
public class UnifiedRpcRequest {
    
    @Schema(
        description = "RPC method name", 
        example = "reboot", 
        required = true,
        allowableValues = {"reboot", "status", "shutdown", "restart", "version"}
    )
    @JsonProperty("method")
    private String method;
    
    @Schema(
        description = "Method-specific parameters discriminated by method field",
        discriminatorProperty = "method",
        discriminatorMapping = {
            @DiscriminatorMapping(value = "reboot", schema = RebootMethod.RebootRequest.class),
            @DiscriminatorMapping(value = "status", schema = StatusMethod.StatusRequest.class),
            @DiscriminatorMapping(value = "shutdown", schema = ShutdownMethod.ShutdownRequest.class),
            @DiscriminatorMapping(value = "restart", schema = RestartMethod.RestartRequest.class),
            @DiscriminatorMapping(value = "version", schema = VersionMethod.VersionRequest.class)
        },
        oneOf = {
            RebootMethod.RebootRequest.class,
            StatusMethod.StatusRequest.class,
            ShutdownMethod.ShutdownRequest.class,
            RestartMethod.RestartRequest.class,
            VersionMethod.VersionRequest.class
        }
    )
    @JsonProperty("params")
    private Object params;
    
    @Schema(description = "Request identifier", example = "1", required = true)
    @JsonProperty("id")
    private String id;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    
    public Object getParams() { return params; }
    public void setParams(Object params) { this.params = params; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Helper methods to get typed parameters
    public RebootMethod.RebootRequest getRebootParams() {
        return "reboot".equals(method) ? (RebootMethod.RebootRequest) params : null;
    }
    
    public StatusMethod.StatusRequest getStatusParams() {
        return "status".equals(method) ? (StatusMethod.StatusRequest) params : null;
    }
    
    public ShutdownMethod.ShutdownRequest getShutdownParams() {
        return "shutdown".equals(method) ? (ShutdownMethod.ShutdownRequest) params : null;
    }
    
    public RestartMethod.RestartRequest getRestartParams() {
        return "restart".equals(method) ? (RestartMethod.RestartRequest) params : null;
    }
    
    public VersionMethod.VersionRequest getVersionParams() {
        return "version".equals(method) ? (VersionMethod.VersionRequest) params : null;
    }
}