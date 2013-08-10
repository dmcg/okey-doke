package org.rococoa.okeydoke;

import org.rococoa.okeydoke.checkers.BinaryChecker;
import org.rococoa.okeydoke.checkers.HexChecker;
import org.rococoa.okeydoke.checkers.StringChecker;

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
