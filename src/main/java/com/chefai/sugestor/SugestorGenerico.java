package com.chefai.sugestor;
import com.chefai.api.*; import com.chefai.model.*;

public class SugestorGenerico extends SugestorBase{
    public void sugerir(Usuario u)throws Exception{
        LLMClient.enviarPrompt(PromptBuilder.criarPrompt(u, "generico"));
    }
}