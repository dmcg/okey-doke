package com.oneeyedmen.okeydoke.pickle;

public class FeatureInfo {

    private final String name;
    private final String asA;
    private final String inOrder;
    private final String want;

    public static FeatureInfo featureNamed(String name) {
        return new FeatureInfo(name, "", "", "");
    }

    public FeatureInfo(String name, String asA, String inOrder, String want) {
        this.name = name;
        this.inOrder = inOrder;
        this.asA = asA;
        this.want = want;
    }

    public FeatureInfo asA(String newAsA) {
        return new FeatureInfo(name, newAsA, inOrder, want);
    }

    public FeatureInfo inOrder(String newInOrder) {
        return new FeatureInfo(name, asA, newInOrder, want);
    }

    public FeatureInfo iWant(String newWant) {
        return new FeatureInfo(name, asA, inOrder, newWant);
    }

    public String name() {
        return name;
    }

    public String asA() {
        return asA;
    }

    public String inOrder() {
        return inOrder;
    }

    public String iWant() {
        return want;
    }
}
