package com.oneeyedmen.okeydoke;

import com.oneeyedmen.okeydoke.checkers.BinaryChecker;
import com.oneeyedmen.okeydoke.checkers.HexChecker;
import com.oneeyedmen.okeydoke.checkers.StringChecker;

public class Checkers {

    private static final Checker<String> string = new StringChecker();
    private static final Checker<byte[]> binary = new BinaryChecker();
    private static final Checker<byte[]> hex = new HexChecker();

    public static Checker<String> stringChecker() {
        return string;
    }

    public static Checker<byte[]> binaryChecker() {
        return binary;
    }

    public static Checker<byte[]> hexChecker() {
        return hex;
    }

}
