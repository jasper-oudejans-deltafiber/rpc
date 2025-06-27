package com.example.rpcrest.rpc;

import com.example.rpcrest.rpc.methods.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "RPC method results discriminated by method type",
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "method", visible = false)
@JsonSubTypes({
    @JsonSubTypes.Type(value = RebootMethod.RebootResponse.class, name = "reboot"),
    @JsonSubTypes.Type(value = StatusMethod.StatusResponse.class, name = "status"),
    @JsonSubTypes.Type(value = ShutdownMethod.ShutdownResponse.class, name = "shutdown"),
    @JsonSubTypes.Type(value = RestartMethod.RestartResponse.class, name = "restart"),
    @JsonSubTypes.Type(value = VersionMethod.VersionResponse.class, name = "version")
})
public abstract class RpcResult {
    // This is a marker class for the discriminated union
    // The actual implementations are the method-specific response classes
}