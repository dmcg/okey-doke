package com.oneeyedmen.okeydoke.examples;

import java.util.LinkedList;

public class Calculator {
    private final LinkedList<Integer> stack = new LinkedList<Integer>();
    private int display;

    public void enter(int n) {
        stack.push(n);
    }

    public void add() {
        display = stack.pop() + stack.pop();
    }

    public String display() {
        return String.valueOf(display);
    }
}
