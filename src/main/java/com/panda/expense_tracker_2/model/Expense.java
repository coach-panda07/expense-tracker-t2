package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Expense")
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logbook_id")
    private Logbook logbook;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getCost() { return cost; }

    public void setCost(BigDecimal cost) { this.cost = cost; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public Logbook getLogbook() { return logbook; }

    public void setLogbook(Logbook logbook) { this.logbook = logbook; }

    public Expense(String name, BigDecimal cost, LocalDate date, Category category, Logbook logbook) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.category = category;
        this.logbook = logbook;
    }

}