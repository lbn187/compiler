package com.IR;

public class Immediate extends Value {
    public int value;
    public Immediate(int val){
        value=val;
    }
    public String toString() {
        return String.valueOf(value);
    }
}
