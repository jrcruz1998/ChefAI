package com.chefai.model;
import java.util.List;
public class Receita {
    private final String nome;
    private final List<String> ingredientes;
    private final String modoPreparo;
    private final int tempo;

    public Receita(String n,List<String> i,String m,int t){
        nome=n;
        ingredientes=i;
        modoPreparo=m;
        tempo=t;
    }
    public String getNome(){
        return nome;
    }
    public List<String> getIngredientes(){
        return ingredientes;
    }
    public String getModoPreparo(){
        return modoPreparo;
    }
    public int getTempo(){
        return tempo;
    }
}