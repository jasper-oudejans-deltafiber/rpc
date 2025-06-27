package com.example.rpcrest.rpc;

import com.example.rpcrest.rpc.methods.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SystemRpcService {

    public RebootMethod.RebootResponse reboot(RebootMethod.RebootRequest request) {
        String reason = request.getReason() != null ? request.getReason() : "Manual reboot";
        Boolean force = request.getForce() != null ? request.getForce() : false;
        
        return new RebootMethod.RebootResponse(
            "reboot_initiated",
            reason,
            force,
            LocalDateTime.now().toString(),
            "30 seconds"
        );
    }
    
    // Backward compatibility method
    public Object reboot(Map<String, Object> params) {
        RebootMethod.RebootRequest request = new RebootMethod.RebootRequest();
        request.setReason((String) params.get("reason"));
        request.setForce((Boolean) params.get("force"));
        return reboot(request);
    }

    public StatusMethod.StatusResponse getStatus(StatusMethod.StatusRequest request) {
        return new StatusMethod.StatusResponse(
            "2 days, 14 hours, 23 minutes",
            "65%",
            "12%",
            "78%",
            42,
            LocalDateTime.now().toString()
        );
    }
    
    // Backward compatibility method
    public Object getStatus(Map<String, Object> params) {
        return getStatus(new StatusMethod.StatusRequest());
    }

    public ShutdownMethod.ShutdownResponse shutdown(ShutdownMethod.ShutdownRequest request) {
        String reason = request.getReason() != null ? request.getReason() : "Manual shutdown";
        Integer delay = request.getDelay() != null ? request.getDelay() : 0;
        
        return new ShutdownMethod.ShutdownResponse(
            "shutdown_scheduled",
            reason,
            delay,
            LocalDateTime.now().toString()
        );
    }
    
    // Backward compatibility method
    public Object shutdown(Map<String, Object> params) {
        ShutdownMethod.ShutdownRequest request = new ShutdownMethod.ShutdownRequest();
        request.setReason((String) params.get("reason"));
        request.setDelay((Integer) params.get("delay"));
        return shutdown(request);
    }

    public RestartMethod.RestartResponse restart(RestartMethod.RestartRequest request) {
        String service = request.getService() != null ? request.getService() : "system";
        
        return new RestartMethod.RestartResponse(
            "restart_initiated",
            service,
            LocalDateTime.now().toString(),
            "10 seconds"
        );
    }
    
    // Backward compatibility method
    public Object restart(Map<String, Object> params) {
        RestartMethod.RestartRequest request = new RestartMethod.RestartRequest();
        request.setService((String) params.get("service"));
        return restart(request);
    }

    public VersionMethod.VersionResponse getVersion(VersionMethod.VersionRequest request) {
        return new VersionMethod.VersionResponse(
            "RPC-REST Service",
            "1.0.0",
            "20241127",
            System.getProperty("java.version"),
            "3.2.0"
        );
    }
    
    // Backward compatibility method
    public Object getVersion(Map<String, Object> params) {
        return getVersion(new VersionMethod.VersionRequest());
    }
}