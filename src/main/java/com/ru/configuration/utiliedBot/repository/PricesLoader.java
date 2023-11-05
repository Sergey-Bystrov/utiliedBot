package com.ru.configuration.utiliedBot.repository;

import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class PricesLoader {
    private Map<String, Float> priceValues = new HashMap<>();
    private String dataFileName;
    public PricesLoader() {

        this.dataFileName = "src/main/java/com/ru/configuration/utiliedBot/repository/prices.txt";
    }

    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    float value = Float.parseFloat(parts[1].trim());
                    priceValues.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFileName))) {
            for (Map.Entry<String, Float> entry : priceValues.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putValue(String key, float value) {
        priceValues.put(key, value);
        saveDataToFile();
    }

    public BigDecimal getValue(String key) {
        return BigDecimal.valueOf(priceValues.get(key));
    }

    public void removeValue(String key) {
        priceValues.remove(key);
        saveDataToFile();
    }
}
