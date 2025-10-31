package com.hubpedro.bookstoreapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
// 1. Habilita a auditoria para esta entidade
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    // Dentro da classe Loan
    private static final BigDecimal LATE_FEE_BASE = new BigDecimal("5.00"); // Taxa base de R$5,00 por atraso
    private static final BigDecimal DAILY_FINE_RATE = new BigDecimal("0.50"); // Multa de R$0,50 por dia de atraso

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference // Indica o lado que NÃO será serializado
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

	@CreatedDate
    private LocalDate createdAt;

	@LastModifiedDate
    private LocalDate lastModifiedAt;

    private LocalDate loanedAt;
    private LocalDate dueOn;
    private LocalDate returnedAt;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private BigDecimal loanDebt;

    /**
     * Processa a devolução de um empréstimo, calculando possíveis multas.
     *
     * @param actualReturnDate A data em que a devolução está sendo efetivamente realizada.
     */
    public void processReturn(LocalDate actualReturnDate) {
        if (this.status == LoanStatus.RETURNED || this.status == LoanStatus.OVERDUE) {
            throw new IllegalStateException("Este empréstimo já foi devolvido.");
        }

        this.returnedAt = actualReturnDate;

        if (actualReturnDate.isAfter(this.dueOn)) {
            this.loanDebt = calculateLateFee(actualReturnDate);
            this.status = LoanStatus.OVERDUE; // 2. Define o status final como ATRASADO
        } else {
            this.loanDebt = BigDecimal.ZERO;
            this.status = LoanStatus.RETURNED; // 3. Define o status final como DEVOLVIDO (no prazo)
        }
    }

    /**
     * Calcula a multa com base na data de devolução.
     *
     * @param actualReturnDate A data de devolução.
     * @return O valor da multa calculada.
     */
    private BigDecimal calculateLateFee(LocalDate actualReturnDate) {
        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(this.dueOn, actualReturnDate);
        return LATE_FEE_BASE.add(DAILY_FINE_RATE.multiply(new BigDecimal(daysLate)));
    }

    @PrePersist
    private void onCreate() {
        this.status = LoanStatus.ACTIVE;
    }

}