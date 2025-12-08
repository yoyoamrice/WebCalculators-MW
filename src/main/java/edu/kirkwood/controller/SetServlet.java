package edu.kirkwood.controller;

import edu.kirkwood.model.Set;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/setcalculator")
public class SetServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("WEB-INF/set.jsp").forward(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // Get parameters
    String set1Name = req.getParameter("set1Name");
    String set1Elements = req.getParameter("set1Elements");
    String operator = req.getParameter("operator");
    String set2Name = req.getParameter("set2Name");
    String set2Elements = req.getParameter("set2Elements");

    // Set parameters as attributes on the request object
    req.setAttribute("set1Name", set1Name);
    req.setAttribute("set1Elements", set1Elements);
    req.setAttribute("operator", operator);
    req.setAttribute("set2Name", set2Name);
    req.setAttribute("set2Elements", set2Elements);

    // Validate the data
    boolean errorsFound = false;

    // Set 1
    if(set1Name == null || set1Name.trim().isEmpty()) {
        errorsFound = true;
        req.setAttribute("set1NameError", "<li>Set 1 Name is required</li>");
    }

    // Operator
    if(operator == null || !operator.matches("(union|intersection|difference)")) {
        errorsFound = true;
        req.setAttribute("operatorError", "<li>Operator is invalid</li>");
    }

    // Set 2
    if(set2Name == null || set2Name.trim().isEmpty()) {
        errorsFound = true;
        req.setAttribute("set2NameError", "<li>Set 2 Name is required</li>");
    }

    //  Create Sets
    Set set1 = null;
    Set set2 = null;

    if(!errorsFound) {
        try {
            // Parse elements for Set 1
            ArrayList<String> elements1 = new ArrayList<>();
            if (!set1Elements.trim().isEmpty()) {
                String[] parts1 = set1Elements.split(",");
                for (String part : parts1) {
                    elements1.add(part.trim());
                }
            }
            set1 = new Set(set1Name, elements1);
        } catch (IllegalArgumentException e) {
            req.setAttribute("set1NameError", "<li>" + e.getMessage() + "</li>");
            errorsFound = true;
        }

        try {
            // Parse elements for Set 2
            ArrayList<String> elements2 = new ArrayList<>();
            if (!set2Elements.trim().isEmpty()) {
                String[] parts2 = set2Elements.split(",");
                for (String part : parts2) {
                    elements2.add(part.trim());
                }
            }
            set2 = new Set(set2Name, elements2);
        } catch (IllegalArgumentException e) {
            req.setAttribute("set2NameError", "<li>" + e.getMessage() + "</li>");
            errorsFound = true;
        }
    }

    // Produce Result
    if(!errorsFound && set1 != null && set2 != null) {
        Set ResultSet = null;
        String operatorResult = "";

        if(operator.equals("union")) {
            ResultSet = set1.union(set2);
            operatorResult = "∪";
        } else if(operator.equals("intersection")) {
            ResultSet = set1.intersection(set2);
            operatorResult = "∩";
        } else if(operator.equals("difference")) {
            ResultSet = set1.difference(set2);
            operatorResult = "-";
        }

        String result = String.format("%s %s %s = %s", set1.getName(), operatorResult, set2.getName(), ResultSet.toString());
        req.setAttribute("result", result);
    }

    // Forward the request and response objects to the JSP
    req.getRequestDispatcher("WEB-INF/set.jsp").forward(req, resp);
}
}