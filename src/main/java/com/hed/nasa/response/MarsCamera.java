package com.hed.nasa.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
MarsCamera je objekt, který mapuje z NASA API hodnoty o kamerách.
V projektu zatím není nijak využit.
Nicméně když už ty data člověk z Api má, proč je neskladovat pro budoucí využití.
 */

public class MarsCamera {
    private Long id;
    private String name;
    @JsonProperty("rover_id")
    private Long roverId;
    @JsonProperty("full_name")
    private String fullName;

    public MarsCamera() {
    }

    @Override
    public String toString() {
        return "MarsCamera{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roverId=" + roverId +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoverId() {
        return roverId;
    }

    public void setRoverId(Long roverId) {
        this.roverId = roverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
