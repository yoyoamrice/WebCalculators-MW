// file: /src/main/java/edu/kirkwood/controller/IngredientServlet.java v1 - Initial servlet for ingredient calculator

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
            req.setAttribute("name1Error", "<li>First ingredient name is required</li>");
            hasErrors = true;
        }

        // Validate first ingredient quantity
        double quantity1 = 0;
        if (!Helpers.isValidNumber(quantity1Str)) {
            req.setAttribute("quantity1Error", "<li>First quantity must be a valid number</li>");
            hasErrors = true;
        } else {
            quantity1 = Double.parseDouble(quantity1Str);
            if (quantity1 < -999999.999 || quantity1 > 999999.999) {
                req.setAttribute("quantity1Error", "<li>First quantity must be between -999999.999 and 999999.999</li>");
                hasErrors = true;
            } else if (Math.abs(quantity1) < 0.001 && quantity1 != 0) {
                req.setAttribute("quantity1Error", "<li>Non-zero quantity must be at least 0.001 in absolute value</li>");
                hasErrors = true;
            }
        }

        // Validate first ingredient unit
        Unit unit1 = parseUnit(unit1Str);
        if (unit1 == null) {
            req.setAttribute("unit1Error", "<li>First unit is invalid. Use: tsp, tbsp, fl oz, cup, pint, quart, oz, lb, g, kg</li>");
            hasErrors = true;
        }

        // Validate operator
        if (!Helpers.isValidString(operator)) {
            req.setAttribute("operatorError", "<li>Operation is required</li>");
            hasErrors = true;
        } else if (!operator.equals("add") && !operator.equals("subtract") &&
                !operator.equals("multiply") && !operator.equals("divide")) {
            req.setAttribute("operatorError", "<li>Invalid operation</li>");
            hasErrors = true;
        }

        // For add/subtract operations, validate second ingredient
        if (operator != null && (operator.equals("add") || operator.equals("subtract"))) {
            // Validate second ingredient name
            if (!Helpers.isValidString(name2) || name2.trim().isEmpty()) {
                req.setAttribute("name2Error", "<li>Second ingredient name is required for addition/subtraction</li>");
                hasErrors = true;
            }

            // Validate second ingredient quantity
            double quantity2 = 0;
            if (!Helpers.isValidNumber(quantity2Str)) {
                req.setAttribute("quantity2Error", "<li>Second quantity must be a valid number</li>");
                hasErrors = true;
            } else {
                quantity2 = Double.parseDouble(quantity2Str);
                if (quantity2 < -999999.999 || quantity2 > 999999.999) {
                    req.setAttribute("quantity2Error", "<li>Second quantity must be between -999999.999 and 999999.999</li>");
                    hasErrors = true;
                } else if (Math.abs(quantity2) < 0.001 && quantity2 != 0) {
                    req.setAttribute("quantity2Error", "<li>Non-zero quantity must be at least 0.001 in absolute value</li>");
                    hasErrors = true;
                }
            }

            // Validate second ingredient unit
            Unit unit2 = parseUnit(unit2Str);
            if (unit2 == null) {
                req.setAttribute("unit2Error", "<li>Second unit is invalid. Use: tsp, tbsp, fl oz, cup, pint, quart, oz, lb, g, kg</li>");
                hasErrors = true;
            }
        }

        // For multiply/divide operations, validate scalar
        if (operator != null && (operator.equals("multiply") || operator.equals("divide"))) {
            if (!Helpers.isValidNumber(scalarStr)) {
                req.setAttribute("scalarError", "<li>Scalar value must be a valid number</li>");
                hasErrors = true;
            } else {
                double scalar = Double.parseDouble(scalarStr);
                if (operator.equals("divide") && scalar == 0) {
                    req.setAttribute("scalarError", "<li>Cannot divide by zero</li>");
                    hasErrors = true;
                } else if (scalar <= 0) {
                    req.setAttribute("scalarError", "<li>Scalar must be positive</li>");
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
                        result = ingredient1.add(ingredient2Add);
                        break;

                    case "subtract":
                        double quantity2Sub = Double.parseDouble(quantity2Str);
                        Unit unit2Sub = parseUnit(unit2Str);
                        Ingredient ingredient2Sub = new Ingredient(name2.trim(), quantity2Sub, unit2Sub);
                        result = ingredient1.subtract(ingredient2Sub);
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
                    String resultStr = result.toString();
                    req.setAttribute("result", resultStr);
                }

            } catch (IllegalArgumentException e) {
                req.setAttribute("calculationError", "<li>Calculation error: " + e.getMessage() + "</li>");
            } catch (Exception e) {
                req.setAttribute("calculationError", "<li>An unexpected error occurred</li>");
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

        switch(unit) {
            case "tsp":
            case "teaspoon":
            case "teaspoons":
                return Unit.TSP;
            case "tbsp":
            case "tablespoon":
            case "tablespoons":
                return Unit.TBSP;
            case "fl oz":
            case "floz":
            case "fluid ounce":
            case "fluid ounces":
                return Unit.FL_OZ;
            case "cup":
            case "cups":
                return Unit.CUP;
            case "pint":
            case "pints":
                return Unit.PINT;
            case "quart":
            case "quarts":
                return Unit.QUART;
            case "oz":
            case "ounce":
            case "ounces":
                return Unit.OZ;
            case "lb":
            case "lbs":
            case "pound":
            case "pounds":
                return Unit.LB;
            case "g":
            case "gram":
            case "grams":
                return Unit.GRAM;
            case "kg":
            case "kilogram":
            case "kilograms":
                return Unit.KILOGRAM;
            default:
                return null;
        }
    }
}