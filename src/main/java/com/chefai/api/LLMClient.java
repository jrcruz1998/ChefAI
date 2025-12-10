package com.chefai.api;
import com.chefai.exceptions.APIException;
import com.chefai.model.Receita;
import com.fasterxml.jackson.databind.*;
import okhttp3.*;
import java.nio.file.*; import java.util.*;

public class LLMClient {
    private static final String apiKey;
    private static final String apiUrl;
    private static final String model;
    static{
        try{
            Properties p=new Properties();
            p.load(Files.newInputStream(Paths.get("config/api.properties")));
            apiKey=p.getProperty("API_KEY");
            apiUrl=p.getProperty("API_URL");
            model=p.getProperty("MODEL");
        }catch(Exception e){throw new RuntimeException("Erro config API");}
    }
    public static void enviarPrompt(String prompt) throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // corpo JSON no formato de chat completions do Groq/OpenAI
        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        String bodyJson = mapper.writeValueAsString(payload);

        RequestBody body = RequestBody.create(
                bodyJson,
                MediaType.parse("application/json")
        );

        Request req = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response r = client.newCall(req).execute()) {
            if (!r.isSuccessful()) {
                throw new APIException("Falha API: " + r.code() + " - " + r.message());
            }

            assert r.body() != null;
            String respJson = r.body().string();

            parseReceitasFromGroq(respJson, mapper);
        }
    }

    private static void parseReceitasFromGroq(String respJson, ObjectMapper mapper) throws Exception {
        List<Receita> receitas = new ArrayList<>();

        // 1. Extração do conteúdo de texto puro da LLM
        JsonNode root = mapper.readTree(respJson);
        JsonNode choices = root.path("choices");
        if (!choices.isArray() || choices.isEmpty()) {
            throw new Exception("Resposta inválida da API (sem 'choices').");
        }
        String content = choices.get(0).path("message").path("content").asText().trim();

        // 2. Divisão do texto em blocos de receita
        String[] receitasText = content.split("--- RECEITA ---");

        for (String receitaText : receitasText) {
            if (receitaText.trim().isEmpty()) continue; // Pula blocos vazios

            try {
                // 3. Extração dos campos usando o auxiliar
                String nome = extractValue(receitaText, "NOME:");
                String ingredientesStr = extractValue(receitaText, "INGREDIENTES:");
                String modoPreparo = extractValue(receitaText, "MODO_PREPARO:");
                String tempoStr = extractValue(receitaText, "TEMPO:");

                // 4. Conversão e criação do objeto
                List<String> ingredientes = Arrays.stream(ingredientesStr.split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();

                int tempo = Integer.parseInt(tempoStr.replaceAll("[^0-9]", "").trim());

                if (!nome.isEmpty()) { // Checagem mínima
                    receitas.add(new Receita(nome, ingredientes, modoPreparo, tempo));
                }

            } catch (Exception e) {
                System.err.println("Erro ao analisar bloco de receita. Verifique se o LLM seguiu o formato de texto corretamente. Detalhe: " + e.getMessage());
            }
        }

        if (!receitas.isEmpty()) {
            System.out.println("\n=== Sugestões Encontradas ===");
            for(int i=0; i<receitas.size(); i++){
                imprimirReceita(i+1, receitas.get(i));
            }
        } else {
            throw new Exception("Falha ao analisar a resposta do LLM. Nenhuma receita extraída.");
        }

    }

    private static void imprimirReceita(int num, Receita r){
        System.out.println("\n-------------------------------------");
        System.out.println("Receita #" + num + ": " + r.getNome());
        System.out.println("Tempo Estimado: " + r.getTempo() + " minutos");
        // ATENÇÃO: Se a Main não tem acesso à classe Ingrediente, use String para os ingredientes.
        System.out.println("\nIngredientes Necessários (Você tem os itens abaixo):");
        for(String ing : r.getIngredientes()){
            System.out.println("* " + ing);
        }
        System.out.println("\nModo de Preparo Passo-a-Passo:");
        System.out.println(r.getModoPreparo());
        System.out.println("-------------------------------------");
    }

    private static String extractValue(String text, String startMarker) {
        int startIndex = text.indexOf(startMarker);
        if (startIndex == -1) return "";

        int endOfValue = text.length();

        List<String> markers = Arrays.asList("NOME:", "INGREDIENTES:", "MODO_PREPARO:", "TEMPO:");

        for(String marker : markers){
            if (!marker.equals(startMarker)) {
                int nextMarkerIndex = text.indexOf(marker, startIndex + 1);
                if (nextMarkerIndex != -1) {
                    endOfValue = Math.min(endOfValue, nextMarkerIndex);
                }
            }
        }

        String value = text.substring(startIndex + startMarker.length(), endOfValue);
        return value.trim();
    }
}