package com.bitee.event.utils;

public class EventUtils {
    public static String generateRandomToken(){
        int randomPin = (int) Math.floor(Math.random() * 900000) + 100000; // Generate a 6-digit random number
        return String.valueOf(randomPin);
    }
}
