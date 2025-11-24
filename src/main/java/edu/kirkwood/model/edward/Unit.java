// file: /src/main/java/edu/kirkwood/model/Unit.java v2 - Complete Unit enum with all methods

package edu.kirkwood.model.edward;

import static edu.kirkwood.model.edward.IngredientCalculatorParameters.*;

/**
 * Unit enumeration with all measurement types and conversion capabilities
 */
public enum Unit {
    // Volume units (base: teaspoons)
    TSP(UnitType.VOLUME, TSP_TO_TSP, "tsp", "teaspoon"),
    TBSP(UnitType.VOLUME, TBSP_TO_TSP, "tbsp", "tablespoon"),
    FL_OZ(UnitType.VOLUME, FL_OZ_TO_TSP, "fl oz", "fluid ounce"),
    CUP(UnitType.VOLUME, CUP_TO_TSP, "cup", "cup"),
    PINT(UnitType.VOLUME, PINT_TO_TSP, "pint", "pint"),
    QUART(UnitType.VOLUME, QUART_TO_TSP, "quart", "quart"),

    // Weight units (base: grams)
    OZ(UnitType.WEIGHT, OZ_TO_GRAM, "oz", "ounce"),
    LB(UnitType.WEIGHT, LB_TO_GRAM, "lb", "pound"),
    GRAM(UnitType.WEIGHT, GRAM_TO_GRAM, "g", "gram"),
    KILOGRAM(UnitType.WEIGHT, KG_TO_GRAM, "kg", "kilogram");

    private final UnitType type;
    private final double conversionFactor;
    private final String abbreviation;
    private final String fullName;

    /**
     * Constructor for Unit enum
     * @param type The type of unit (VOLUME or WEIGHT)
     * @param conversionFactor Factor to convert to base unit
     * @param abbreviation Short form display name
     * @param fullName Full display name
     */
    Unit(UnitType type, double conversionFactor, String abbreviation, String fullName) {
        this.type = type;
        this.conversionFactor = conversionFactor;
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }

    // ========== GETTERS ==========
    /**
     * Gets the unit type
     * @return The unit type (VOLUME or WEIGHT)
     */
    public UnitType getType() {
        return type;
    }

    /**
     * Gets the conversion factor to base unit
     * @return The conversion factor
     */
    public double getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Gets the abbreviation
     * @return The unit abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Gets the full name
     * @return The full unit name
     */
    public String getFullName() {
        return fullName;
    }

    // ========== UTILITY METHODS ==========
    /**
     * Checks if this unit is compatible with another unit
     * @param other The other unit
     * @return true if compatible (same type), false otherwise
     */
    public boolean isCompatible(Unit other) {
        if (other == null) {
            return false;
        }
        return this.type == other.type;
    }

    /**
     * Checks if this is a volume unit
     * @return true if volume unit, false otherwise
     */
    public boolean isVolumeUnit() {
        return this.type == UnitType.VOLUME;
    }

    /**
     * Checks if this is a weight unit
     * @return true if weight unit, false otherwise
     */
    public boolean isWeightUnit() {
        return this.type == UnitType.WEIGHT;
    }

    /**
     * Gets the base unit for this unit's type
     * @return TSP for volume units, GRAM for weight units
     */
    public Unit getBaseUnit() {
        return this.type == UnitType.VOLUME ? TSP : GRAM;
    }

    /**
     * Converts a quantity from this unit to the base unit
     * @param quantity The quantity in this unit
     * @return The quantity in base units
     */
    public double toBaseUnit(double quantity) {
        return quantity * this.conversionFactor;
    }

    /**
     * Converts a quantity from the base unit to this unit
     * @param baseQuantity The quantity in base units
     * @return The quantity in this unit
     */
    public double fromBaseUnit(double baseQuantity) {
        return baseQuantity / this.conversionFactor;
    }

    /**
     * Gets all volume units
     * @return Array of volume units
     */
    public static Unit[] getVolumeUnits() {
        return new Unit[]{TSP, TBSP, FL_OZ, CUP, PINT, QUART};
    }

    /**
     * Gets all weight units
     * @return Array of weight units
     */
    public static Unit[] getWeightUnits() {
        return new Unit[]{OZ, LB, GRAM, KILOGRAM};
    }

    /**
     * Finds a unit by its abbreviation (case-insensitive)
     * @param abbreviation The abbreviation to search for
     * @return The matching unit, or null if not found
     */
    public static Unit findByAbbreviation(String abbreviation) {
        if (abbreviation == null) {
            return null;
        }

        for (Unit unit : values()) {
            if (unit.abbreviation.equalsIgnoreCase(abbreviation.trim())) {
                return unit;
            }
        }
        return null;
    }

    /**
     * Finds a unit by its full name (case-insensitive)
     * @param fullName The full name to search for
     * @return The matching unit, or null if not found
     */
    public static Unit findByFullName(String fullName) {
        if (fullName == null) {
            return null;
        }

        for (Unit unit : values()) {
            if (unit.fullName.equalsIgnoreCase(fullName.trim())) {
                return unit;
            }
        }
        return null;
    }

    // ========== OVERRIDDEN METHODS ==========
    /**
     * Returns a string representation of the unit
     * @return String representation with abbreviation and full name
     */
    @Override
    public String toString() {
        return String.format("%s (%s)", abbreviation, fullName);
    }
}
