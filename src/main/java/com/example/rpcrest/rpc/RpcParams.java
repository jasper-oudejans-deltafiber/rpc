package com.example.rpcrest.rpc;

import com.example.rpcrest.rpc.methods.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "RPC method parameters discriminated by method type",
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "method", visible = false)
@JsonSubTypes({
    @JsonSubTypes.Type(value = RebootMethod.RebootRequest.class, name = "reboot"),
    @JsonSubTypes.Type(value = StatusMethod.StatusRequest.class, name = "status"),
    @JsonSubTypes.Type(value = ShutdownMethod.ShutdownRequest.class, name = "shutdown"),
    @JsonSubTypes.Type(value = RestartMethod.RestartRequest.class, name = "restart"),
    @JsonSubTypes.Type(value = VersionMethod.VersionRequest.class, name = "version")
})
public abstract class RpcParams {
    // This is a marker class for the discriminated union
    // The actual implementations are the method-specific request classes
}