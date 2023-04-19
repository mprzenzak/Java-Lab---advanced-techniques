package com.mprzenzak.lab07.services;

import com.mprzenzak.lab07.models.Client;
import com.mprzenzak.lab07.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            return optionalClient.get();
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }

    public Client updateClient(Long id, Client updatedClient) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        if (clientOptional.isPresent()) {
            Client existingClient = clientOptional.get();
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            return clientRepository.save(existingClient);
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }
}
