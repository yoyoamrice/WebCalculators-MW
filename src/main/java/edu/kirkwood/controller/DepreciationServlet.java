package edu.kirkwood.controller;

import edu.kirkwood.model.Depreciation;
import edu.kirkwood.model.Fraction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "DepreciationServlet", urlPatterns = "/depreciation")
public class DepreciationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward to the JSP to show the empty form
        req.getRequestDispatcher("WEB-INF/depreciation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Retrieve all parameters as Strings
        String costStr = req.getParameter("assetCost");
        String salvageStr = req.getParameter("salvageValue");
        String lifeStr = req.getParameter("usefulLife");
        String operation = req.getParameter("operation");
        String yearStr = req.getParameter("targetYear"); // Needed for accumulated/item value

        // Map to hold error messages
        Map<String, String> errors = new HashMap<>();

        // Variables to hold parsed values
        double assetCost = 0;
        double salvageValue = 0;
        int usefulLife = 0;
        int targetYear = 0;

        // 2. Server-Side Validation (Manual parsing because input type="text")

        // Validate Asset Cost
        try {
            if (costStr == null || costStr.trim().isEmpty()) {
                errors.put("costError", "Asset Cost is required.");
            } else {
                assetCost = Double.parseDouble(costStr);
                if (assetCost < 0) errors.put("costError", "Cost cannot be negative.");
            }
        } catch (NumberFormatException e) {
            errors.put("costError", "Asset Cost must be a valid number.");
        }

        // Validate Salvage Value
        try {
            if (salvageStr == null || salvageStr.trim().isEmpty()) {
                errors.put("salvageError", "Salvage Value is required.");
            } else {
                salvageValue = Double.parseDouble(salvageStr);
                if (salvageValue < 0) errors.put("salvageError", "Salvage value cannot be negative.");
                // We check if Cost < Salvage later, after we know both are valid numbers
            }
        } catch (NumberFormatException e) {
            errors.put("salvageError", "Salvage Value must be a valid number.");
        }

        // Validate Useful Life
        try {
            if (lifeStr == null || lifeStr.trim().isEmpty()) {
                errors.put("lifeError", "Useful Life is required.");
            } else {
                usefulLife = Integer.parseInt(lifeStr);
                if (usefulLife <= 0) errors.put("lifeError", "Useful Life must be positive.");
            }
        } catch (NumberFormatException e) {
            errors.put("lifeError", "Useful Life must be a whole number.");
        }

        // Validate Logic: Cost vs Salvage
        if (!errors.containsKey("costError") && !errors.containsKey("salvageError")) {
            if (assetCost < salvageValue) {
                errors.put("costError", "Cost must be greater than Salvage Value.");
            }
        }

        // Validate Target Year (Only if operation requires it)
        if ("accumulated".equals(operation) || "itemValue".equals(operation)) {
            try {
                if (yearStr == null || yearStr.trim().isEmpty()) {
                    errors.put("yearError", "Year is required for this calculation.");
                } else {
                    targetYear = Integer.parseInt(yearStr);
                    if (targetYear < 0) errors.put("yearError", "Year cannot be negative.");
                    // Note: We can't check if year > usefulLife here easily without a valid usefulLife,
                    // so we handle that in the catch block below.
                }
            } catch (NumberFormatException e) {
                errors.put("yearError", "Year must be a whole number.");
            }
        }

        // 3. Process Logic if No Errors
        if (errors.isEmpty()) {
            try {
                Depreciation dep = new Depreciation(assetCost, salvageValue, usefulLife);
                Fraction result = null;

                switch (operation) {
                    case "annual":
                        result = dep.calculateAnnualDepreciation();
                        req.setAttribute("resultType", "Annual Depreciation");
                        break;
                    case "accumulated":
                        if(targetYear > usefulLife) {
                            errors.put("yearError", "Year cannot exceed useful life (" + usefulLife + ").");
                        } else {
                            result = dep.calculateAccumulatedDepreciation(targetYear);
                            req.setAttribute("resultType", "Accumulated Depreciation at Year " + targetYear);
                        }
                        break;
                    case "itemValue":
                        if(targetYear > usefulLife) {
                            errors.put("yearError", "Year cannot exceed useful life (" + usefulLife + ").");
                        } else {
                            result = dep.calculateItemValue(targetYear);
                            req.setAttribute("resultType", "Item Value at Year " + targetYear);
                        }
                        break;
                    default:
                        result = dep.calculateAnnualDepreciation();
                }

                if(errors.isEmpty() && result != null) {
                    req.setAttribute("resultFraction", result.toMixedNumber()); // Assuming your Fraction class has this
                    double decimalVal = (double) result.getNumerator() / result.getDenominator();
                    req.setAttribute("resultDecimal", String.format("$%.2f", decimalVal));
                }

            } catch (IllegalArgumentException e) {
                // Handle business logic exceptions from your Model
                errors.put("globalError", e.getMessage());
            }
        }

        // 4. Set Errors and Forward
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
        }

        req.getRequestDispatcher("WEB-INF/depreciation.jsp").forward(req, resp);
    }
}