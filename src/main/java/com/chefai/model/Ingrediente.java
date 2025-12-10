package com.chefai.model;
public class Ingrediente {
    private final String nome;
    private final double quantidade;

    public Ingrediente(String nome, double quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public String toString() {
        return nome + " (" + quantidade + ")";
    }
}