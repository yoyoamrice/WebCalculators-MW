// file: /src/main/java/edu/kirkwood/model/Ingredient.java v2 - Complete Ingredient class with all methods

package edu.kirkwood.model.edward;

import edu.kirkwood.shared.Helpers;

import java.util.Objects;

import static edu.kirkwood.model.edward.IngredientCalculatorParameters.*;

/**
 * Ingredient class representing a cooking ingredient with quantity and unit
 */
public class Ingredient {
    private String name;
    private double quantity;
    private Unit unit;

    // ========== CONSTRUCTORS ==========
    /**
     * Primary constructor with all parameters
     * @param name The name of the ingredient
     * @param quantity The amount of the ingredient (must be positive)
     * @param unit The unit of measurement
     * @throws IllegalArgumentException if quantity is not positive or name is invalid
     */
    public Ingredient(String name, double quantity, Unit unit) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
        if (name.length() > MAX_INGREDIENT_NAME_LENGTH) {
            throw new IllegalArgumentException("Ingredient name too long (max " + MAX_INGREDIENT_NAME_LENGTH + " characters)");
        }
        if (quantity < -MAX_QUANTITY || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be between -" + MAX_QUANTITY + " and " + MAX_QUANTITY);
        }
        if (Math.abs(quantity) < MIN_QUANTITY && quantity != 0) {
            throw new IllegalArgumentException("Non-zero quantity must be at least " + MIN_QUANTITY + " in absolute value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.name = name.trim();
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Constructor with default name
     * @param quantity The amount of the ingredient
     * @param unit The unit of measurement
     */
    public Ingredient(double quantity, Unit unit) {
        this("Ingredient", quantity, unit);
    }

    /**
     * Copy constructor
     * @param other The ingredient to copy
     */
    public Ingredient(Ingredient other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy null ingredient");
        }
        this.name = other.name;
        this.quantity = other.quantity;
        this.unit = other.unit;
    }

    // ========== GETTERS ==========
    /**
     * Gets the ingredient name
     * @return The ingredient name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the ingredient quantity
     * @return The ingredient quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Gets the ingredient unit
     * @return The ingredient unit
     */
    public Unit getUnit() {
        return unit;
    }

    // ========== SETTERS ==========
    /**
     * Sets the ingredient name
     * @param name The new ingredient name
     * @throws IllegalArgumentException if name is invalid
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
        if (name.length() > MAX_INGREDIENT_NAME_LENGTH) {
            throw new IllegalArgumentException("Ingredient name too long");
        }
        this.name = name.trim();
    }

    /**
     * Sets the ingredient quantity
     * @param quantity The new quantity
     * @throws IllegalArgumentException if quantity is not positive
     */
    public void setQuantity(double quantity) {
        if (quantity < -MAX_QUANTITY || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be between -" + MAX_QUANTITY + " and " + MAX_QUANTITY);
        }
        if (Math.abs(quantity) < MIN_QUANTITY && quantity != 0) {
            throw new IllegalArgumentException("Non-zero quantity must be at least " + MIN_QUANTITY + " in absolute value");
        }
        this.quantity = quantity;
    }

    /**
     * Sets the ingredient unit
     * @param unit The new unit
     * @throws IllegalArgumentException if unit is null
     */
    public void setUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.unit = unit;
    }

    // ========== ARITHMETIC OPERATIONS ==========
    /**
     * Adds another ingredient to this one
     * @param other The ingredient to add
     * @return A new ingredient with the sum
     * @throws IllegalArgumentException if units are incompatible
     */
    public Ingredient add(Ingredient other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add null ingredient");
        }

        if (!this.unit.isCompatible(other.unit)) {
            throw new IllegalArgumentException(ERROR_INCOMPATIBLE_UNITS);
        }

        double otherQuantityConverted;
        if (this.unit == other.unit) {
            otherQuantityConverted = other.quantity;
        } else {
            UnitConverter converter = new UnitConverter();
            otherQuantityConverted = converter.convert(other.quantity, other.unit, this.unit);
        }

