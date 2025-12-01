package com.upc.finanzasbackend.Interfaces;
import com.upc.finanzasbackend.dtos.BonoMiViviendaDTO;
import com.upc.finanzasbackend.entities.Client;

import java.math.BigDecimal;
import java.util.List;

public interface IClientService {
    List<Client> getAllClientes();
    Client getClientById(Long id);
    Client createClient(Client client);
    Client updateClient(Client client);
    void deleteClient(Long id);
    Client getClientByDni(String dni);
    BonoMiViviendaDTO checkMyHomeBonus(Long clientId, BigDecimal propertyPrice);
}
