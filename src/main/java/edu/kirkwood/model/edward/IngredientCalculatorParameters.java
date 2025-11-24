// file: src/main/java/edu/kirkwood/model/IngredientCalculatorParameters.java v1 - Core parameters for Ingredient Calculator

package edu.kirkwood.model.edward;


/**
 * Represents a Calculator that performs addition, subtraction, multiplication,
 * and division on ingredients. Will also convert ingredients between imperial
 * and metric based on volume and weight measurements.
 */
public class IngredientCalculatorParameters {


    // ========== PRECISION PARAMETERS ==========
    public static final int DECIMAL_PRECISION = 3;
    public static final double EPSILON = 0.001; // For floating point comparisons

    // ========== PERFORMANCE PARAMETERS ==========
    public static final int MAX_OPERATION_TIME_MS = 100;
    public static final int MAX_STARTUP_TIME_MS = 2000;

    // ========== VOLUME UNIT CONVERSION FACTORS (to teaspoons as base) ==========
    public static final double TSP_TO_TSP = 1.0;
    public static final double TBSP_TO_TSP = 3.0;
    public static final double FL_OZ_TO_TSP = 6.0;
    public static final double CUP_TO_TSP = 48.0;
    public static final double PINT_TO_TSP = 96.0;
    public static final double QUART_TO_TSP = 192.0;

    // ========== WEIGHT UNIT CONVERSION FACTORS (to grams as base) ==========
    public static final double GRAM_TO_GRAM = 1.0;
    public static final double OZ_TO_GRAM = 28.3495;
    public static final double LB_TO_GRAM = 453.592;
    public static final double KG_TO_GRAM = 1000.0;

    // ========== USER INTERFACE PARAMETERS ==========
    public static final int MENU_OPTION_CREATE_INGREDIENT = 1;
    public static final int MENU_OPTION_PERFORM_ARITHMETIC = 2;
    public static final int MENU_OPTION_DISPLAY_INGREDIENTS = 3;
    public static final int MENU_OPTION_CONVERT_UNITS = 4;
    public static final int MENU_OPTION_EXIT = 5;

    public static final int ARITHMETIC_OPTION_ADD = 1;
    public static final int ARITHMETIC_OPTION_SUBTRACT = 2;
    public static final int ARITHMETIC_OPTION_MULTIPLY = 3;
    public static final int ARITHMETIC_OPTION_DIVIDE = 4;

    // ========== VALIDATION PARAMETERS ==========
    public static final double MIN_QUANTITY = 0.001;
    public static final double MAX_QUANTITY = 999999.999;
    public static final int MAX_INGREDIENT_NAME_LENGTH = 50;
    public static final int MIN_INGREDIENT_NAME_LENGTH = 1;
    public static final int MAX_INGREDIENTS_STORED = 100;

    // ========== ERROR HANDLING PARAMETERS ==========
    public static final String ERROR_INCOMPATIBLE_UNITS = "Error: Cannot perform operation on incompatible unit types (volume vs weight)";
    public static final String ERROR_DIVISION_BY_ZERO = "Error: Division by zero is not allowed";
    public static final String ERROR_INVALID_QUANTITY = "Error: Quantity must be a positive number";
    public static final String ERROR_INVALID_UNIT = "Error: Invalid unit specified";
    public static final String ERROR_INVALID_MENU_CHOICE = "Error: Please enter a valid menu option";
    public static final String ERROR_INGREDIENT_NOT_FOUND = "Error: Ingredient not found";
    public static final String ERROR_MAX_INGREDIENTS_REACHED = "Error: Maximum number of ingredients reached";

    // ========== DISPLAY FORMATTING PARAMETERS ==========
    public static final String QUANTITY_FORMAT = "%.3f";
    public static final String MENU_SEPARATOR = "=".repeat(50);
    public static final String RESULT_PREFIX = "Result: ";
    public static final String INPUT_PROMPT = "> ";

    // ========== UNIT DISPLAY NAMES ==========
    public static final String[] VOLUME_UNIT_NAMES = {
            "tsp (teaspoon)", "tbsp (tablespoon)", "fl oz (fluid ounce)",
            "cup", "pint", "quart"
    };

    public static final String[] WEIGHT_UNIT_NAMES = {
            "oz (ounce)", "lb (pound)", "g (gram)", "kg (kilogram)"
    };

    // ========== SCANNER INPUT PARAMETERS ==========
    public static final String INPUT_DELIMITER = "\\s+";
    public static final boolean CASE_SENSITIVE_UNITS = false;

    // ========== APPLICATION METADATA ==========
    public static final String APP_NAME = "Ingredient Calculator";
    public static final String APP_VERSION = "1.0";
    public static final String WELCOME_MESSAGE = "Welcome to " + APP_NAME + " v" + APP_VERSION;
    public static final String EXIT_MESSAGE = "Thank you for using " + APP_NAME + "!";

    // Private constructor to prevent instantiation
    private IngredientCalculatorParameters() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }










































}
