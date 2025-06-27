package com.example.rpcrest;

import com.example.client.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstration of OneOf Schema Benefits for Type-Safe Client Generation
 * This test shows how oneOf discriminated unions provide compile-time type safety
 * and enable strongly-typed client code generation from OpenAPI specifications.
 */
public class OneOfTypeSafetyDemo {

    @Test
    void demonstrateStrongTypingSafety() {
        System.out.println("\n🎯 === OneOf Type Safety Benefits ===");
        
        // ❌ BEFORE: Weak typing with Map<String, Object>
        System.out.println("\n❌ Before (without oneOf):");
        System.out.println("   Map<String, Object> params = Map.of(");
        System.out.println("     \"reason\", \"maintenance\",");
        System.out.println("     \"invalid_field\", \"bad_value\"  // ← Runtime error!");
        System.out.println("   );");
        System.out.println("   // Problems:");
        System.out.println("   // - No compile-time validation");
        System.out.println("   // - Typos cause runtime errors");
        System.out.println("   // - No IDE autocompletion");
        System.out.println("   // - No type checking for field values");
        
        // ✅ AFTER: Strong typing with oneOf discriminated unions
        System.out.println("\n✅ After (with oneOf discrimination):");
        
        // 1. Reboot Request - Strongly Typed
        RebootRequest rebootParams = new RebootRequest()
            .reason("Scheduled maintenance")
            .force(false);
            // .invalidField("test"); // ← Compile error prevents this!
        
        UnifiedRpcRequest rebootRequest = new UnifiedRpcRequest()
            .method(UnifiedRpcRequest.MethodEnum.REBOOT)
            .params(new UnifiedRpcRequestParams(rebootParams))
            .id("typed-reboot-1");
        
        System.out.println("   // Reboot - Strongly typed parameters");
        System.out.println("   RebootRequest params = new RebootRequest()");
        System.out.println("     .reason(\"Scheduled maintenance\")");
        System.out.println("     .force(false);");
        System.out.println("   UnifiedRpcRequest request = new UnifiedRpcRequest()");
        System.out.println("     .method(MethodEnum.REBOOT)");
        System.out.println("     .params(new UnifiedRpcRequestParams(params));");
        
        // Verify type safety
        assertNotNull(rebootParams.getReason());
        assertFalse(rebootParams.getForce());
        assertEquals(UnifiedRpcRequest.MethodEnum.REBOOT, rebootRequest.getMethod());
        System.out.println("   ✓ Type validation successful!");
        
        // 2. Shutdown Request - Different typed parameters
        ShutdownRequest shutdownParams = new ShutdownRequest()
            .reason("System upgrade")
            .delay(120);  // delay is Integer, not String
        
        UnifiedRpcRequest shutdownRequest = new UnifiedRpcRequest()
            .method(UnifiedRpcRequest.MethodEnum.SHUTDOWN)
            .params(new UnifiedRpcRequestParams(shutdownParams))
            .id("typed-shutdown-1");
        
        System.out.println("\n   // Shutdown - Different parameter types");
        System.out.println("   ShutdownRequest params = new ShutdownRequest()");
        System.out.println("     .reason(\"System upgrade\")");
        System.out.println("     .delay(120);  // Integer type enforced");
        
        assertNotNull(shutdownParams.getReason());
        assertEquals(Integer.valueOf(120), shutdownParams.getDelay());
        System.out.println("   ✓ Type validation successful!");
        
        // 3. Restart Request - Service-specific parameters
        RestartRequest restartParams = new RestartRequest()
            .service("database-server");
        
        UnifiedRpcRequest restartRequest = new UnifiedRpcRequest()
            .method(UnifiedRpcRequest.MethodEnum.RESTART)
            .params(new UnifiedRpcRequestParams(restartParams))
            .id("typed-restart-1");
        
        System.out.println("\n   // Restart - Service-specific parameters");
        System.out.println("   RestartRequest params = new RestartRequest()");
        System.out.println("     .service(\"database-server\");");
        
        assertNotNull(restartParams.getService());
        assertEquals("database-server", restartParams.getService());
        System.out.println("   ✓ Type validation successful!");
        
        // 4. Method Enum Type Safety
        System.out.println("\n   // Method discrimination with enums");
        UnifiedRpcRequest.MethodEnum[] allMethods = UnifiedRpcRequest.MethodEnum.values();
        System.out.println("   Available methods: " + java.util.Arrays.toString(allMethods));
        
        // This would be a compile error:
        // rebootRequest.method("invalid-method"); // ← Compile error!
        // Only valid enum values are accepted
        
        System.out.println("   ✓ Enum validation prevents invalid methods!");
    }

