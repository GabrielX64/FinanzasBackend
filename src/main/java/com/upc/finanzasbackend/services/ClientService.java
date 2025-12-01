package com.upc.finanzasbackend.services;
import com.upc.finanzasbackend.Interfaces.IClientService;
import com.upc.finanzasbackend.dtos.BonoMiViviendaDTO;
import com.upc.finanzasbackend.entities.Client;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.ClientRepository;
import com.upc.finanzasbackend.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public List<Client> getAllClientes() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RequestException("C001",
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"));
    }

    @Override
    public Client createClient(Client client) {
        // Validaciones
        validationService.verifyNoNulls(
                client.getNames(),
                client.getSurnames(),
                client.getDni(),
                client.getEmail()
        );

        validationService.verifyNoEmpty(
                client.getNames(),
                client.getSurnames(),
                client.getDni(),
                client.getEmail()
        );

        // Validar DNI único
        if (clientRepository.existsByDni(client.getDni())) {
            throw new RequestException("C002",
                    HttpStatus.CONFLICT,
                    "El DNI ya está registrado");
        }

        // Validar correo único
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RequestException("C003",
                    HttpStatus.CONFLICT,
                    "El correo ya está registrado");
        }

        // Validar formato DNI (8 dígitos)
        if (!client.getDni().matches("\\d{8}")) {
            throw new RequestException("C004",
                    HttpStatus.BAD_REQUEST,
                    "El DNI debe tener 8 dígitos");
        }

        return clientRepository.save(client);
    }

    @Override
    public Client updateCliente(Client client) {
        Client existingCliente = clientRepository.findById(client.getClientID())
                .orElseThrow(() -> new RequestException("C001",
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"));

        // Validar DNI único (excluyendo el cliente actual)
        clientRepository.findByDni(client.getDni()).ifPresent(c -> {
            if (!c.getClientID().equals(client.getClientID())) {
                throw new RequestException("C002",
                        HttpStatus.CONFLICT,
                        "El DNI ya está registrado");
            }
        });

        // Validar correo único (excluyendo el cliente actual)
        clientRepository.findByEmail(client.getEmail()).ifPresent(c -> {
            if (!c.getClientID().equals(client.getClientID())) {
                throw new RequestException("C003",
                        HttpStatus.CONFLICT,
                        "El correo ya está registrado");
            }
        });

        return clientRepository.save(client);
    }

    @Override
    public void deleteCliente(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RequestException("C001",
                    HttpStatus.NOT_FOUND,
                    "Cliente no encontrado");
        }
        clientRepository.deleteById(id);
    }

    @Override
    public Client getClienteByDni(String dni) {
        return clientRepository.findByDni(dni)
                .orElseThrow(() -> new RequestException("C001",
                        HttpStatus.NOT_FOUND,
                        "Cliente no encontrado"));
    }

    @Override
    public BonoMiViviendaDTO verificarBonoMiVivienda(Long clienteId, BigDecimal precioVivienda) {
        Client client = getClientById(clienteId);
        BonoMiViviendaDTO resultado = new BonoMiViviendaDTO();
        resultado.setPropertyPrice(precioVivienda);
        resultado.setMonthlyIncome(client.getMonthlyIncome());

        // Límite máximo del precio de vivienda según MiVivienda (en UIT)
        // Aproximadamente S/ 52,250 UIT * 5.3 UIT = S/ 276,925
        BigDecimal PRECIO_MAXIMO = new BigDecimal("276925");

        // Validar precio de vivienda
        if (precioVivienda.compareTo(PRECIO_MAXIMO) > 0) {
            resultado.setApplies(false);
            resultado.setReason("El precio de la vivienda supera el límite máximo de S/ 276,925 (5.3 UIT)");
            return resultado;
        }

        // Validar ingreso mínimo (aproximadamente 1 RMU = S/ 1,025)
        BigDecimal INGRESO_MINIMO = new BigDecimal("1025");
        if (client.getMonthlyIncome().compareTo(INGRESO_MINIMO) < 0) {
            resultado.setApplies(false);
            resultado.setReason("El ingreso mensual debe ser al menos S/ 1,025");
            return resultado;
        }

        // Validar ingreso máximo para Bono Familiar (2.5 UIT = S/ 13,062.50)
        BigDecimal INGRESO_MAXIMO_FAMILIAR = new BigDecimal("13062.50");

        // Determinar tipo de bono y monto
        if (client.getMonthlyIncome().compareTo(INGRESO_MAXIMO_FAMILIAR) <= 0) {
            // Bono Familiar
            resultado.setApplies(true);
            resultado.setTipobonusType("BONO FAMILIAR");

            // El monto del bono varía según ingreso y tipo de vivienda
            // Simplificado: entre S/ 37,600 y S/ 42,400
            BigDecimal BONO_FAMILIAR_MIN = new BigDecimal("37600");
            BigDecimal BONO_FAMILIAR_MAX = new BigDecimal("42400");

            // Calcular bono proporcional al ingreso
            BigDecimal proporcion = client.getMonthlyIncome()
                    .divide(INGRESO_MAXIMO_FAMILIAR, 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal montoBono = BONO_FAMILIAR_MIN.add(
                    BONO_FAMILIAR_MAX.subtract(BONO_FAMILIAR_MIN).multiply(proporcion)
            );

            resultado.setBonusAmount(montoBono);
            resultado.setReason("Califica para Bono Familiar - Se otorga un bono de S/ " +
                    montoBono.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            // Bono Buen Pagador (para ingresos mayores)
            BigDecimal INGRESO_MAXIMO_BUEN_PAGADOR = new BigDecimal("26125"); // 5 UIT

            if (client.getMonthlyIncome().compareTo(INGRESO_MAXIMO_BUEN_PAGADOR) <= 0) {
                resultado.setApplies(true);
                resultado.setTipobonusType("BONO BUEN PAGADOR");

                // Bono Buen Pagador: hasta S/ 25,000
                BigDecimal BONO_BUEN_PAGADOR = new BigDecimal("25000");
                resultado.setBonusAmount(BONO_BUEN_PAGADOR);
                resultado.setReason("Califica para Bono Buen Pagador - Se otorga un bono de S/ " +
                        BONO_BUEN_PAGADOR);
            } else {
                resultado.setApplies(false);
                resultado.setReason("El ingreso mensual supera el límite para acceder a bonos");
            }
        }

        return resultado;
    }
}
