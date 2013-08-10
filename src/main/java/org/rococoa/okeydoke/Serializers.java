package org.rococoa.okeydoke;

import org.rococoa.okeydoke.serializers.BinarySerializer;
import org.rococoa.okeydoke.serializers.StringSerializer;

import java.nio.charset.Charset;

public class Serializers {

    private static final Serializer<String> string = new StringSerializer(Charset.forName("UTF-8"));
    private static final Serializer<byte[]> binary = new BinarySerializer();

    public static Serializer<String> stringSerializer() {
        return string;
    }

    public static Serializer<byte[]> binarySerializer() {
        return binary;
    }

}
