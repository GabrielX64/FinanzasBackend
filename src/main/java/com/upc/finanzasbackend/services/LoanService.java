package com.upc.finanzasbackend.services;

import com.upc.finanzasbackend.Interfaces.ILoanService;
import com.upc.finanzasbackend.dtos.LoanInstallmentDTO;
import com.upc.finanzasbackend.dtos.LoanRequestDTO;
import com.upc.finanzasbackend.dtos.LoanResponseDTO;
import com.upc.finanzasbackend.entities.Loan;
import com.upc.finanzasbackend.entities.LoanInstallment;
import com.upc.finanzasbackend.entities.UserApp;
import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.repositories.LoanInstallmentRepository;
import com.upc.finanzasbackend.repositories.LoanRepository;
import com.upc.finanzasbackend.repositories.UserAppRepository;
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

        // COK periodo (mes de 30 días, año 360)
        double k = cokAnnual.doubleValue();                   // ej. 0.27
        double kPeriod = Math.pow(1 + k, 30.0 / 360.0) - 1.0; // COK mensual 30/360

        double npv = 0.0;
        for (int t = 0; t < cashFlows.size(); t++) {
            double cf = cashFlows.get(t).doubleValue();
            npv += cf / Math.pow(1 + kPeriod, t);             // t = 0..N
        }
        return BigDecimal.valueOf(npv);
    }

    private BigDecimal calculateIRR(List<BigDecimal> cashFlows) {
        if (cashFlows == null || cashFlows.size() < 2) {
            return BigDecimal.ZERO;
        }
        // Tasa inicial (guess) estilo Excel: 10%
        double r = 0.10;

        for (int iter = 0; iter < 100; iter++) {
            double npv = 0.0;
            double dnpv = 0.0; // derivada del NPV

            for (int t = 0; t < cashFlows.size(); t++) {
                double cf = cashFlows.get(t).doubleValue();
                npv += cf / Math.pow(1 + r, t);

                if (t > 0) {
                    dnpv += -t * cf / Math.pow(1 + r, t + 1);
                }
            }
            // Si la derivada se vuelve 0, evitamos división por cero
            if (Math.abs(dnpv) < 1e-12) {
                break;
            }
            double rNext = r - npv / dnpv;
            if (Math.abs(rNext - r) < 1e-12) {
                r = rNext;
                break;
            }
            r = rNext;
        }
        // Convertimos de tasa periódica (mensual) a anual 30/360
        double irrAnnual = Math.pow(1 + r, 12) - 1;
        return BigDecimal.valueOf(irrAnnual);
    }

    @Override
    public LoanResponseDTO createFrenchLoan(LoanRequestDTO dto) {
        // -------- 1. Usuario ----------
        UserApp user = userAppRepository.findById(dto.getUserID())
                .orElseThrow(() -> new RequestException("U001",
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // -------- 2. Calcular TEA efectiva (desde TEA o TNP) e iPeriod (30/360) --------
        double teaEff;
        double iPeriod;

        if ("TNP".equalsIgnoreCase(dto.getRateType())) {
            double j = dto.getTnp().doubleValue();           // TNP anual decimal
            int m = dto.getCapitalizationsPerYear();         // ej. 12

            teaEff = Math.pow(1 + j / m, m) - 1.0;          // TEA equivalente
        } else {
            teaEff = dto.getTea().doubleValue();            // TEA directa
        }

        iPeriod = Math.pow(1 + teaEff, 30.0 / 360.0) - 1.0; // TEM mensual 30/360
        BigDecimal iBD = BigDecimal.valueOf(iPeriod);

        // -------- 3. Guardar cabecera del préstamo --------
        Loan loan = new Loan();
        loan.setUserID(user);
        loan.setPrincipal(dto.getPrincipal());
        loan.setTea(BigDecimal.valueOf(teaEff));            // TEA efectiva equivalente
        loan.setRateType(dto.getRateType());                // "TEA" o "TNP"
        loan.setTnp(dto.getTnp());                          // puede ser null si TEA
        loan.setCapitalizationsPerYear(dto.getCapitalizationsPerYear());
        loan.setYears(dto.getYears());
        loan.setFrequencyPerYear(12);                       // mensual
        loan.setTotalGrace(dto.getTotalGrace());
        loan.setPartialGrace(dto.getPartialGrace());
        loan.setCok(dto.getCok());
        loan = loanRepository.save(loan);

        int Ntotal = dto.getYears() * 12;
        int gTotal = dto.getTotalGrace();
        int gParcial = dto.getPartialGrace();

        // -------- 4. Inicialización de cronograma y flujos --------
        BigDecimal balance = dto.getPrincipal();
        List<LoanInstallment> installments = new ArrayList<>();
        List<BigDecimal> cashFlows = new ArrayList<>();

        // Punto de vista del CLIENTE:
        // CF0 = +principal (recibe dinero)
        cashFlows.add(balance);

        // -------- 5. Gracia total: capitaliza interés, no paga nada --------
        for (int k = 1; k <= gTotal; k++) {
            BigDecimal initial = balance;
            BigDecimal interest = initial.multiply(iBD, MC);
            BigDecimal amort = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            BigDecimal finalBal = initial.add(interest);    // se capitaliza

            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, fee, finalBal, BigDecimal.ZERO
            );
            installments.add(li);
            cashFlows.add(BigDecimal.ZERO);                 // cliente no paga
            balance = finalBal;
        }

        // -------- 6. Gracia parcial: paga solo intereses --------
        for (int k = gTotal + 1; k <= gTotal + gParcial; k++) {
            BigDecimal initial = balance;
            BigDecimal interest = initial.multiply(iBD, MC);
            BigDecimal amort = BigDecimal.ZERO;
            BigDecimal fee = interest;                      // cuota = interés
            BigDecimal finalBal = initial;                  // saldo se mantiene

            // desde el cliente: flujo negativo (pago)
            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, fee, finalBal, fee.negate()
            );
            installments.add(li);
            cashFlows.add(fee.negate());
            balance = finalBal;
        }

        // -------- 7. Etapa normal: cuota francesa constante --------
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

            // última cuota: corregir redondeo para dejar saldo 0
            if (k == Ntotal) {
                amort = initial;
                cuota = interest.add(amort);
                finalBal = BigDecimal.ZERO;
            }

            // desde el cliente: flujo negativo (pago de cuota)
            LoanInstallment li = buildInstallment(
                    loan, k, initial, interest, amort, cuota, finalBal, cuota.negate()
            );
            installments.add(li);
            cashFlows.add(cuota.negate());
            balance = finalBal;
        }

        loanInstallmentRepository.saveAll(installments);

        // -------- 8. VAN / TIR / TCEA con cashFlows y COK --------
        BigDecimal van = calculateNPV(cashFlows, dto.getCok());
        BigDecimal tirAnnual = calculateIRR(cashFlows);
        BigDecimal tcea = tirAnnual; // TCEA = TIR anual de los flujos del cliente

        loan.setVan(van);
        loan.setTir(tirAnnual);
        loan.setTcea(tcea);
        loanRepository.save(loan);

        // -------- 9. Armar response ----------
        LoanResponseDTO response = new LoanResponseDTO();
        response.setLoanId(loan.getLoanID());
        response.setVan(van);
        response.setTir(tirAnnual);
        response.setTcea(tcea);
        response.setSchedule(
                installments.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
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
        return loanRepository.findByUserID_UserAppID(userID);
    }
}
