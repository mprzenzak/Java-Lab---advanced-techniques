package com.mprzenzak.lab06.controllers;

import com.mprzenzak.lab06.enums.ServiceType;
import com.mprzenzak.lab06.models.Client;
import com.mprzenzak.lab06.models.Installation;
import com.mprzenzak.lab06.repository.InstallationRepository;
import com.mprzenzak.lab06.services.ClientService;
import com.mprzenzak.lab06.services.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InstallationController {
    private final InstallationService installationService;
    private final ClientService clientService;

    @Autowired
    private InstallationRepository installationRepository;

    public InstallationController(InstallationService installationService, ClientService clientService) {
        this.installationService = installationService;
        this.clientService = clientService;
    }
    @GetMapping("clients/add_installation/{id}")
    public String showAddInstallationForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("installation", new Installation());
        model.addAttribute("client_id", id);
        return "add_installation";
    }

    @PostMapping("/clients/add_installation/{id}")
    public String addInstallation(@PathVariable("id") Long id,
                                  @RequestParam("address") String address,
                                  @RequestParam("routerNumber") String routerNumber,
                                  @RequestParam("serviceType") ServiceType serviceType) {
        Client client = clientService.findById(id);

        if (client == null) {
            return "error";
        }

        Installation installation = new Installation();
        installation.setAddress(address);
        installation.setRouterNumber(routerNumber);
        installation.setServiceType(serviceType);
        installation.setClient(client);

        installationService.save(installation);

        return "redirect:/clients";
    }

    @GetMapping("/installations_list")
    public String showClients(Model model) {
        Iterable<Installation> installations = installationRepository.findAll();
        model.addAttribute("installations", installations);
        return "installations_list";
    }
}
