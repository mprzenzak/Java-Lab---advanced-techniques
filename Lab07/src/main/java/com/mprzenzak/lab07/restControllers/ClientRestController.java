package com.mprzenzak.lab07.restControllers;

import com.mprzenzak.lab07.models.Client;
import com.mprzenzak.lab07.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        return clientService.findById(id);
    }

    @PostMapping("/")
    public Client createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }
}
