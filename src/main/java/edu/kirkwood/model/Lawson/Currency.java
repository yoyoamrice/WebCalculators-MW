package edu.kirkwood.model.Lawson;


import java.util.HashMap;
import java.util.Map;

public class Currency {
    private Double amount;
    private String currency;

    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 1.1);
        exchangeRates.put("JPY", 0.0067);
        exchangeRates.put("GBP", 1.3);
    }

    public Currency() {}

    // This javadoc comment was written by human hands
    /**
     * Makes a currency object with a currency code and the amount.
     *
     * @param amount   the amount
     * @param currency the currency code (e.g. "USD", "EUR")
     */
    public Currency(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency.toUpperCase();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency.toUpperCase();
    }

    // This javadoc comment was written by human hands.
    /**
     * Takes in a target currency then converts into it.
     *
     * @param targetCurrency the currency to convert to
     * @return the converted amount
     */
    public Double convert(String targetCurrency) {
        targetCurrency = targetCurrency.toUpperCase();
        if (!exchangeRates.containsKey(this.currency) || !exchangeRates.containsKey(targetCurrency)) {
            throw new IllegalArgumentException("That was not a currency we support");
        }
        double amountInUSD = this.amount * exchangeRates.get(this.currency);
        return amountInUSD / exchangeRates.get(targetCurrency);
    }
    /**
     * Parses a currency-formatted string (e.g. "$123.45") to a double.
     *
     * @param currencyStr the formatted string
     * @return the numeric value
     */
    public double parseCurrency(String currencyStr) {
        return Double.parseDouble(currencyStr.replaceAll("[^\\d.]", ""));
    }
    
    public static boolean isSupported(String currency) {
        return exchangeRates.containsKey(currency.toUpperCase());
    }

    /**
     * Simplifies the object's amount to two decimal places.
     */
    public void simplify() {
        this.amount = simplify(this.amount);
    }

    /**
     * Simplifies a given amount to two decimal places.
     *
     * @param amount the amount to round
     * @return the rounded amount
     */
    public Double simplify(Double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    /**
     * Adds two amounts with different currencies and returns the result in the first currency.
     *
     * @param amount1      the first amount
     * @param currency1    the first currency
     * @param amount2      the second amount
     * @param currency2    the second currency
     * @return the summed amount in the first currency
     */
    public Double addDifferentCurrencies(Double amount1, String currency1, Double amount2, String currency2) {
        Currency c1 = new Currency(amount1, currency1);
        Currency c2 = new Currency(amount2, currency2);
        double amount2Converted = c2.convert(currency1);
        return simplify(amount1 + amount2Converted);
    }

    /**
     * Subtracts two amounts with different currencies and returns the result in the first currency.
     *
     * @param amount1      the first amount
     * @param currency1    the first currency
     * @param amount2      the second amount
     * @param currency2    the second currency
     * @return the difference in the first currency
     */
    public Double subDifferentCurrencies(Double amount1, String currency1, Double amount2, String currency2) {
        Currency c1 = new Currency(amount1, currency1);
        Currency c2 = new Currency(amount2, currency2);
        double amount2Converted = c2.convert(currency1);
        return simplify(amount1 - amount2Converted);
    }

    /**
     * Converts a given amount to USD using the specified currency.
     *
     * @param amount   the amount to convert
     * @param currency the currency of the amount
     * @return the amount in USD
     */
    public Double multiplyDifferentCurrencies(Double amount, String currency) {
        if (!exchangeRates.containsKey(currency.toUpperCase())) {
            throw new IllegalArgumentException("Unsupported currency");
        }
        return simplify(amount * exchangeRates.get(currency.toUpperCase()));
    }

    /**
     * Divides a given amount by the exchange rate of the specified currency.
     *
     * @param amount   the amount to divide
     * @param currency the currency to use for division
     * @return the result after division
     */
    public Double divideDifferentCurrencies(Double amount, String currency) {
        if (!exchangeRates.containsKey(currency.toUpperCase())) {
            throw new IllegalArgumentException("Unsupported currency");
        }
        return simplify(amount / exchangeRates.get(currency.toUpperCase()));
    }
}
