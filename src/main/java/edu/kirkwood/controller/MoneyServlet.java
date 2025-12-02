package edu.kirkwood.controller;

import edu.kirkwood.model.Fraction;
import edu.kirkwood.model.Money;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.Helpers.isValidInteger;

@WebServlet(value="/money")
public class MoneyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/YousifMoney.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get parameters
        String money1Amount = req.getParameter("money1Amount");
        String money1Currency =  req.getParameter("money1Currency");
        String operator = req.getParameter("operator");
        String money2Amount= req.getParameter("money2Amount");
        String money2Currency = req.getParameter("money2Currency");
        // Step 2: Set parameters as attributes on the request object
        req.setAttribute("money1Amount",money1Amount);
        req.setAttribute("money1Currency",money1Currency);
        req.setAttribute("operator",operator);
        req.setAttribute("money2Amount",money2Amount);
        req.setAttribute("money2Currency",money2Currency);
        // Step 4: Validate the data
        boolean errorsFound = false;
        if(money1Amount == null || money1Amount.isEmpty()) {
            errorsFound = true;
            req.setAttribute("money1AmountError", "<li>money1Amount 1 is required</li>");
        } else if(!isValidInteger(money1Amount)) {
            errorsFound = true;
            req.setAttribute("money1AmountError", "<li>money1Amount 1 is not a valid integer</li>");
        }
        if(money1Currency == null || money1Currency.isEmpty()) {
            errorsFound = true;
            req.setAttribute("money1CurrencyError", "<li>money1Currency 1 is required</li>");

        }
        if(operator == null || !operator.matches("(add|subtract|multiply|divide)")) {
            errorsFound = true;
            req.setAttribute("operatorError", "<li>Operator is invalid</li>");
        }
        if(money2Amount == null || money2Amount.isEmpty()) {
            errorsFound = true;
            req.setAttribute("money2AmountError", "<li>money2Amount 2 is required</li>");
        } else if(!isValidInteger(money2Amount)) {
            errorsFound = true;
            req.setAttribute("money2AmountError", "<li>money2Amount 2 is not a valid integer</li>");
        }
        if(money2Currency == null || money2Currency.isEmpty()) {
            errorsFound = true;
            req.setAttribute("money2CurrencyError", "<li>money2Currency 2 is required</li>");

        }
        // Step 5: Validate the Money
        Money money1 = new Money();
        Money money2 = new Money();
        if(!errorsFound) {
            try {
                money1 = new Money(Double.parseDouble(money1Amount), money1Currency);
            } catch(Exception e) {
                req.setAttribute("money1Error", e.getMessage());
                errorsFound = true;
            }
            try {
                money2 = new Money(Double.parseDouble(money2Amount), money2Currency);
            } catch(Exception e) {
                req.setAttribute("money2Error", e.getMessage());
                errorsFound = true;
            }
        }
        // Step 6: Produce the result
        if(!errorsFound) {
            String operatorStr = "";
            if(operator.equals("add")) {
                  money1.add(money2);
                operatorStr = "+";
            } else if(operator.equals("subtract")) {
                 money1.subtract(money2);
                operatorStr = "-";
            } else if(operator.equals("multiply")) {
                 money1.multiply(money2);
                operatorStr = "×";
            } else if(operator.equals("divide")) {
                 money1.divide(money2);
                operatorStr = "÷";
            }
            String result = String.format(" = %s", money1.toString());
            req.setAttribute("result", result);
        }
        // Step 3: Forward the request and response objects to the JSP
        req.getRequestDispatcher("WEB-INF/YousifMoney.jsp").forward(req, resp);
    }
}
