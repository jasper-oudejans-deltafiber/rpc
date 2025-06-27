package com.example.rpcrest.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "JSON-RPC request structure")
public class RpcRequest {
    @Schema(description = "RPC method name to execute", example = "reboot", required = true)
    @JsonProperty("method")
    private String method;
    
    @Schema(description = "Parameters for the RPC method", example = "{\"reason\": \"maintenance\", \"force\": false}")
    @JsonProperty("params")
    private Map<String, Object> params;
    
    @Schema(description = "Unique identifier for the request", example = "1", required = true)
    @JsonProperty("id")
    private String id;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    
    public Map<String, Object> getParams() { return params; }
    public void setParams(Map<String, Object> params) { this.params = params; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}