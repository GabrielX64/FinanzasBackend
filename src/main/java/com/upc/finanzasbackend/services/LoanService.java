package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.ILoanService;
import com.upc.finanzasbackend.dtos.LoanInstallmentDTO;
import com.upc.finanzasbackend.dtos.LoanRequestDTO;
import com.upc.finanzasbackend.dtos.LoanResponseDTO;
import com.upc.finanzasbackend.entities.*;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService implements ILoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanInstallmentRepository loanInstallmentRepository;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CapitalizationFrequencyRepository capitalizationFrequencyRepository;
    @Autowired
    private EntityFinancialRepository entityFinancialRepository;

    private final MathContext MC = new MathContext(16, RoundingMode.HALF_UP);

    private LoanInstallment buildInstallment(Loan loan, int period,
                                             BigDecimal initial, BigDecimal interest,
                                             BigDecimal amort, BigDecimal fee,
                                             BigDecimal finalBal, BigDecimal cashFlow) {
        LoanInstallment li = new LoanInstallment();
        li.setLoan(loan);
        li.setPeriod(period);
        li.setInitialBalance(initial);
        li.setInterest(interest);
        li.setAmortization(amort);
        li.setFee(fee);
        li.setFinalBalance(finalBal);
        li.setCashFlow(cashFlow);
        return li;
    }

    private LoanInstallmentDTO toDto(LoanInstallment li) {
        LoanInstallmentDTO dto = new LoanInstallmentDTO();
        dto.setPeriod(li.getPeriod());
        dto.setInitialBalance(li.getInitialBalance());
        dto.setInterest(li.getInterest());
        dto.setAmortization(li.getAmortization());
        dto.setFee(li.getFee());
        dto.setFinalBalance(li.getFinalBalance());
        dto.setCashFlow(li.getCashFlow());
        return dto;
    }

    private BigDecimal calculateNPV(List<BigDecimal> cashFlows, BigDecimal cokAnnual) {
        if (cashFlows == null || cashFlows.isEmpty()) {
            return BigDecimal.ZERO;
        }

        double k = cokAnnual.doubleValue();
        double kPeriod = Math.pow(1 + k, 30.0 / 360.0) - 1.0;

        double npv = 0.0;
        for (int t = 0; t < cashFlows.size(); t++) {
            double cf = cashFlows.get(t).doubleValue();
            npv += cf / Math.pow(1 + kPeriod, t);
        }
        return BigDecimal.valueOf(npv);
    }

    private BigDecimal calculateIRR(List<BigDecimal> cashFlows) {
        if (cashFlows == null || cashFlows.size() < 2) {
            return BigDecimal.ZERO;
        }
        double r = 0.10;

        for (int iter = 0; iter < 100; iter++) {
            double npv = 0.0;
            double dnpv = 0.0;

            for (int t = 0; t < cashFlows.size(); t++) {
                double cf = cashFlows.get(t).doubleValue();
                npv += cf / Math.pow(1 + r, t);
                if (t > 0) {
                    dnpv += -t * cf / Math.pow(1 + r, t + 1);
                }
            }
            if (Math.abs(dnpv) < 1e-12) break;

            double rNext = r - npv / dnpv;
            if (Math.abs(rNext - r) < 1e-12) {
                r = rNext;
                break;
            }
            r = rNext;
        }

        double irrAnnual = Math.pow(1 + r, 12) - 1;
        return BigDecimal.valueOf(irrAnnual);
    }

    @Override
    public LoanResponseDTO createFrenchLoan(LoanRequestDTO dto) {
        Client client = clientRepository.findById(dto.getClientID())
                .orElseThrow(() -> new RequestException("C001",
                        HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        UserApp asesor = null;
        if (dto.getAsesorID() != null) {
            asesor = userAppRepository.findById(dto.getAsesorID())
                    .orElseThrow(() -> new RequestException("U001",
                            HttpStatus.NOT_FOUND, "Asesor no encontrado"));
        }

        FinancialEntity entidad = entityFinancialRepository.findById(dto.getFinancialEntityID())
                .orElseThrow(() -> new RequestException("EF001",
                        HttpStatus.NOT_FOUND, "Entidad financiera no encontrada"));

        BigDecimal precioVivienda = dto.getPropertyPrice();
        BigDecimal porcentajeCuota = entidad.getDownPaymentPercentage();
        BigDecimal cuotaInicial = precioVivienda.multiply(porcentajeCuota)
                .divide(new BigDecimal("100"), MC);
        BigDecimal montoFinanciado = precioVivienda.subtract(cuotaInicial);

        double teaEff;
        CapitalizationFrequency capFreq = null;

        if ("TNP".equalsIgnoreCase(dto.getRateType())) {
            if (dto.getCapitalizationFrequencyID() == null) {
                throw new RequestException("F000",
                        HttpStatus.BAD_REQUEST,
                        "Debe indicar la frecuencia de capitalización para TNP");
            }

            capFreq = capitalizationFrequencyRepository.findById(dto.getCapitalizationFrequencyID())
                    .orElseThrow(() -> new RequestException("F001",
                            HttpStatus.NOT_FOUND,
                            "Frecuencia de capitalización no encontrada"));

            int m = capFreq.getPeriodsPerYear();
            double j = dto.getTnp().doubleValue();
            teaEff = Math.pow(1 + j / m, m) - 1.0;
        } else {
            teaEff = dto.getTea().doubleValue();
        }

        double iPeriod = Math.pow(1 + teaEff, 30.0 / 360.0) - 1.0;
        BigDecimal iBD = BigDecimal.valueOf(iPeriod);

        Loan loan = new Loan();
        loan.setClientID(client);
        loan.setAsesor(asesor);
        loan.setFinancialEntity(entidad);
        loan.setPrincipal(dto.getPrincipal());
        loan.setPropertyPrice(precioVivienda);
        loan.setDownPayment(cuotaInicial);
        loan.setDownPaymentPercentage(porcentajeCuota);
        loan.setTea(BigDecimal.valueOf(teaEff));
        loan.setRateType(dto.getRateType());
        loan.setTnp(dto.getTnp());
        loan.setYears(dto.getYears());
        loan.setFrequencyPerYear(12);
        loan.setTotalGrace(dto.getTotalGrace());
        loan.setPartialGrace(dto.getPartialGrace());
        loan.setCok(dto.getCok());

        if (capFreq != null) {
            loan.setCapitalizationFrequency(capFreq);
        }

        loan = loanRepository.save(loan);

        int Ntotal = dto.getYears() * 12;
        int gTotal = dto.getTotalGrace();
        int gParcial = dto.getPartialGrace();

        BigDecimal balance = dto.getPrincipal();
        List<LoanInstallment> installments = new ArrayList<>();
        List<BigDecimal> cashFlows = new ArrayList<>();

        cashFlows.add(balance);

        for (int k = 1; k <= gTotal; k++) {
            BigDecimal initial = balance;
            BigDecimal interest = initial.multiply(iBD, MC);
            BigDecimal amort = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal finalBal = initial.add(interest);

            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, fee, finalBal, BigDecimal.ZERO
            );
            installments.add(li);
            cashFlows.add(BigDecimal.ZERO);
            balance = finalBal;
        }

        for (int k = gTotal + 1; k <= gTotal + gParcial; k++) {
            BigDecimal initial = balance;
            BigDecimal interest = initial.multiply(iBD, MC);
            BigDecimal amort = BigDecimal.ZERO;
            BigDecimal fee = interest;
            BigDecimal finalBal = initial;

            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, fee, finalBal, fee.negate()
            );
            installments.add(li);
            cashFlows.add(fee.negate());
            balance = finalBal;
        }

        int Nnormal = Ntotal - gTotal - gParcial;
        if (Nnormal <= 0) {
            throw new RequestException("L001", HttpStatus.BAD_REQUEST,
                    "No hay periodos suficientes para amortizar el préstamo");
        }

        double B0 = balance.doubleValue();
        double factor = Math.pow(1 + iPeriod, Nnormal);
        double cuotaD = B0 * (iPeriod * factor / (factor - 1));
        BigDecimal cuota = BigDecimal.valueOf(cuotaD);

        int start = gTotal + gParcial + 1;
        for (int k = start; k <= Ntotal; k++) {
            BigDecimal initial = balance;
            BigDecimal interest = initial.multiply(iBD, MC);
            BigDecimal amort = cuota.subtract(interest);
            BigDecimal finalBal = initial.subtract(amort);

            if (k == Ntotal) {
                amort = initial;
                cuota = interest.add(amort);
                finalBal = BigDecimal.ZERO;
            }

            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, cuota, finalBal, cuota.negate()
            );
            installments.add(li);
            cashFlows.add(cuota.negate());
            balance = finalBal;
        }

        loanInstallmentRepository.saveAll(installments);

        BigDecimal van = calculateNPV(cashFlows, dto.getCok());
        BigDecimal tirAnnual = calculateIRR(cashFlows);
        BigDecimal tcea = tirAnnual;

        loan.setVan(van);
        loan.setTir(tirAnnual);
        loan.setTcea(tcea);
        loanRepository.save(loan);

        LoanResponseDTO response = new LoanResponseDTO();
        response.setLoanId(loan.getLoanID());
        response.setVan(van);
        response.setTir(tirAnnual);
        response.setTcea(tcea);
        response.setPropertyPrice(precioVivienda);
        response.setDownPayment(cuotaInicial);
        response.setDownPaymentPercentage(porcentajeCuota);
        response.setFinancedAmount(montoFinanciado);
        response.setFinancialEntity(entidad.getName());
        response.setSchedule(
                installments.stream().map(this::toDto).collect(Collectors.toList())
        );
        return response;
    }

    @Override
    public List<LoanInstallmentDTO> getScheduleByLoanId(Long loanID) {
        return loanInstallmentRepository.findByLoan_LoanID(loanID)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Loan> getLoansByUser(Long userID) {
        return loanRepository.findByAsesor_UserAppID(userID);
    }

    @Override
    public List<Loan> getLoanByClient(Long clientID) {
        return loanRepository.findByClientID_ClientID(clientID);
    }
}
