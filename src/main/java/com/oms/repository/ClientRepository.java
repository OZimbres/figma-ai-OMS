package com.oms.repository;

import com.oms.model.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClientRepository {
    private final ConcurrentHashMap<String, Client> clients = new ConcurrentHashMap<>();

    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }

    public Optional<Client> findById(String id) {
        return Optional.ofNullable(clients.get(id));
    }

    public Client save(Client client) {
        clients.put(client.getId(), client);
        return client;
    }

    public Client update(Client client) {
        clients.put(client.getId(), client);
        return client;
    }

    public void delete(String id) {
        clients.remove(id);
    }
}
