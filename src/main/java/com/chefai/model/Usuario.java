package com.chefai.model;
import java.util.*;
public class Usuario {

    private final List<Ingrediente> ingredientes=new ArrayList<>();
    private String restricao="nenhuma";
    private String tipoRefeicao="qualquer";
    public void adicionarIngrediente(Ingrediente ing){
        ingredientes.add(ing);
    }
    public List<Ingrediente> getIngredientes(){
        return ingredientes;
    }
    public String getRestricao(){
        return restricao;
    }
    public void setRestricao(String r){
        restricao=r;
    }
    public String getTipoRefeicao(){
        return tipoRefeicao;
    }
    public void setTipoRefeicao(String t){
        tipoRefeicao=t;
    }
}