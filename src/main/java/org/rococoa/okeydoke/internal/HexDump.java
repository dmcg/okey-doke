package org.rococoa.okeydoke.internal;


public class HexDump {

    final static private char[] DIGITS = new char[] {
        '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' ,
        '8' , '9' , 'A' , 'B' , 'C' , 'D' , 'E' , 'F'
    };

    public static String format(byte[] bytes) {
        return format(bytes, true, false);
    }

    public static String formatCompact(byte[] bytes) {
        return format(bytes, false, false);
    }

    public static String format(byte[] bytes, boolean spaces, boolean Oxs) {
        StringBuffer result = new StringBuffer();
        for (int i = 0, length = bytes.length; i < length; i++) {
            if (spaces && i != 0)
                result.append(' ');
            result.append(format(bytes[i], Oxs));
        }
        return result.toString();
    }

    public static String format(byte aByte, boolean Ox) {
        char top = DIGITS[ (aByte >> 4) & 0x0F ];
        char bottom = DIGITS[ aByte & 0x0F ];
        return (Ox == true ? "0x" : "") + top + bottom;
    }

    public static String format(byte aByte) {
        return format(aByte, false);
    }

    public static byte[] readCompact(String hexString) {
        if (hexString.length() % 2 != 0) {
            throw new NumberFormatException("Hex string " + hexString + " has odd character");
        }
        int resultLength = hexString.length() / 2;
        byte[] result = new byte[resultLength];
        for (int i = 0; i < resultLength; i++) {
            String pair = hexString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) Short.parseShort(pair, 16);
        }
        return result;
    }
}