package com.paulrenn.foursquarevenues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtil {
    public static String readTestFile(Object obj, String filename) throws IOException {
        return readString(obj.getClass().getClassLoader().getResourceAsStream("test.json"));
    }

    public static String readString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is), 4096);
        StringBuilder sb =  new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        br.close();
        return sb.toString();
    }
}
