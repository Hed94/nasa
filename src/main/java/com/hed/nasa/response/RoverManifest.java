package com.hed.nasa.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class RoverManifest {
    private String name;
    private int maxSol;

    /*
    Metoda unpackuje JSON - bere určité proměné z rodiče - photo_manifest
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
