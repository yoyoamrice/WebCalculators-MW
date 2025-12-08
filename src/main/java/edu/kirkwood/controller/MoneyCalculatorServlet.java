package edu.kirkwood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/moneyCalculator")
public class MoneyCalculatorServlet extends HttpServlet {

    private static final String[] COIN_NAMES = {"Dollars", "Half-Dollars", "Quarters", "Dimes", "Nickels", "Pennies"};
    private static final int[] COIN_VALUES = {100, 50, 25, 10, 5, 1};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Default empty arrays and operation
        request.setAttribute("coins1", new Integer[6]);
        request.setAttribute("coins2", new Integer[6]);
        request.setAttribute("errorMsg1", "");
        request.setAttribute("errorMsg2", "");
        request.setAttribute("resultCents", null);
        request.setAttribute("operation", "add");
        request.setAttribute("coinNames", COIN_NAMES);

        request.getRequestDispatcher("/WEB-INF/moneyCalculator.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer[] coins1 = new Integer[6];
        Integer[] coins2 = new Integer[6];
        String errorMsg1 = "";
        String errorMsg2 = "";
        Integer resultCents = null;

        try {
            for (int i = 0; i < 6; i++) {
                String c1 = request.getParameter("coins1_" + i);
                String c2 = request.getParameter("coins2_" + i);

                if (c1 == null || c1.isEmpty()) { errorMsg1 = "Enter all values"; }
                if (c2 == null || c2.isEmpty()) { errorMsg2 = "Enter all values"; }

                coins1[i] = (c1 != null && !c1.isEmpty()) ? Integer.parseInt(c1) : 0;
                coins2[i] = (c2 != null && !c2.isEmpty()) ? Integer.parseInt(c2) : 0;

                if (coins1[i] < 0 || coins2[i] < 0) {
                    throw new NumberFormatException("Negative coins not allowed");
                }
            }

            String op = request.getParameter("operation");
            int total1 = coinsToCents(coins1);
            int total2 = coinsToCents(coins2);

            switch (op) {
                case "add": resultCents = total1 + total2; break;
                case "subtract": resultCents = total1 - total2; break;
                default: resultCents = null;
            }

            // Set selected operation
            request.setAttribute("operation", op);

        } catch (NumberFormatException e) {
            if (errorMsg1.isEmpty()) errorMsg1 = "Invalid number";
            if (errorMsg2.isEmpty()) errorMsg2 = "Invalid number";
        }

        request.setAttribute("coins1", coins1);
        request.setAttribute("coins2", coins2);
        request.setAttribute("errorMsg1", errorMsg1);
        request.setAttribute("errorMsg2", errorMsg2);
        request.setAttribute("resultCents", resultCents);
        request.setAttribute("coinNames", COIN_NAMES);

        request.getRequestDispatcher("/WEB-INF/moneyCalculator.jsp").forward(request, response);
    }

    private int coinsToCents(Integer[] coins) {
        int total = 0;
        for (int i = 0; i < coins.length; i++) {
            total += coins[i] * COIN_VALUES[i];
        }
        return total;
    }
}
