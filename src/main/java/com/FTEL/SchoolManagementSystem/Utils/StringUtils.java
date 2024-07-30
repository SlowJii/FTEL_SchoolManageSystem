package com.FTEL.SchoolManagementSystem.Utils;

import java.util.UUID;

public class StringUtils {
    public static String formatString(String str){
        if (str == null || str.isEmpty())
            return str;

        StringBuilder string = new StringBuilder();
        String[] res = str.trim().split("\\s+");
        for(String word :  res){
            char firstChar = Character.toUpperCase(word.charAt(0));
            String remaining = word.substring(1).toLowerCase();
            string.append(firstChar).append(remaining).append(" ");
        }
        return string.toString().trim();
    }

    public static String generateUserEmail(String firstName, String lastName) {
        String firstPara = formatString(lastName);
        String[] res = firstName.trim().split("\\s+");
        for (String word : res) {
            char firstChar = Character.toUpperCase(word.charAt(0));
            firstPara += firstChar;
        }
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Generate a random UUID
        String secondPara = uuid + "@fpt.com";
        return firstPara + secondPara;
    }
}
