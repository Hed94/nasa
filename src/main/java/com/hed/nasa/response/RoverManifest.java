package com.hed.nasa.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/*
RoverManifest je datový objekt, který přenáší data z NASA API do frontendu
Díky tomuto objektu frontendový slider ví, kolik solárních dnů bylo jaké vozítko na Marsu
Atribut maxSol tedy říká ten počet dní
 */

public class RoverManifest {
    private String name;
    private int maxSol;

    /*
    Metoda unpackuje JSON - bere určité proměné z rodiče - photo_manifest
    Protože Api vrací všechny údaje obalené v rodiči photo_manifest
     */
    @JsonProperty("photo_manifest")
    private void unpackProperties(Map<String, Object> manifest) {
        name = (String)manifest.get("name");
        maxSol = (Integer)manifest.get("max_sol");
    }

    @Override
    public String toString() {
        return "RoverManifest{" +
                "name='" + name + '\'' +
                ", maxSol=" + maxSol +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }
}
