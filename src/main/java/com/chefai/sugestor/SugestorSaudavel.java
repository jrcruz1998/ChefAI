package com.chefai.sugestor;
import com.chefai.api.*; import com.chefai.model.*;

public class SugestorSaudavel extends SugestorBase{
    public void sugerir(Usuario u)throws Exception{
        LLMClient.enviarPrompt(PromptBuilder.criarPrompt(u, "saudavel"));
    }
}