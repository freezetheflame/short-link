package com.example.short_link.utils;

public class Base62 {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
//        System.out.println(value);
//        System.out.println("wow");
        while (value > 0) {
            sb.append(BASE62_CHARS.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return sb.reverse().toString();
    }

    public static long decode(String str) {
        long value = 0;
        for (char c : str.toCharArray()) {
            value = value * BASE + BASE62_CHARS.indexOf(c);
        }
        return value;
    }
}