package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Log_Book")
@NoArgsConstructor
public class Logbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "logbook", cascade = CascadeType.REMOVE)
    private Income income;

    @OneToMany(mappedBy = "logbook", cascade = CascadeType.REMOVE)
    private List<Expense> expense;

    public Logbook(String name) {
        this.name = name;

    }
}
