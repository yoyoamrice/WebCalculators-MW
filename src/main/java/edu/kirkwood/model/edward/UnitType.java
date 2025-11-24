// file: /src/main/java/edu/kirkwood/model/UnitType.java v2 - Complete UnitType enum

package edu.kirkwood.model.edward;

/**
 * Unit type enumeration for categorizing measurement types
 */
public enum UnitType {
    VOLUME("Volume", "Liquid and dry volume measurements"),
    WEIGHT("Weight", "Mass and weight measurements");

    private final String displayName;
    private final String description;

    /**
     * Constructor for UnitType enum
     * @param displayName Human-readable name for the unit type
     * @param description Description of the unit type
     */
    UnitType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    // ========== GETTERS ==========
    /**
     * Gets the display name
     * @return The display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the description
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    // ========== UTILITY METHODS ==========
    /**
     * Finds a unit type by display name (case-insensitive)
     * @param displayName The display name to search for
     * @return The matching unit type, or null if not found
     */
    public static UnitType findByDisplayName(String displayName) {
        if (displayName == null) {
            return null;
        }

        for (UnitType type : values()) {
            if (type.displayName.equalsIgnoreCase(displayName.trim())) {
                return type;
            }
        }
        return null;
    }

    // ========== OVERRIDDEN METHODS ==========
    /**
     * Returns a string representation of the unit type
     * @return String representation with display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}
