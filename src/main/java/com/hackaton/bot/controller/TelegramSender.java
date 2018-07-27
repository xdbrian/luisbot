package com.hackaton.bot.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelegramSender {
  // TODO: Replace the following with your instance ID, Premium Client ID and Secret:
  private static final String INSTANCE_ID = "657527630";
  private static final String CLIENT_ID = "657527630";
  private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET_HERE";
  private static final String GATEWAY_URL = "http://api.whatsmate.net/v1/telegram/single/message/" + INSTANCE_ID;

  /**
   * Entry Point
   */
  public static void main(String[] args) throws Exception {
    String number = "12025550108";  // Specify the recipient's number (NOT the gateway number) here.
    String message = "Have a nice day! Loving you:)";  // FIXME

    TelegramSender.sendMessage(number, message);
  }

  /**
   * Sends out a Telegram message via WhatsMate Telegram Gateway.
   */
  public static void sendMessage(String number, String message) throws Exception {
    // TODO: Should have used a 3rd party library to make a JSON string from an object
    String jsonPayload = new StringBuilder()
            .append("{")
            .append("\"number\":\"")
            .append(number)
            .append("\",")
            .append("\"message\":\"")
            .append(message)
            .append("\"")
            .append("}")
            .toString();

    URL url = new URL(GATEWAY_URL);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(jsonPayload.getBytes());
    os.flush();
    os.close();

    int statusCode = conn.getResponseCode();
    System.out.println("Response from Telegram Gateway: \n");
    System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(new InputStreamReader(
            (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
    ));
    String output;
    while ((output = br.readLine()) != null) {
      System.out.println(output);
    }
    conn.disconnect();
  }

}
