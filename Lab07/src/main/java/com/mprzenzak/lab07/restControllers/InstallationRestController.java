package com.mprzenzak.lab07.restControllers;

import com.mprzenzak.lab07.models.Installation;
import com.mprzenzak.lab07.services.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installations")
public class InstallationRestController {

    @Autowired
    private InstallationService installationService;

    @GetMapping
    public ResponseEntity<List<Installation>> getAllInstallations() {
        List<Installation> installations = installationService.findAll();
        return new ResponseEntity<>(installations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Installation> getInstallationById(@PathVariable("id") Long id) {
        Installation installation = installationService.findById(id);

        if (installation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(installation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Installation> createInstallation(@RequestBody Installation installation) {
        Installation savedInstallation = installationService.save(installation);
        return new ResponseEntity<>(savedInstallation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Installation> updateInstallation(@PathVariable("id") Long id,
                                                           @RequestBody Installation installation) {
        Installation currentInstallation = installationService.findById(id);

        if (currentInstallation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentInstallation.setAddress(installation.getAddress());
        currentInstallation.setRouterNumber(installation.getRouterNumber());
        currentInstallation.setServiceType(installation.getServiceType());
        currentInstallation.setClient(installation.getClient());

        Installation updatedInstallation = installationService.save(currentInstallation);
        return new ResponseEntity<>(updatedInstallation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstallation(@PathVariable("id") Long id) {
        Installation installation = installationService.findById(id);

        if (installation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        installationService.delete(installation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
