package edu.kirkwood.model;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Represents a Temperature with a type and a value
 * The class provides temperature conversions for
 * Fahrenheit, Celsius, and Kelvin
 */
public class HunterTemperature {

    private double temperature;
    private String temperatureType;
    private final String[] typeOptions = {"fahrenheit","celsius","kelvin","f","c","k"};
    private DecimalFormat decimalFormat;

    /**
     * Default constructor
     * Initializes as 0 degrees Fahrenheit
     */
    public HunterTemperature(){
        setTemperatureType("fahrenheit");
        setTemperature(0);
        decimalFormat = new DecimalFormat("#.00");
    }

    /**
     * Constructs a Temperature with a specified value and type
     * @param temperature the value of the temperature
     * @param temperatureType the unit being used for the temperature
     */
    public HunterTemperature(double temperature, String temperatureType) {
        setTemperatureType(temperatureType);
        setTemperature(temperature);
        decimalFormat = new DecimalFormat("#.00");
    }

    /**
     * Gets the temperature value of the Temperature
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Gets the temperature type of the Temperature
     * @return the temperature type
     */
    public String getTemperatureType() {
        return temperatureType;
    }

    /**
     * Set the temperature of the Temperature
     * @param temperature the new temperature
     * @throws IllegalArgumentException if the temperature is out of bounds
     */
    public void setTemperature(double temperature) {
        if((temperatureType.toLowerCase().equals("fahrenheit") || temperatureType.toLowerCase().equals("f"))
                && temperature >= -459.67){
            this.temperature = temperature;
        }
        else if ((temperatureType.toLowerCase().equals("celsius") || temperatureType.toLowerCase().equals("c"))
                && temperature >= -273.15) {
            this.temperature = temperature;
        }
        else if ((temperatureType.toLowerCase().equals("kelvin") || temperatureType.toLowerCase().equals("k"))
                && temperature >= 0) {
            this.temperature = temperature;
        }
        else {
            throw new IllegalArgumentException("Temperature out of range");
        }
    }

    /**
     * Sets the temperature type of the Temperature
     * @param temperatureType the new temperature type
     * @throws IllegalArgumentException if the inserted type is not a valid type
     */
    public void setTemperatureType(String temperatureType) {
        if(Arrays.asList(this.typeOptions).contains(temperatureType.toLowerCase())){
            this.temperatureType = temperatureType.toLowerCase();
        }
        else {
            throw new IllegalArgumentException("Temperature type is invalid");
        }
    }

    /**
     * Returns a string representation of Temperature in the format
     * type: value
     * @return a string representation of Temperature
     */
    @Override
    public String toString() {
        String result = temperatureType + ": " + temperature;
        return result;
    }

    /**
     * Returns the current temperature converted to Fahrenheit
     * @return temperature as converted to Fahrenheit
     */
    public double convertToFahrenheit(){
        double result = this.temperature;

        if(temperatureType.equals("celsius") ||
                temperatureType.equals("c")){
            // convert from celsius
            result = (this.temperature * (9.0/5.0)) + 32;
        }
        else if(temperatureType.equals("kelvin") ||
                temperatureType.equals("k")){
            //convert from kelvin
            result = (this.temperature * (9.0/5.0)) - 459.67;
        }

        return Format(result);
    }

    /**
     * Returns the current temperature converted to Celsius
     * @return temperature as converted to Celsius
     */
    public double convertToCelsius(){
        double result = this.temperature;

        if(temperatureType.equals("fahrenheit") ||
                temperatureType.equals("f")){
            //convert from fahrenheit
            result = (this.temperature - 32) / (9.0/5.0);
        }
        else if(temperatureType.equals("kelvin") ||
                temperatureType.equals("k")){
            //convert from kelvin
            result = (this.temperature) - 273.15;
        }

        return Format(result);
    }

    /**
     * Returns the current temperature converted to Kelvin
     * @return temperature as converted to Kelvin
     */
    public double convertToKelvin(){
        double result = this.temperature;

        if(temperatureType.equals("fahrenheit") ||
                temperatureType.equals("f")){
            //convert from fahrenheit
            result = (this.temperature + 459.67) * (5.0/9.0);
        }
        else if(temperatureType.equals("celsius") ||
                temperatureType.equals("c")){
            //convert from celsius
            result = this.temperature + 273.15;
        }

        return Format(result);
    }

    /**
     * Formats the string to 2 places after the decimal
     * @param input the number to be formatted
     * @return the newly formated button
     */
    public double Format(double input){
        double result = input;
        result = Double.parseDouble(decimalFormat.format(result));
        return result;
    }
}
