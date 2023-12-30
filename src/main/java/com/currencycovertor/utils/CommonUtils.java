package com.currencycovertor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommonUtils {

	 public static String callServerGetReq(String url,String host) {
	        try {
	            URL serverUrl = new URL(url);
	            HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

	            connection.setRequestMethod("GET");

	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setRequestProperty("X-RapidAPI-Host", host);
	            connection.setRequestProperty("X-RapidAPI-Key", "a3a1c5e300msh04eb29d4f6b7d15p1870e0jsnfdb8ca2a0733");

	            int responseCode = connection.getResponseCode();

	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String inputLine;
	                StringBuilder response = new StringBuilder();

	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();

	                return response.toString();
	            } else {
	                System.out.println("Error: " + responseCode);
	                return null;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
		public static boolean isNotBlank(String str) {
			return str != null && str.trim().length() > 0;
		}
		
		public static boolean isNotEmpty(List<?> list) {
			return !list.isEmpty() && list != null;
		}
}
