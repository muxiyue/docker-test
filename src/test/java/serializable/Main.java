package serializable;

import serializable.JavaSerialize.JavaSerialize;
import serializable.fastjson.FastJsonSerialize;
import serializable.jackson.JsonSerialize;
import serializable.protostuff.jprotobuf.ProtoBuffSerialize;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ProtoBuffSerialize protoBuffSerialize = new ProtoBuffSerialize();
        protoBuffSerialize.start();

        System.err.println();
        System.err.println();

        JavaSerialize javaSerialize = new JavaSerialize();
        javaSerialize.start();
        System.err.println();

        JsonSerialize jsonSerialize = new JsonSerialize();
        jsonSerialize.start();
        System.err.println();

        FastJsonSerialize fastJsonSerialize = new FastJsonSerialize();
        fastJsonSerialize.start();
    }
}