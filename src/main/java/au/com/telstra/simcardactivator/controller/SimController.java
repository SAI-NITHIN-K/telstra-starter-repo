package au.com.telstra.simcardactivator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class SimController {
	
	@PostMapping("/activate-sim")
    public ResponseEntity<String> activateSim(@RequestBody Map<String, String> payload) {
        String iccid = payload.get("iccid");
        String customerEmail = payload.get("customerEmail");

        if (iccid == null || customerEmail == null) {
            return ResponseEntity.badRequest().body("Invalid payload");
        }

        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(actuatorUrl, Map.of("iccid", iccid), Map.class);
            Boolean success = (Boolean) response.getBody().get("success");

            if (success != null && success) {
                return ResponseEntity.ok("SIM activation successful!");
            } else {
                return ResponseEntity.status(500).body("SIM activation failed!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error communicating with the actuator service.");
        }
    }
}


