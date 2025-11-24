// file: /src/main/java/edu/kirkwood/model/UnitConverter.java v2 - Complete UnitConverter utility class

package edu.kirkwood.model.edward;

import static edu.kirkwood.model.edward.IngredientCalculatorParameters.*;

/**
 * Utility class for converting between different units
 */
public class UnitConverter {

    /**
     * Default constructor
     */
    public UnitConverter() {
        // Empty constructor - all methods are instance-based
    }

    // ========== CONVERSION METHODS ==========
    /**
     * Converts a quantity from one unit to another
     * @param quantity The quantity to convert
     * @param fromUnit The source unit
     * @param toUnit The target unit
     * @return The converted quantity
     * @throws IllegalArgumentException if units are incompatible
     */
    public double convert(double quantity, Unit fromUnit, Unit toUnit) {
        if (fromUnit == null || toUnit == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!areCompatible(fromUnit, toUnit)) {
            throw new IllegalArgumentException(ERROR_INCOMPATIBLE_UNITS);
        }

        if (fromUnit == toUnit) {
            return quantity;
        }

        // Convert to base unit, then to target unit
        double baseQuantity = getBaseConversion(quantity, fromUnit);
        return fromBaseConversion(baseQuantity, toUnit);
    }

    /**
     * Checks if two units are compatible for conversion
     * @param unit1 First unit
     * @param unit2 Second unit
     * @return true if compatible, false otherwise
     */
    public boolean areCompatible(Unit unit1, Unit unit2) {
        if (unit1 == null || unit2 == null) {
            return false;
        }
        return unit1.getType() == unit2.getType();
    }

    /**
     * Converts quantity to base unit for the unit type
     * @param quantity The quantity to convert
     * @param unit The source unit
     * @return The quantity in base units
     */
    private double getBaseConversion(double quantity, Unit unit) {
        return unit.toBaseUnit(quantity);
    }

    /**
     * Converts quantity from base unit to target unit
     * @param baseQuantity The quantity in base units
     * @param unit The target unit
     * @return The quantity in target units
     */
    private double fromBaseConversion(double baseQuantity, Unit unit) {
        return unit.fromBaseUnit(baseQuantity);
    }

    // ========== UTILITY METHODS ==========
    /**
     * Gets the conversion factor between two compatible units
     * @param fromUnit Source unit
     * @param toUnit Target unit
     * @return The conversion factor
     * @throws IllegalArgumentException if units are incompatible
     */
    public double getConversionFactor(Unit fromUnit, Unit toUnit) {
        if (!areCompatible(fromUnit, toUnit)) {
            throw new IllegalArgumentException(ERROR_INCOMPATIBLE_UNITS);
        }

        if (fromUnit == toUnit) {
            return 1.0;
        }

        return fromUnit.getConversionFactor() / toUnit.getConversionFactor();
    }

    /**
     * Rounds a converted value to the specified precision
     * @param value The value to round
     * @param decimalPlaces Number of decimal places
     * @return The rounded value
     */
    public double roundToPrecision(double value, int decimalPlaces) {
        double factor = Math.pow(10, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

    /**
     * Converts and rounds to standard precision
     * @param quantity The quantity to convert
     * @param fromUnit The source unit
     * @param toUnit The target unit
     * @return The converted and rounded quantity
     * @throws IllegalArgumentException if units are incompatible
     */
    public double convertAndRound(double quantity, Unit fromUnit, Unit toUnit) {
        double converted = convert(quantity, fromUnit, toUnit);
        return roundToPrecision(converted, DECIMAL_PRECISION);
    }

    /**
     * Gets all possible conversions for a given unit
     * @param unit The source unit
     * @return Array of compatible units for conversion
     */
    public Unit[] getCompatibleUnits(Unit unit) {
        if (unit == null) {
            return new Unit[0];
        }

        return unit.isVolumeUnit() ? Unit.getVolumeUnits() : Unit.getWeightUnits();
    }

    /**
     * Validates if a conversion is possible and safe
     * @param quantity The quantity to convert
     * @param fromUnit Source unit
     * @param toUnit Target unit
     * @return true if conversion is valid, false otherwise
     */
    public boolean isValidConversion(double quantity, Unit fromUnit, Unit toUnit) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            return false;
        }

        if (!areCompatible(fromUnit, toUnit)) {
            return false;
        }

        try {
            double result = convert(quantity, fromUnit, toUnit);
            return result >= MIN_QUANTITY && result <= MAX_QUANTITY;
        } catch (Exception e) {
            return false;
        }
    }

    // ========== OVERRIDDEN METHODS ==========
    /**
     * Returns a string representation of the converter
     * @return String representation
     */
    @Override
    public String toString() {
        return "UnitConverter[precision=" + DECIMAL_PRECISION + "]";
    }
}