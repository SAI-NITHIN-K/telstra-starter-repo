package au.com.telstra.simcardactivator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.telstra.simcardactivator.entity.SimCardActivation;
import au.com.telstra.simcardactivator.service.SimCardActivationService;

@RestController
@RequestMapping("/api/activations")
public class SimCardActivationController {

    @Autowired
    private SimCardActivationService service;

    @PostMapping
    public ResponseEntity<String> activateSim(@RequestBody Map<String, String> payload) {
        String iccid = payload.get("iccid");
        String customerEmail = payload.get("customerEmail");

        // Forward the request to the Actuator Service
        boolean isActivated = forwardToActuator(iccid);

        // Save the record to the database
        service.saveActivation(iccid, customerEmail, isActivated);

        return isActivated ? ResponseEntity.ok("Activation successful")
                           : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Activation failed");
    }

    @GetMapping
    public ResponseEntity<?> getActivation(@RequestParam Long simCardId) {
        SimCardActivation activation = service.getActivationById(simCardId);
        if (activation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found for the given ID");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("iccid", activation.getIccid());
        response.put("customerEmail", activation.getCustomerEmail());
        response.put("active", activation.isActive());
        return ResponseEntity.ok(response);
    }

    private boolean forwardToActuator(String iccid) {
        // Implementation of forwarding to the Actuator Service
        return true; // Placeholder for actual implementation
    }
}
