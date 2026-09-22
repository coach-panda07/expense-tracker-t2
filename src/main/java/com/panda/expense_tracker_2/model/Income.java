package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Income")
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;


    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Income(String name, BigDecimal cost, Category category) {
        this.name = name;
        this.cost = cost;
    }

}
