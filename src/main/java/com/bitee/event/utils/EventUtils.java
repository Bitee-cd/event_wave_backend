package com.bitee.event.utils;

public class EventUtils {
    public static String generateRandomToken(){
        int randomPin = (int) Math.floor(Math.random() * 900000) + 100000; // Generate a 6-digit random number
        return String.valueOf(randomPin);
    }

    public static String EmailOtpBody(String username, String otp){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Your OTP Code</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding: 10px 0;\n" +
                "            background-color: #007bff;\n" +
                "            color: #ffffff;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "        .header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content p {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .otp {\n" +
                "            display: inline-block;\n" +
                "            background-color: #007bff;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 20px;\n" +
                "            font-size: 24px;\n" +
                "            letter-spacing: 2px;\n" +
                "            border-radius: 5px;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            padding: 10px;\n" +
                "            font-size: 12px;\n" +
                "            color: #777777;\n" +
                "            margin-top: 20px;\n" +
                "            border-top: 1px solid #eeeeee;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Your OTP Code</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Dear "+username+",</p>\n" +
                "            <p>Thank you for signing up with us. Please use the following One-Time Password (OTP) to complete your email verification:</p>\n" +
                "            <div class=\"otp\">"+ otp +"</div>\n" +
                "            <p>This OTP is valid for 10 minutes. Please do not share this code with anyone.</p>\n" +
                "            <p>If you did not request this email, please ignore it.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2024 Your Company. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
