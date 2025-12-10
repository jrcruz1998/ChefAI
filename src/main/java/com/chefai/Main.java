package com.chefai;
import com.chefai.model.*;
import com.chefai.sugestor.*;
import com.chefai.util.*;
import java.util.*;

public class Main {

    public static void main(String[] args){

        Usuario u = new Usuario();
        System.out.println("=== ChefAI ===");

        while(true){
            String nome=InputHelper.lerLinha("Ingrediente (ENTER para sair): ");
            if(nome.isEmpty()) break;
            double q=InputHelper.lerDouble("Quantidade: ");
            u.adicionarIngrediente(new Ingrediente(nome,q));
        }
        u.setRestricao(InputHelper.lerLinha("Restrição (ex: vegetariano): "));
        u.setTipoRefeicao(InputHelper.lerLinha("Tipo de Refeição (ex: almoço): "));
        int op = Integer.parseInt(InputHelper.lerLinha("1-Rápido 2-Saudável 3-Genérico: \n"));
        SugestorBase s = switch(op){
            case 1 -> new SugestorRapido();
            case 2 -> new SugestorSaudavel();
            default -> new SugestorGenerico();};

        System.out.println("\n--- Buscando Sugestões com LLM ---");
        try{
            s.sugerir(u);
        } catch(Exception e){
            System.err.println("\n!!! ERRO NO SISTEMA OU API !!!");
            System.err.println("Detalhe: "+e.getMessage());
        }
    }
}