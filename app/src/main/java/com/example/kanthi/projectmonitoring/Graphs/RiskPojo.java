package com.example.kanthi.projectmonitoring.Graphs;

public class RiskPojo {
    private String Probability,impact;
    private int value;

    public String getProbability() {
        return Probability;
    }

    public void setProbability(String probability) {
        Probability = probability;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
