package com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity;

import com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOVEMENT")
public class MovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MOVEMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVEMENT_GEN")
    @SequenceGenerator(name = "MOVEMENT_GEN", sequenceName = "CP_MOVEMENT_GEN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDate;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private AccountEntity account;

}
