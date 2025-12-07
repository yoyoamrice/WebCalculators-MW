// file: /src/main/java/edu/kirkwood/controller/IngredientServlet.java v2 - Enhanced result display, subtraction uses same ingredient

package edu.kirkwood.controller;

import edu.kirkwood.model.edward.Ingredient;
import edu.kirkwood.model.edward.Unit;
import edu.kirkwood.model.edward.UnitConverter;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ingredient")
public class IngredientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/ingredient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get form parameters
        String name1 = req.getParameter("name1");
        String quantity1Str = req.getParameter("quantity1");
        String unit1Str = req.getParameter("unit1");

        String operator = req.getParameter("operator");

        String name2 = req.getParameter("name2");
        String quantity2Str = req.getParameter("quantity2");
        String unit2Str = req.getParameter("unit2");

        String scalarStr = req.getParameter("scalar");

        // Set values back to form
        req.setAttribute("name1", name1);
        req.setAttribute("quantity1", quantity1Str);
        req.setAttribute("unit1", unit1Str);
        req.setAttribute("operator", operator);
        req.setAttribute("name2", name2);
        req.setAttribute("quantity2", quantity2Str);
        req.setAttribute("unit2", unit2Str);
        req.setAttribute("scalar", scalarStr);

        // Validation flags
        boolean hasErrors = false;

        // Validate first ingredient name
        if (!Helpers.isValidString(name1) || name1.trim().isEmpty()) {
            req.setAttribute("name1Error", "First ingredient name is required");
            hasErrors = true;
        }

        // Validate first ingredient quantity
        double quantity1 = 0;
        if (!Helpers.isValidNumber(quantity1Str)) {
            req.setAttribute("quantity1Error", "First quantity must be a valid number");
            hasErrors = true;
        } else {
            quantity1 = Double.parseDouble(quantity1Str);
            if (quantity1 <= 0) {
                req.setAttribute("quantity1Error", "Quantity must be a positive number");
                hasErrors = true;
            } else if (quantity1 > 999999.999) {
                req.setAttribute("quantity1Error", "Quantity must be 999999.999 or less");
                hasErrors = true;
            } else if (quantity1 < 0.001) {
                req.setAttribute("quantity1Error", "Quantity must be at least 0.001");
                hasErrors = true;
            }
        }

        // Validate first ingredient unit
        Unit unit1 = parseUnit(unit1Str);
        if (unit1 == null) {
            req.setAttribute("unit1Error", "First unit is invalid. Use: tsp, tbsp, fl oz, cup, pint, quart, oz, lb, g, kg");
            hasErrors = true;
        }

        // Validate operator
        if (!Helpers.isValidString(operator)) {
            req.setAttribute("operatorError", "Operation is required");
            hasErrors = true;
        } else if (!operator.equals("add") && !operator.equals("subtract") &&
                !operator.equals("multiply") && !operator.equals("divide")) {
            req.setAttribute("operatorError", "Invalid operation");
            hasErrors = true;
        }

        // For add/subtract operations, validate second ingredient
        if (operator != null && (operator.equals("add") || operator.equals("subtract"))) {
            // Validate second ingredient name (only for add, not subtract)
            if (operator.equals("add")) {
                if (!Helpers.isValidString(name2) || name2.trim().isEmpty()) {
                    req.setAttribute("name2Error", "Second ingredient name is required for addition");
                    hasErrors = true;
                }
            }

            // Validate second ingredient quantity
            double quantity2 = 0;
            if (!Helpers.isValidNumber(quantity2Str)) {
                req.setAttribute("quantity2Error", "Quantity must be a valid number");
                hasErrors = true;
            } else {
                quantity2 = Double.parseDouble(quantity2Str);
                if (quantity2 <= 0) {
                    req.setAttribute("quantity2Error", "Quantity must be a positive number");
                    hasErrors = true;
                } else if (quantity2 > 999999.999) {
                    req.setAttribute("quantity2Error", "Quantity must be 999999.999 or less");
                    hasErrors = true;
                } else if (quantity2 < 0.001) {
                    req.setAttribute("quantity2Error", "Quantity must be at least 0.001");
                    hasErrors = true;
                }
            }

            // Validate second ingredient unit
            Unit unit2 = parseUnit(unit2Str);
            if (unit2 == null) {
                req.setAttribute("unit2Error", "Second unit is invalid. Use: tsp, tbsp, fl oz, cup, pint, quart, oz, lb, g, kg");
                hasErrors = true;
            }
        }

        // For multiply/divide operations, validate scalar
        if (operator != null && (operator.equals("multiply") || operator.equals("divide"))) {
            if (!Helpers.isValidNumber(scalarStr)) {
                req.setAttribute("scalarError", "Scalar value must be a valid number");
                hasErrors = true;
            } else {
                double scalar = Double.parseDouble(scalarStr);
                if (operator.equals("divide") && scalar == 0) {
                    req.setAttribute("scalarError", "Cannot divide by zero");
                    hasErrors = true;
                } else if (scalar <= 0) {
                    req.setAttribute("scalarError", "Scalar must be positive");
                    hasErrors = true;
                }
            }
        }

        // If no errors, perform calculation
        if (!hasErrors) {
            try {
                Ingredient ingredient1 = new Ingredient(name1.trim(), quantity1, unit1);
                Ingredient result = null;

                switch (operator) {
                    case "add":
                        double quantity2Add = Double.parseDouble(quantity2Str);
                        Unit unit2Add = parseUnit(unit2Str);
                        Ingredient ingredient2Add = new Ingredient(name2.trim(), quantity2Add, unit2Add);
                        Ingredient addResult = ingredient1.add(ingredient2Add);
                        // Create result with combined name
                        result = new Ingredient(name1.trim() + " & " + name2.trim() + " mixture", addResult.getQuantity(), addResult.getUnit());
                        break;

                    case "subtract":
                        double quantity2Sub = Double.parseDouble(quantity2Str);
                        Unit unit2Sub = parseUnit(unit2Str);
                        // For subtraction, use the first ingredient's name (subtracting from same ingredient)
                        Ingredient ingredient2Sub = new Ingredient(name1.trim() + " (used)", quantity2Sub, unit2Sub);
                        Ingredient subtractResult = ingredient1.subtract(ingredient2Sub);
                        // Create a cleaner result with "remaining" label
                        result = new Ingredient(name1.trim() + " remaining", subtractResult.getQuantity(), subtractResult.getUnit());
                        break;

                    case "multiply":
                        double scalar = Double.parseDouble(scalarStr);
                        Ingredient tempMultiply = ingredient1.multiply(scalar);
                        result = new Ingredient(ingredient1.getName(), tempMultiply.getQuantity(), tempMultiply.getUnit());
                        break;

                    case "divide":
                        double divisor = Double.parseDouble(scalarStr);
                        Ingredient tempDivide = ingredient1.divide(divisor);
                        result = new Ingredient(ingredient1.getName(), tempDivide.getQuantity(), tempDivide.getUnit());
                        break;
                }

                if (result != null) {
                    String resultStr = "";
                    String qty1Formatted = Helpers.round(quantity1, 3);

                    switch (operator) {
                        case "add":
                            double qty2Add = Double.parseDouble(quantity2Str);
                            String qty2AddFormatted = Helpers.round(qty2Add, 3);
                            resultStr = String.format("%s %s %s + %s %s %s = %s",
                                    qty1Formatted, unit1Str, name1.trim(),
                                    qty2AddFormatted, unit2Str, name2.trim(),
                                    result.toString());
                            break;
                        case "subtract":
                            double qty2Sub = Double.parseDouble(quantity2Str);
                            String qty2SubFormatted = Helpers.round(qty2Sub, 3);
                            resultStr = String.format("%s %s %s − %s %s used = %s",
                                    qty1Formatted, unit1Str, name1.trim(),
                                    qty2SubFormatted, unit2Str,
                                    result.toString());
                            break;
                        case "multiply":
                            String scalarMult = Helpers.round(Double.parseDouble(scalarStr), 3);
                            resultStr = String.format("%s %s %s × %s = %s",
                                    qty1Formatted, unit1Str, name1.trim(),
                                    scalarMult,
                                    result.toString());
                            break;
                        case "divide":
                            String scalarDiv = Helpers.round(Double.parseDouble(scalarStr), 3);
                            resultStr = String.format("%s %s %s ÷ %s = %s",
                                    qty1Formatted, unit1Str, name1.trim(),
                                    scalarDiv,
                                    result.toString());
                            break;
                    }
                    req.setAttribute("result", resultStr);
                }

            } catch (IllegalArgumentException e) {
                req.setAttribute("calculationError", "Calculation error: " + e.getMessage());
            } catch (Exception e) {
                req.setAttribute("calculationError", "An unexpected error occurred");
            }
        }

        req.getRequestDispatcher("WEB-INF/ingredient.jsp").forward(req, resp);
    }

    /**
     * Parses a unit string to a Unit enum
     * @param unitStr The unit string from user input
     * @return The corresponding Unit enum, or null if invalid
     */
    private Unit parseUnit(String unitStr) {
        if (unitStr == null || unitStr.trim().isEmpty()) {
            return null;
        }

        String unit = unitStr.toLowerCase().trim();

        return switch (unit) {
            case "tsp", "teaspoon", "teaspoons" -> Unit.TSP;
            case "tbsp", "tablespoon", "tablespoons" -> Unit.TBSP;
            case "fl oz", "floz", "fluid ounce", "fluid ounces" -> Unit.FL_OZ;
            case "cup", "cups" -> Unit.CUP;
            case "pint", "pints" -> Unit.PINT;
            case "quart", "quarts" -> Unit.QUART;
            case "oz", "ounce", "ounces" -> Unit.OZ;
            case "lb", "lbs", "pound", "pounds" -> Unit.LB;
            case "g", "gram", "grams" -> Unit.GRAM;
            case "kg", "kilogram", "kilograms" -> Unit.KILOGRAM;
            default -> null;
        };
    }
}