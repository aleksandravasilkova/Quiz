package com.javatpoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Product implements Comparable<Product> {
    String name;
    boolean domestic;
    double price;

    String weight;

    String description;

    public Product(String name, boolean domestic, double price, String weight, String description) {
        this.name = name;
        this.domestic = domestic;
        this.price = price;
        this.weight= weight;
        this.description= description;
    }

    @Override
    public int compareTo(Product p) {
        return this.name.compareTo(p.name);
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://interview-task-api.mca.dev/qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        List<Product> domesticProducts = new ArrayList<>();
        List<Product> importedProducts = new ArrayList<>();

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }
        in.close();

        JSONArray jsonArray = new JSONArray(sb.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String weight;
            if(jsonObject.has("weight")){
                weight= jsonObject.getInt("weight") + "g";
            }
            else
                weight = "N/A";
            Product  product = new Product(jsonObject.getString("name"), jsonObject.getBoolean("domestic"), jsonObject.getDouble("price"), weight , jsonObject.getString("description"));
            if (product.domestic) {
                domesticProducts.add(product);
            } else {
                importedProducts.add(product);
            }
        }

        Collections.sort(domesticProducts);
        Collections.sort(importedProducts);
        double costd =0.0;
        double costi = 0.0;
        for (Product productd : domesticProducts) {
            costd += productd.price;
        }
        for (Product producti : importedProducts) {
            costi += producti.price;
        }
        printProducts("Domestic", domesticProducts);
        printProducts("Imported", importedProducts);
        System.out.println(String.format("Domestic " + " cost: $" + costd).replace(".",","));
        System.out.println(String.format("Imported " + " cost: $" + costi).replace(".",","));
        System.out.println("Domestic " + " count: " + domesticProducts.size());
        System.out.println("Imported " + " count: " + importedProducts.size());

    }

    private static void printProducts(String type, List<Product> products) {
        System.out.println(". " + type);
        for (Product product : products) {
            System.out.println("... " + product.name);
            System.out.println(String.format("\tPrice: $" + product.price).replace(".", ","));
            if(product.description.length()>10) {
                System.out.println("\t" + product.description.substring(0, 10) + "...");
            }
            else
                System.out.println("\t" + product.description);

            System.out.println("\tWeight: " + product.weight);


        }




    }


}

