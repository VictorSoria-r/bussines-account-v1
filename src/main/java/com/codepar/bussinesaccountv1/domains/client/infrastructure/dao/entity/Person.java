package com.codepar.bussinesaccountv1.domains.client.infrastructure.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Person {

    @Column(name = "NAME")
    protected String name;
    @Column(name = "IDENTIFICATION")
    protected String identification;
    @Column(name = "ADDRESS")
    protected String address;
    @Column(name = "PHONE_NUMBER")
    protected String phoneNumber;
}
