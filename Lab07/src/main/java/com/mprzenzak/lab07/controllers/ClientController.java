package com.mprzenzak.lab07.controllers;

import com.mprzenzak.lab07.models.Client;
import com.mprzenzak.lab07.repository.ClientRepository;
import com.mprzenzak.lab07.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "add_client";
    }

    @PostMapping("/clients/add")
    public String addClient(@ModelAttribute Client client, Model model) {
        clientService.save(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "clients";
    }

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String showClients(Model model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String showEditClientForm(@PathVariable("id") Long id, Model model) {
        Client client = clientService.findById(id);
        model.addAttribute("client", client);
        return "edit_client";
    }

    @PostMapping("/clients/edit/{id}")
    public String updateClient(@PathVariable("id") Long id, @ModelAttribute("client") Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            client.setId(id);
            return "edit_client";
        }

        clientService.updateClient(id, client);
        model.addAttribute("clients", clientRepository.findAll());
        return "clients";
    }
}
