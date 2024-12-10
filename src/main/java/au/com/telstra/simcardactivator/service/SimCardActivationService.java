package au.com.telstra.simcardactivator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.telstra.simcardactivator.entity.SimCardActivation;
import au.com.telstra.simcardactivator.repository.SimCardActivationRepository;

@Service
public class SimCardActivationService {

	
	@Autowired
    private SimCardActivationRepository repository;

    public SimCardActivation saveActivation(String iccid, String customerEmail, boolean active) {
        SimCardActivation activation = new SimCardActivation();
        activation.setIccid(iccid);
        activation.setCustomerEmail(customerEmail);
        activation.setActive(active);
        return repository.save(activation);
    }

    public SimCardActivation getActivationById(Long id) {
        return repository.findById(id).orElse(null);
    }
	
}
