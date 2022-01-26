package com.example.converter_currency.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "currencies")
@Accessors(chain = true)
public class Currency {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;
    @Column
    private String numCode;
    @Column
    private String charCode;
    @Column
    private int nominal;
    @Column
    private String name;
}
