package com.chefai.sugestor;
import com.chefai.model.*;

public abstract class SugestorBase {
    public abstract void sugerir(Usuario u)
            throws Exception;
}