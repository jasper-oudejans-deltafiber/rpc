package com.example.rpcrest.rpc;

import com.example.rpcrest.rpc.methods.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unified RPC response with discriminated result field based on method")
public class UnifiedRpcResponse {
    
    @Schema(
        description = "Method-specific result discriminated by method field",
        discriminatorProperty = "method", 
        discriminatorMapping = {
            @DiscriminatorMapping(value = "reboot", schema = RebootMethod.RebootResponse.class),
            @DiscriminatorMapping(value = "status", schema = StatusMethod.StatusResponse.class),
            @DiscriminatorMapping(value = "shutdown", schema = ShutdownMethod.ShutdownResponse.class),
            @DiscriminatorMapping(value = "restart", schema = RestartMethod.RestartResponse.class),
            @DiscriminatorMapping(value = "version", schema = VersionMethod.VersionResponse.class)
        },
        oneOf = {
            RebootMethod.RebootResponse.class,
            StatusMethod.StatusResponse.class,
            ShutdownMethod.ShutdownResponse.class,
            RestartMethod.RestartResponse.class,
            VersionMethod.VersionResponse.class
        }
    )
    @JsonProperty("result")
    private Object result;
    
    @Schema(description = "Error information (present when result is null)")
    @JsonProperty("error")
    private RpcResponse.RpcError error;
    
    @Schema(description = "Request identifier", example = "1")
    @JsonProperty("id")
    private String id;
    
    @Schema(description = "RPC method name for discriminator", example = "reboot")
    @JsonProperty("method")
    private String method;

    public UnifiedRpcResponse(String id, String method, Object result) {
        this.id = id;
        this.method = method;
        this.result = result;
    }

    public UnifiedRpcResponse(String id, String method, RpcResponse.RpcError error) {
        this.id = id;
        this.method = method;
        this.error = error;
    }

    public Object getResult() { return result; }
    public RpcResponse.RpcError getError() { return error; }
    public String getId() { return id; }
    public String getMethod() { return method; }

    // Helper methods to get typed results
    public RebootMethod.RebootResponse getRebootResult() {
        return "reboot".equals(method) && result != null ? (RebootMethod.RebootResponse) result : null;
    }
    
    public StatusMethod.StatusResponse getStatusResult() {
        return "status".equals(method) && result != null ? (StatusMethod.StatusResponse) result : null;
    }
    
    public ShutdownMethod.ShutdownResponse getShutdownResult() {
        return "shutdown".equals(method) && result != null ? (ShutdownMethod.ShutdownResponse) result : null;
    }
    
    public RestartMethod.RestartResponse getRestartResult() {
        return "restart".equals(method) && result != null ? (RestartMethod.RestartResponse) result : null;
    }
    
    public VersionMethod.VersionResponse getVersionResult() {
        return "version".equals(method) && result != null ? (VersionMethod.VersionResponse) result : null;
    }
}