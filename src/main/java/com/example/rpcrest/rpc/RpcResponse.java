package com.example.rpcrest.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JSON-RPC response structure")
public class RpcResponse {
    @Schema(description = "Result of the RPC call (present on success)", example = "{\"status\": \"reboot_initiated\", \"timestamp\": \"2023-11-27T10:30:00\"}")
    @JsonProperty("result")
    private Object result;
    
    @Schema(description = "Error information (present on failure)")
    @JsonProperty("error")
    private RpcError error;
    
    @Schema(description = "Request identifier that matches the original request", example = "1")
    @JsonProperty("id")
    private String id;

    public RpcResponse(String id, Object result) {
        this.id = id;
        this.result = result;
    }

    public RpcResponse(String id, RpcError error) {
        this.id = id;
        this.error = error;
    }

    public Object getResult() { return result; }
    public RpcError getError() { return error; }
    public String getId() { return id; }

    @Schema(description = "RPC error details")
    public static class RpcError {
        @Schema(description = "Error code", example = "-1")
        @JsonProperty("code")
        private int code;
        
        @Schema(description = "Error message", example = "Unknown method: invalid_method")
        @JsonProperty("message")
        private String message;

        public RpcError(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() { return code; }
        public String getMessage() { return message; }
    }
}