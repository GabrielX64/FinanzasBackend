package com.upc.finanzasbackend.controllers;
import com.upc.finanzasbackend.Interfaces.IClientService;
import com.upc.finanzasbackend.dtos.BonoMiViviendaDTO;
import com.upc.finanzasbackend.dtos.ClientDTO;
import com.upc.finanzasbackend.entities.Client;
import com.upc.finanzasbackend.entities.MaritalStatus;
import com.upc.finanzasbackend.repositories.MaritalStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://18.223.169.236/"})
@RestController
@RequestMapping("/api")
@Slf4j
public class ClientController {
    @Autowired
    private IClientService clientService;

    @Autowired
    private MaritalStatusRepository maritalStatusRepository;

    private final ModelMapper mapper = new ModelMapper();

    // Método auxiliar para convertir Entity a DTO
    private ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setClientID(client.getClientID());
        dto.setNames(client.getNames());
        dto.setSurnames(client.getSurnames());
        dto.setDni(client.getDni());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setMonthlyIncome(client.getMonthlyIncome());
        dto.setOccupation(client.getOccupation());
        dto.setCurrentAddress(client.getCurrentAddress());

        if (client.getMaritalStatus() != null) {
            dto.getMaritalStatusID(client.getMaritalStatus().getMaritalStatusId());
        }

        return dto;
    }

    // Método auxiliar para convertir DTO a Entity
    private Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setClientID(dto.getClientID());
        client.setNames(dto.getNames());
        client.setSurnames(dto.getSurnames());
        client.setDni(dto.getDni());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setMonthlyIncome(dto.getMonthlyIncome());
        client.setOccupation(dto.getOccupation());
        client.setCurrentAddress(dto.getCurrentAddress());

        // Buscar y asignar EstadoCivil
        if (dto.getMaritalStatusID() != null) {
            MaritalStatus maritalStatus = maritalStatusRepository.findById(dto.getMaritalStatusID())
                    .orElseThrow(() -> new RuntimeException("Estado civil no encontrado"));
            client.setMaritalStatus(maritalStatus);
        }

        return client;
    }

    @GetMapping("/clientes")
    public List<ClientDTO> getAllClientes() {
        List<Client> clientes = clientService.getAllClientes();
        return clientes.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClientDTO> getClienteById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(toDTO(client));
    }

    @GetMapping("/cliente/dni/{dni}")
    public ResponseEntity<ClientDTO> getClienteByDni(@PathVariable String dni) {
        Client client = clientService.getClientByDni(dni);
        return ResponseEntity.ok(toDTO(client));
    }

    @PostMapping("/cliente")
    public ResponseEntity<ClientDTO> createCliente(@RequestBody ClientDTO clientDTO) {
        Client client = toEntity(clientDTO);
        client = clientService.createClient(client);
        return ResponseEntity.ok(toDTO(client));
    }

    @PutMapping("/cliente/update")
    public ResponseEntity<ClientDTO> updateCliente(@RequestBody ClientDTO clientDTO) {
        Client client = toEntity(clientDTO);
        client = clientService.updateClient(client);
        return ResponseEntity.ok(toDTO(client));
    }

    @DeleteMapping("/cliente/delete/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }

    @GetMapping("/cliente/{clienteId}/bono-mivivienda")
    public ResponseEntity<BonoMiViviendaDTO> verificarBonoMiVivienda(
            @PathVariable Long clienteId,
            @RequestParam BigDecimal precioVivienda) {
        BonoMiViviendaDTO resultado = clientService.verificarBonoMiVivienda(clienteId, precioVivienda);
        return ResponseEntity.ok(resultado);
    }
}
