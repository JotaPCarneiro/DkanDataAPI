package com.dkan.dkandata.controllers;

import com.dkan.dkandata.services.DkanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

@RestController
@RequestMapping("/api/dkan")
public class DkanController {

    @Autowired
    private DkanClient dkanClient;

    @GetMapping("/dataset/{id}")
    public ResponseEntity<JsonNode> getDataset(@PathVariable("id") String datasetId) {
        try {
            JsonNode dataset = dkanClient.getDataset(datasetId);
            return ResponseEntity.ok(dataset);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
