package com.codepar.bussinesaccountv1.domains.account.infrastructure.dao.entity;

import com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity.ClientEntity;
import com.codepar.bussinesaccountv1.domains.movements.infrastructure.dao.entity.MovementEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_GEN")
    @SequenceGenerator(name = "ACCOUNT_GEN", sequenceName = "CP_ACCOUNT_GEN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "INITIAL_BALANCE")
    private BigDecimal initialBalance;

    @Column(name = "STATUS")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY, mappedBy = "account")
    private List<MovementEntity> movementEntities;

}