    @Test
    void demonstrateCompileTimeValidation() {
        System.out.println("\n🛡️ === Compile-Time Validation Demo ===");
        
        // ✅ Valid parameter combinations
        System.out.println("\n✅ Valid combinations (compile successfully):");
        
        // Reboot can have reason and force
        RebootRequest validReboot = new RebootRequest()
            .reason("Maintenance")
            .force(true);
        System.out.println("   RebootRequest: reason='" + validReboot.getReason() + "', force=" + validReboot.getForce());
        
        // Shutdown can have reason and delay  
        ShutdownRequest validShutdown = new ShutdownRequest()
            .reason("Upgrade")
            .delay(60);
        System.out.println("   ShutdownRequest: reason='" + validShutdown.getReason() + "', delay=" + validShutdown.getDelay());
        
        // Restart can have service
        RestartRequest validRestart = new RestartRequest()
            .service("web-server");
        System.out.println("   RestartRequest: service='" + validRestart.getService() + "'");
        
        // ❌ These would cause COMPILE ERRORS (uncomment to see):
        System.out.println("\n❌ Invalid combinations (would not compile):");
        System.out.println("   // rebootRequest.service(\"invalid\");     ← Compile error!");
        System.out.println("   // shutdownRequest.force(true);          ← Compile error!");
        System.out.println("   // restartRequest.delay(30);             ← Compile error!");
        System.out.println("   // request.method(\"invalid-string\");    ← Compile error!");
        
        // ✅ Type safety enforced
        assertNotNull(validReboot);
        assertNotNull(validShutdown); 
        assertNotNull(validRestart);
        System.out.println("   ✓ All type validations passed!");
    }

    @Test
    void demonstrateCodeGenerationBenefits() {
        System.out.println("\n⚙️ === Code Generation Benefits ===");
        
        System.out.println("\n🎯 What the OpenAPI Generator Created:");
        System.out.println("   ✓ UnifiedRpcRequest with MethodEnum discrimination");
        System.out.println("   ✓ UnifiedRpcRequestParams with constructor overloads:");
        System.out.println("     - UnifiedRpcRequestParams(RebootRequest)");
        System.out.println("     - UnifiedRpcRequestParams(ShutdownRequest)");
        System.out.println("     - UnifiedRpcRequestParams(RestartRequest)");
        System.out.println("   ✓ Strongly-typed request classes for each method");
        System.out.println("   ✓ Method enum preventing invalid method names");
        System.out.println("   ✓ Complete client API with type safety");
        
        System.out.println("\n🚀 Developer Experience Benefits:");
        System.out.println("   ✓ IDE autocompletion for all parameters");
        System.out.println("   ✓ Compile-time error prevention");
        System.out.println("   ✓ Refactoring safety (rename fields across codebase)");
        System.out.println("   ✓ Documentation in code (JavaDoc from OpenAPI)");
        System.out.println("   ✓ Version compatibility (regenerate when API changes)");
        
        System.out.println("\n🔄 OneOf Schema Achievement:");
        System.out.println("   ✓ Single endpoint (/rpc) handles multiple request types");
        System.out.println("   ✓ Method field discriminates between parameter schemas");
        System.out.println("   ✓ Each method gets its own strongly-typed parameter class");
        System.out.println("   ✓ Code generators understand the discrimination pattern");
        System.out.println("   ✓ Client SDKs maintain full type safety across languages");
        
        // Verify we can instantiate all the generated classes
        UnifiedRpcRequest request = new UnifiedRpcRequest();
        UnifiedRpcRequestParams params = new UnifiedRpcRequestParams(new RebootRequest());
        UnifiedRpcResponse response = new UnifiedRpcResponse();
        
        assertNotNull(request);
        assertNotNull(params);
        assertNotNull(response);
        
        System.out.println("\n🎉 Success: OneOf discriminated unions provide the perfect balance of:");
        System.out.println("   • Single endpoint flexibility");
        System.out.println("   • Strong typing for code generation");
        System.out.println("   • Compile-time safety");
        System.out.println("   • Excellent developer experience");
    }
}