package com.example.rpcrest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "springdoc.api-docs.path=/v3/api-docs",
    "springdoc.swagger-ui.path=/swagger-ui.html"
})
public class OpenApiSpecExporter {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exportOpenApiYaml() throws Exception {
        // Get the OpenAPI spec in YAML format
        MvcResult result = mockMvc.perform(get("/v3/api-docs.yaml"))
                .andExpect(status().isOk())
                .andReturn();

        String yamlContent = result.getResponse().getContentAsString();
        
        // Write to test resources
        String resourcePath = "src/test/resources/openapi.yaml";
        try (FileWriter writer = new FileWriter(resourcePath)) {
            writer.write(yamlContent);
        }
        
        System.out.println("OpenAPI YAML exported to: " + resourcePath);
        System.out.println("YAML content length: " + yamlContent.length());
    }

    @Test
    public void exportOpenApiJson() throws Exception {
        // Get the OpenAPI spec in JSON format
        MvcResult result = mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonContent = result.getResponse().getContentAsString();
        
        // Write to test resources
        String resourcePath = "src/test/resources/openapi.json";
        try (FileWriter writer = new FileWriter(resourcePath)) {
            writer.write(jsonContent);
        }
        
        System.out.println("OpenAPI JSON exported to: " + resourcePath);
        System.out.println("JSON content length: " + jsonContent.length());
    }
}