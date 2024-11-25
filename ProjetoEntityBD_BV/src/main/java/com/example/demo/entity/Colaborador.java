package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @NotEmpty(message = "O nome deve ser informado!")
    @Length(min = 5, max = 200, message = "O nome deve ter de 5 a 200 caracteres")
    private String nome;

    @Column
    private String cpf;

    @Column(nullable = false)
    @NotEmpty(message = "O departamento deve ser informado!")
    @Length(min = 2, max = 100, message = "O departamento deve ter de 2 a 100 caracteres")
    private String departamento;

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
