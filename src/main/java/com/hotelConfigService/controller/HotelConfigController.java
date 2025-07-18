package com.hotelConfigService.controller;

import com.hotelConfigService.entity.HotelDetails;
import com.hotelConfigService.service.HotelConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("*")
public class HotelConfigController {

    @Autowired
    private HotelConfigService service;

    private static final Logger log = LoggerFactory.getLogger(HotelConfigController.class);

    public HotelConfigController(HotelConfigService service) {
        this.service = service;
    }

    @PostMapping(value = "/hotel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HotelDetails> createOrUpdateConfig(
            @RequestPart("hotelData") HotelDetails hotelDetails,
            @RequestPart(value = "logo", required = false) MultipartFile logo) {

        log.info("Creating/Updating hotel config: {}", hotelDetails.getHotelName());

        if (logo != null && !logo.isEmpty()) {
            try {
                String base64Logo = Base64.getEncoder().encodeToString(logo.getBytes());
                hotelDetails.setLogoUrl(base64Logo); // save base64 in DB
            } catch (IOException e) {
                log.error("Error reading logo image", e);
                return ResponseEntity.badRequest().build();
            }
        }

        HotelDetails saved = service.saveConfig(hotelDetails);

        log.info("Hotel config saved with ID: {}", saved.getId());

        return ResponseEntity.ok(saved);
    }


    @GetMapping("/hotel")
    public ResponseEntity<HotelDetails> getAllConfigs() {
        log.info("Received request to get all hotel configurations");

        List<HotelDetails> configs = service.getAllConfigs();

        log.info("Total configurations found: {}", configs.size());

        return ResponseEntity.ok(configs.get(0));
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<HotelDetails> getConfigById(@PathVariable String id) {
        log.info("Received request to get hotel config by ID: {}", id);

        HotelDetails hotelDetails = service.getById(id);
        if (hotelDetails == null) {
            log.warn("No hotel config found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Hotel config retrieved successfully: {}", hotelDetails);
        return ResponseEntity.ok(hotelDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable String id) {
        log.info("Received request to delete hotel config with ID: {}", id);

        service.deleteById(id);

        log.info("Hotel config deleted successfully for ID: {}", id);

        return ResponseEntity.noContent().build();
    }
}
