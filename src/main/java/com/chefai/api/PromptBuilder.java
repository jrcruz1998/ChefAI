package com.chefai.api;
import com.chefai.model.*;

public class PromptBuilder {
    public static String criarPrompt(Usuario usuario,String tipo){
        StringBuilder sb = new StringBuilder();
        sb.append("Gere 3 receitas baseadas nos ingredientes:\n");
        for(Ingrediente ing : usuario.getIngredientes()) {
            sb.append("- ").append(ing.getNome()).append(" (").append(ing.getQuantidade()).append(")\n");
        }
        sb.append("\nRequisitos:\n- Tempo máximo: 30 minutos\n- Cozinha doméstica\n- Estilo: ")
                .append(tipo).append("\n- Restrição alimentar: ").append(usuario.getRestricao())
                .append("\n- Tipo de refeição: ").append(usuario.getTipoRefeicao());

        // Instrução para formato de texto
        sb.append("""
                INSTRUÇÃO DE FORMATO: Responda APENAS com as 3 receitas, separando cada receita com a linha '--- RECEITA ---'. \
                Use os seguintes delimitadores para cada campo:
                """);
        sb.append("NOME: ...\n");
        sb.append("INGREDIENTES: Item 1 (destacar o que o usuario já tem); Item 2; ...\n");
        sb.append("MODO_PREPARO: ... (passo a passo)\n");
        sb.append("TEMPO: ... (coloque o número de minutos junto com a álavra minutos)\n");
        sb.append("Não inclua nenhum texto adicional além do formato solicitado.\n");

        return sb.toString();
    }
}