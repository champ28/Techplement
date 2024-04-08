package org.Currencyconverter;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String> favoriteCurrenciesList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Main Menu:");
                System.out.println("1. Convert Currency");
                System.out.println("2. Manage Favorite Currencies");
                System.out.println("3. Show Favorite Currencies");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        handleCurrencyConversion(scanner);
                        break;
                    case 2:
                        manageFavoriteCurrencies(scanner);
                        break;
                    case 3:
                        showFavoriteCurrencies();
                        break;
                    case 4:
                        System.out.println("Exiting the application.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void showFavoriteCurrencies() {
        System.out.println("Favorite Currencies:");
        for (String favoriteCurrency : favoriteCurrenciesList) {
            System.out.println(favoriteCurrency);
        }
        System.out.println();
    }

    public static void handleCurrencyConversion(Scanner scanner) {
        try {
            System.err.println("Supported currencies ANG,TND,XCD,CAD,MVR,HRK,AUD,MWK,XAG,MAD,PHP,NAD,GNF,KES,MZN,BTN,MGA,AZN,XAU,RON,INR..............etc");
            System.out.print("Enter the source currency (e.g., USD): ");
            String fromCurrency = scanner.nextLine();

            System.out.print("Enter the target currency (e.g., EUR): ");
            String toCurrency = scanner.nextLine();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            String apiKey = "fa638069f1msh8be7098f728c7c6p173d56jsn1457ac7d06be"; // Your provided API key
            String result = convertCurrency(fromCurrency, toCurrency, amount, apiKey);

            System.out.println(amount +" "+ fromCurrency + " converts to " + getPrintValue(result) + " "+toCurrency);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getPrintValue(String  jsonStr){

        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getDouble("result");
    }



    public static String convertCurrency(String fromCurrency, String toCurrency, double amount, String apiKey) throws Exception {
        String host = "currency-conversion-and-exchange-rates.p.rapidapi.com";
        String endpoint = "convert";

        String url = String.format("https://%s/%s?from=%s&to=%s&amount=%.2f", host, endpoint, fromCurrency, toCurrency, amount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Currency conversion request failed with status code: " + response.statusCode());
        }
    }





    public static void manageFavoriteCurrencies(Scanner scanner) {
        while (true) {
            System.out.println("Favorite Currency Menu:");
            System.out.println("1. Add Favorite Currency");
            System.out.println("2. Update Favorite Currency");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addFavoriteCurrency(scanner);
                    break;
                case 2:
                    updateFavoriteCurrency(scanner);
                    break;
                case 3:
                    return; // Return to the main menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void addFavoriteCurrency(Scanner scanner) {
        System.out.print("Enter the Favorite Currency to add (e.g., USD): ");
        String favoriteCurrency = scanner.nextLine();
        favoriteCurrenciesList.add(favoriteCurrency);
        System.out.println("Favorite currency added.");
        System.out.println();
    }

    public static void updateFavoriteCurrency(Scanner scanner) {
        System.out.print("Enter the currency code you want to update: ");
        String currencyCodeToUpdate = scanner.nextLine();

        boolean currencyFound = false;
        for (int i = 0; i < favoriteCurrenciesList.size(); i++) {
            String currency = favoriteCurrenciesList.get(i);
            if (currency.startsWith(currencyCodeToUpdate)) {
                System.out.print("Enter the new Favorite Currency (e.g., USD): ");
                String newCurrency = scanner.nextLine();
                favoriteCurrenciesList.set(i, newCurrency);
                currencyFound = true;
                System.out.println("Favorite currency updated.");
                break; // Exit the loop once updated
            }
        }

        if (!currencyFound) {
            System.out.println("Currency code not found in favorites.");
        }
        System.out.println();


    }

}