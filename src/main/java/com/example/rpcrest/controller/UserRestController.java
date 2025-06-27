package com.example.rpcrest.controller;

import com.example.rpcrest.contract.ApiResponse;
import com.example.rpcrest.contract.UserContract;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "REST API for managing users")
public class UserRestController {

    private final List<UserContract.UserResponse> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostMapping
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user with the provided information"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<UserContract.UserResponse>> createUser(
            @Valid @RequestBody UserContract.CreateUserRequest request) {
        
        UserContract.UserResponse user = new UserContract.UserResponse(
                idGenerator.getAndIncrement(),
                request.getUsername(),
                request.getEmail(),
                request.getFullName(),
                LocalDateTime.now().toString()
        );
        
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", user));
    }

    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users in the system"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved all users",
        content = @Content(schema = @Schema(implementation = ApiResponse.class))
    )
    public ResponseEntity<ApiResponse<List<UserContract.UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their unique identifier"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "User found successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<UserContract.UserResponse>> getUserById(
            @Parameter(description = "User ID to retrieve", required = true) @PathVariable Long id) {
        Optional<UserContract.UserResponse> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        
        if (user.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update user",
        description = "Updates an existing user's information"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<UserContract.UserResponse>> updateUser(
            @Parameter(description = "User ID to update", required = true) @PathVariable Long id,
            @Valid @RequestBody UserContract.UpdateUserRequest request) {
        
        Optional<UserContract.UserResponse> existingUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
        
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
        
        UserContract.UserResponse oldUser = existingUser.get();
        users.removeIf(u -> u.getId().equals(id));
        
        UserContract.UserResponse updatedUser = new UserContract.UserResponse(
                id,
                oldUser.getUsername(),
                request.getEmail() != null ? request.getEmail() : oldUser.getEmail(),
                request.getFullName() != null ? request.getFullName() : oldUser.getFullName(),
                oldUser.getCreatedAt()
        );
        
        users.add(updatedUser);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete user",
        description = "Deletes a user from the system"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "User deleted successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        )
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID to delete", required = true) @PathVariable Long id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        
        if (removed) {
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
    }
}