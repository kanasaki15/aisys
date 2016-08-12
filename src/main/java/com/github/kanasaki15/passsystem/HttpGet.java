package com.github.kanasaki15.passsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpGet {
	public static String StringGet(String u){
		String a = "-1";
		try {
			URL url = new URL(u);
			HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8);
							BufferedReader reader = new BufferedReader(isr)) {
							String line;
							while ((line = reader.readLine()) != null) {
								if (line != null){
									a += line;
								}
							}
							}
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			} catch (IOException e) {
				a = "-1";
			}
		return a;
	}
}