        double resultQuantity = this.quantity + otherQuantityConverted;
        return new Ingredient(this.name + " + " + other.name, resultQuantity, this.unit);
    }

    /**
     * Subtracts another ingredient from this one
     * @param other The ingredient to subtract
     * @return A new ingredient with the difference
     * @throws IllegalArgumentException if units are incompatible or result is negative
     */
    public Ingredient subtract(Ingredient other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot subtract null ingredient");
        }

        if (!this.unit.isCompatible(other.unit)) {
            throw new IllegalArgumentException(ERROR_INCOMPATIBLE_UNITS);
        }

        double otherQuantityConverted;
        if (this.unit == other.unit) {
            otherQuantityConverted = other.quantity;
        } else {
            UnitConverter converter = new UnitConverter();
            otherQuantityConverted = converter.convert(other.quantity, other.unit, this.unit);
        }

        double resultQuantity = this.quantity - otherQuantityConverted;

        // Allow negative results to track shortages/deficits
        // Only validate against reasonable bounds
        if (resultQuantity < -MAX_QUANTITY) {
            throw new IllegalArgumentException("Result quantity out of reasonable range");
        }

        return new Ingredient(this.name + " - " + other.name, resultQuantity, this.unit);
    }

    /**
     * Multiplies this ingredient by a scalar value
     * @param scalar The value to multiply by
     * @return A new ingredient with the multiplied quantity
     * @throws IllegalArgumentException if scalar is not positive
     */
    public Ingredient multiply(double scalar) {
        if (scalar <= 0) {
            throw new IllegalArgumentException("Scalar must be positive");
        }

        double resultQuantity = this.quantity * scalar;
        if (resultQuantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("Result quantity too large");
        }

        return new Ingredient(this.name + " × " + scalar, resultQuantity, this.unit);
    }

    /**
     * Divides this ingredient by a scalar value
     * @param scalar The value to divide by
     * @return A new ingredient with the divided quantity
     * @throws IllegalArgumentException if scalar is zero or negative
     */
    public Ingredient divide(double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException(ERROR_DIVISION_BY_ZERO);
        }
        if (scalar < 0) {
            throw new IllegalArgumentException("Scalar must be positive");
        }

        double resultQuantity = this.quantity / scalar;
        if (resultQuantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("Result quantity too small");
        }

        return new Ingredient(this.name + " ÷ " + scalar, resultQuantity, this.unit);
    }

    /**
     * Converts this ingredient to a different unit
     * @param targetUnit The unit to convert to
     * @return A new ingredient with the converted quantity and unit
     * @throws IllegalArgumentException if units are incompatible
     */
    public Ingredient convertTo(Unit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        if (!this.unit.isCompatible(targetUnit)) {
            throw new IllegalArgumentException(ERROR_INCOMPATIBLE_UNITS);
        }

        if (this.unit == targetUnit) {
            return new Ingredient(this); // Return copy if same unit
        }

        UnitConverter converter = new UnitConverter();
        double convertedQuantity = converter.convert(this.quantity, this.unit, targetUnit);

        return new Ingredient(this.name, convertedQuantity, targetUnit);
    }

    // ========== UTILITY METHODS ==========
    /**
     * Checks if this ingredient is compatible with another for arithmetic operations
     * @param other The other ingredient
     * @return true if compatible, false otherwise
     */
    public boolean isCompatibleWith(Ingredient other) {
        if (other == null) {
            return false;
        }
        return this.unit.isCompatible(other.unit);
    }

    /**
     * Gets the rounded quantity for display purposes
     * @return The quantity rounded to 3 decimal places
     */
    public double getRoundedQuantity() {
        return Math.round(this.quantity * 1000.0) / 1000.0;
    }

    /**
     * Creates a formatted quantity string
     * @return Formatted quantity string
     */
    public String getFormattedQuantity() {
        return Helpers.round(this.quantity, DECIMAL_PRECISION);
    }

    // ========== OVERRIDDEN METHODS ==========
    /**
     * Returns a string representation of the ingredient
     * @return String representation
     */
    @Override
    public String toString() {
        String quantityStr = getFormattedQuantity();
        // Add indicator for negative quantities (shortages)
        String prefix = this.quantity < 0 ? "SHORTAGE: " : "";
        return String.format("%s%s: %s %s",
                prefix,
                this.name,
                quantityStr,
                this.unit.getAbbreviation());
    }

    /**
     * Checks equality with another object
     * @param obj The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Ingredient that = (Ingredient) obj;
        return Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(name, that.name) &&
                unit == that.unit;
    }

    /**
     * Returns hash code for the ingredient
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unit);
    }

    /**
     * Creates a deep copy of this ingredient
     * @return A new ingredient copy
     */
    public Ingredient clone() {
        return new Ingredient(this);
    }
}