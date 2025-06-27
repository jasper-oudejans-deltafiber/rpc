package com.example.rpcrest.controller;

import com.example.rpcrest.rpc.*;
import com.example.rpcrest.rpc.methods.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rpc")
@Tag(name = "RPC Service", description = "Unified JSON-RPC endpoint for system operations")
public class RpcController {

    @Autowired
    private SystemRpcService systemRpcService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    @Operation(
        summary = "Execute RPC call",
        description = "Unified JSON-RPC endpoint using discriminated union for params based on method field",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                schema = @Schema(implementation = UnifiedRpcRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Reboot with typed params",
                        value = "{\"method\": \"reboot\", \"params\": {\"reason\": \"Scheduled maintenance\", \"force\": false}, \"id\": \"1\"}"
                    ),
                    @ExampleObject(
                        name = "Status with typed params",
                        value = "{\"method\": \"status\", \"params\": {}, \"id\": \"2\"}"
                    ),
                    @ExampleObject(
                        name = "Shutdown with typed params",
                        value = "{\"method\": \"shutdown\", \"params\": {\"reason\": \"Maintenance\", \"delay\": 30}, \"id\": \"3\"}"
                    )
                }
            )
        )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "RPC call executed successfully with discriminated result",
        content = @Content(schema = @Schema(implementation = UnifiedRpcResponse.class))
    )
    public ResponseEntity<UnifiedRpcResponse> handleUnifiedRpcCall(@RequestBody UnifiedRpcRequest request) {
        try {
            UnifiedRpcResponse response = executeMethod(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RpcResponse.RpcError error = new RpcResponse.RpcError(-1, e.getMessage());
            return ResponseEntity.ok(new UnifiedRpcResponse(request.getId(), request.getMethod(), error));
        }
    }

    private UnifiedRpcResponse executeMethod(UnifiedRpcRequest request) throws Exception {
        String method = request.getMethod().toLowerCase();
        String id = request.getId();
        Object params = request.getParams();
        
        switch (method) {
            case "reboot":
                RebootMethod.RebootRequest rebootReq = objectMapper.convertValue(params, RebootMethod.RebootRequest.class);
                RebootMethod.RebootResponse rebootResult = systemRpcService.reboot(rebootReq);
                return new UnifiedRpcResponse(id, "reboot", rebootResult);
                
            case "status":
            case "getstatus":
                StatusMethod.StatusRequest statusReq = objectMapper.convertValue(params, StatusMethod.StatusRequest.class);
                StatusMethod.StatusResponse statusResult = systemRpcService.getStatus(statusReq);
                return new UnifiedRpcResponse(id, "status", statusResult);
                
            case "shutdown":
                ShutdownMethod.ShutdownRequest shutdownReq = objectMapper.convertValue(params, ShutdownMethod.ShutdownRequest.class);
                ShutdownMethod.ShutdownResponse shutdownResult = systemRpcService.shutdown(shutdownReq);
                return new UnifiedRpcResponse(id, "shutdown", shutdownResult);
                
            case "restart":
                RestartMethod.RestartRequest restartReq = objectMapper.convertValue(params, RestartMethod.RestartRequest.class);
                RestartMethod.RestartResponse restartResult = systemRpcService.restart(restartReq);
                return new UnifiedRpcResponse(id, "restart", restartResult);
                
            case "version":
            case "getversion":
                VersionMethod.VersionRequest versionReq = objectMapper.convertValue(params, VersionMethod.VersionRequest.class);
                VersionMethod.VersionResponse versionResult = systemRpcService.getVersion(versionReq);
                return new UnifiedRpcResponse(id, "version", versionResult);
                
            default:
                throw new IllegalArgumentException("Unknown method: " + method);
        }
    }
}