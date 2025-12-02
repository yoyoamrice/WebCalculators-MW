package edu.kirkwood.model;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents money with  decimal amount and string currency.
 * This class provides methods for money add, subtract, divide, multiply
 * and converting currencies
 */
public class Money {
    private double amount;
    private String currency;
    private static List<String> allowedCurrencies = Arrays.asList(new String[]{"USD", "GBP", "CAD"});

    private static double usdToGBP = 0.74741;
    private static double usdToCAD = 1.3956;
    private static double gbpToUSD = 1.3380;
    private static double gbpToCAD = 1.8673;
    private static double cadToUSD = 0.71654;
    private static double cadToGBP = 0.53553;

    public Money() {
       this.amount = 0.0;
       this.currency = "USD";

    }

    /**
     * this is a constructor for new money object
     *
     * @param amount   a double to be converted
     * @param currency string for the new currency value
     */
    public Money(double amount, String currency) {
        setAmount(amount);
        setCurrency(currency);
    }

    /**
     * this function getAmount
     *
     * @return amount double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * this function to set the amount
     *
     * @param amount a double
     */
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }


        this.amount = amount;
    }

    /**
     * this function to get currncy
     *
     * @return the currency string
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * this function setCurrency
     *
     * @param currency string for the new currency value
     */
    public void setCurrency(String currency) {
        if (!allowedCurrencies.contains(currency)) {
            throw new IllegalArgumentException("currency not allowed");
        }
        this.currency = currency;
    }

    /**
     * this function convertTo money from the amount
     *
     * @param exchangeRate   a double to be converted
     * @param targetCurrency string for the new currency value
     */
    public void convertTo(String targetCurrency, double exchangeRate) {
        this.amount = this.amount * exchangeRate;
        this.currency = targetCurrency;

    }

    /**
     * this function add money from the amount
     *
     * @param other is a double to be added
     */
    public void add(Money other) {
        if (!this.currency.equals(other.currency)) {
            if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount += (other.amount * cadToUSD);

            } else if (this.currency.equals("CAD") && other.currency.equals("USD")) {
                this.amount += other.amount * usdToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("USD")) {
                this.amount += other.amount * usdToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("GBP")) {
                this.amount += other.amount * gbpToUSD;
            } else if (this.currency.equals("CAD") && other.currency.equals("GBP")) {
                this.amount += other.amount * gbpToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("CAD")) {
                this.amount += other.amount * cadToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount += other.amount * cadToUSD;
            }
        } else {
            this.amount += other.amount;
        }


    }

    /**
     * this function subtract money from the amount
     *
     * @param other is a double to be subtracted
     */
    public void subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount -= (other.amount * cadToUSD);

            } else if (this.currency.equals("CAD") && other.currency.equals("USD")) {
                this.amount -= other.amount * usdToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("USD")) {
                this.amount -= other.amount * usdToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("GBP")) {
                this.amount -= other.amount * gbpToUSD;
            } else if (this.currency.equals("CAD") && other.currency.equals("GBP")) {
                this.amount -= other.amount * gbpToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("CAD")) {
                this.amount -= other.amount * cadToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount -= other.amount * cadToUSD;
            }
        } else {
            this.amount -= other.amount;
        }


    }

    /**
     * this is to convert to string
     *
     * @return string
     */
    @Override
    public String toString() {
        return amount + " " + currency;

    }

    /**
     * this is to multiply money
     */
    public void multiply(Money other) {
        if (!this.currency.equals(other.currency)) {
            if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount = this.amount * (other.amount * cadToUSD);

            } else if (this.currency.equals("CAD") && other.currency.equals("USD")) {
                this.amount *= this.amount * other.amount * usdToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("USD")) {
                this.amount *= this.amount * other.amount * usdToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("GBP")) {
                this.amount *= this.amount * other.amount * gbpToUSD;
            } else if (this.currency.equals("CAD") && other.currency.equals("GBP")) {
                this.amount *= this.amount * other.amount * gbpToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("CAD")) {
                this.amount *= this.amount * other.amount * cadToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount *= this.amount * other.amount * cadToUSD;
            }
        } else {
            this.amount = this.amount * other.amount;
        }

    }

    /**
     * this is to divide money
     */
    public void divide(Money other) {
        if (!this.currency.equals(other.currency)) {
            if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount = this.amount / (other.amount * cadToUSD);

            } else if (this.currency.equals("CAD") && other.currency.equals("USD")) {
                this.amount = this.amount / other.amount * usdToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("USD")) {
                this.amount = this.amount / other.amount * usdToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("GBP")) {
                this.amount = this.amount / other.amount * gbpToUSD;
            } else if (this.currency.equals("CAD") && other.currency.equals("GBP")) {
                this.amount = this.amount / other.amount * gbpToCAD;
            } else if (this.currency.equals("GBP") && other.currency.equals("CAD")) {
                this.amount = this.amount / other.amount * cadToGBP;
            } else if (this.currency.equals("USD") && other.currency.equals("CAD")) {
                this.amount = this.amount / other.amount * cadToUSD;
            }
        } else {
            this.amount = this.amount / other.amount;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(amount, money.amount) == 0 && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}


