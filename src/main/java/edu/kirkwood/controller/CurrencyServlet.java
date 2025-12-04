package edu.kirkwood.controller;

import edu.kirkwood.model.Lawson.Currency;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.Helpers.isValidDouble;

@WebServlet(value = "/currency")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!"POST".equalsIgnoreCase(req.getMethod())) {
            req.getRequestDispatcher("WEB-INF/currency.jsp").forward(req, resp);
            return;
        }
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");
        String currency1Str = req.getParameter("currency1");
        String currency2Str = req.getParameter("currency2");
        String operator = req.getParameter("operator");

        req.setAttribute("num1", num1);
        req.setAttribute("num2", num2);
        req.setAttribute("currency1", currency1Str);
        req.setAttribute("currency2", currency2Str);
        req.setAttribute("operator", operator);

        boolean errorsFound = false;
        if (num1 == null || num1.isEmpty() || !isValidDouble(num1)) {
            errorsFound = true;
            req.setAttribute("num1Error", "<li>Number 1 is required and must be a number</li>");
        }
        if(num2.isEmpty()) {
            num2 = "0";
        }
        if (!isValidDouble(num2)) {
            errorsFound = true;
            req.setAttribute("num2Error", "<li>Number 2 is required and must be a number</li>");
        }

        if (!Currency.isSupported(currency1Str) || !Currency.isSupported(currency2Str)) {
            errorsFound = true;
            req.setAttribute("currencyError", "<li>Invalid currency selection</li>");
        }

        if (!errorsFound) {
            Currency currency1 = new Currency(Double.valueOf(num1), currency1Str);
            Currency currency2 = new Currency(Double.valueOf(num2), currency2Str);
            double result = 0.0;
            String resultString = "";

            if ("convert".equals(operator)) {
                result = currency1.convert(currency2Str);
                resultString = Helpers.round(Double.valueOf(num1), 2) + " " + currency1Str + " = " + Helpers.round(Double.valueOf(result), 2) + " " + currency2Str;
            } else if ("add".equals(operator)) {
                result = currency1.addDifferentCurrencies(currency1.getAmount(), currency1Str,
                        currency2.getAmount(), currency2Str);
                resultString = num1 + " " + currency1Str + " + " + num2 + " " + currency2Str +
                        " = " + Helpers.round(Double.valueOf(result),2) + " " + currency1Str;
            } else if ("sub".equals(operator)) {
                result = currency1.subDifferentCurrencies(currency1.getAmount(), currency1Str,
                        currency2.getAmount(), currency2Str);
                resultString = num1 + " " + currency1Str + " - " + num2 + " " + currency2Str +
                        " = " + Helpers.round(Double.valueOf(result),2) + " " + currency1Str;
            } else {
                resultString = "Unknown operation";
            }

            req.setAttribute("result", resultString);
        }
        req.getRequestDispatcher("WEB-INF/currency.jsp").forward(req, resp);
    }
}
