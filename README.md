# RPC-REST Service

A demonstration project showcasing a **dual-interface microservice** that provides both REST and RPC endpoints with automatic type-safe client generation using OpenAPI 3.0.

## Overview

This Spring Boot application demonstrates how to build a unified API service that combines:
- **JSON-RPC endpoints** for system operations (reboot, status, shutdown)
- **REST endpoints** for resource management (user CRUD operations)
- **Type-safe client generation** using discriminated union patterns
- **Comprehensive API documentation** with OpenAPI/Swagger

## Key Features

### 🔄 JSON-RPC System Operations
- **System Control**: Reboot, shutdown, restart operations with configurable parameters
- **Status Monitoring**: Real-time system metrics (uptime, memory, CPU, disk usage)
- **Version Information**: Application and system version details
- **Unified Endpoint**: Single `/rpc` endpoint with method-based routing

### 👥 REST User Management
- Full CRUD operations for user management
- Input validation with Jakarta Bean Validation
- Structured API responses with standardized error handling

### 🛠️ Type-Safe Client Generation
- **Discriminated Unions**: Type-safe RPC method parameters
- **Automatic Code Generation**: Maven plugin generates Java client code
- **Compile-time Safety**: Prevent runtime errors with strong typing

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Running the Application

```bash
# Clone and build
git clone <repository-url>
cd rpc
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### API Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

## API Usage Examples

### JSON-RPC System Operations

```bash
# Get system status
curl -X POST http://localhost:8080/rpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "status",
    "params": {},
    "id": 1
  }'

# Reboot system with reason
curl -X POST http://localhost:8080/rpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "reboot",
    "params": {
      "reason": "Scheduled maintenance",
      "force": false
    },
    "id": 2
  }'
```

### REST User Management

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com"
  }'

# Get all users
curl http://localhost:8080/api/users
```

## Architecture

### Core Components

- **RpcController**: Unified JSON-RPC endpoint with discriminated union handling
- **SystemRpcService**: Implementation of system operation methods
- **UserRestController**: Traditional REST endpoints for user management
- **UnifiedRpcRequest**: Type-safe request structure with method discrimination

### RPC Methods

| Method | Description | Parameters |
|--------|-------------|------------|
| `status` | Get system status | None |
| `reboot` | Reboot system | `reason`, `force` |
| `shutdown` | Shutdown system | `delay`, `reason` |
| `restart` | Restart service | `service`, `timeout` |
| `version` | Get version info | None |

## Code Generation

The project uses OpenAPI Generator to create type-safe clients:

```bash
# Generate client code
mvn clean compile

# Generated client is available in target/generated-sources/openapi/
```

### Using Generated Client

```java
ApiClient client = new ApiClient();
client.setBasePath("http://localhost:8080");

RpcApi rpcApi = new RpcApi(client);

// Type-safe RPC call
UnifiedRpcRequest statusRequest = new UnifiedRpcRequest()
    .jsonrpc("2.0")
    .method("status")
    .params(new StatusMethod())
    .id(1);

UnifiedRpcResponse response = rpcApi.handleRpcRequest(statusRequest);
```

## Technology Stack

- **Framework**: Spring Boot 3.2.12
- **Language**: Java 17
- **Build Tool**: Maven
- **Documentation**: OpenAPI 3.0 (SpringDoc)
- **Validation**: Jakarta Bean Validation
- **HTTP Client**: OkHttp (generated clients)
- **JSON Processing**: Jackson, Gson

## Use Cases

This project is ideal for:

- **Hybrid API Design**: Combining REST and RPC paradigms in a single service
- **Type-Safe RPC**: Demonstrating discriminated union patterns for RPC
- **API Documentation**: Best practices for OpenAPI-driven development
- **Code Generation**: Automatic client generation workflows
- **Microservice Architecture**: Template for dual-interface services

## Development

### Running Tests
```bash
mvn test
```

### Building
```bash
mvn clean package
```

### Generating API Documentation
```bash
mvn compile
# OpenAPI spec is generated at target/generated-sources/openapi/
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is a proof-of-concept demonstration and is available for educational and reference purposes.