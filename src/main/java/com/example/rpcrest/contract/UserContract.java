package com.example.rpcrest.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "User contract definitions")
public class UserContract {
    
    @Schema(description = "Request payload for creating a new user")
    public static class CreateUserRequest {
        @Schema(description = "Username for the user", example = "johndoe", minLength = 3, maxLength = 50)
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;
        
        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @Schema(description = "Full name of the user", example = "John Doe", maxLength = 100)
        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        private String fullName;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }
    
    @Schema(description = "User response containing user details")
    public static class UserResponse {
        @Schema(description = "Unique identifier for the user", example = "1")
        private Long id;
        
        @Schema(description = "Username of the user", example = "johndoe")
        private String username;
        
        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        private String email;
        
        @Schema(description = "Full name of the user", example = "John Doe")
        private String fullName;
        
        @Schema(description = "Timestamp when the user was created", example = "2023-11-27T10:30:00")
        private String createdAt;

        public UserResponse(Long id, String username, String email, String fullName, String createdAt) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getFullName() { return fullName; }
        public String getCreatedAt() { return createdAt; }
    }
    
    @Schema(description = "Request payload for updating an existing user")
    public static class UpdateUserRequest {
        @Schema(description = "Updated email address (optional)", example = "john.updated@example.com")
        @Email(message = "Email should be valid")
        private String email;
        
        @Schema(description = "Updated full name (optional)", example = "John Updated Doe", maxLength = 100)
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        private String fullName;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }
}