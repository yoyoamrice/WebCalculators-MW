package edu.kirkwood.controller;

import edu.kirkwood.model.HunterTemperature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.Helpers.isValidInteger;

@WebServlet(value="/hunterTemperature")
public class HunterTemperatureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/hunterTemperature.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get parameters
        String startType =  req.getParameter("startType");
        String endType = req.getParameter("endType");
        String startValue = req.getParameter("startValue");

        // Set parameters as attributes on the request object
        req.setAttribute("startType",startType);
        req.setAttribute("endType",endType);
        req.setAttribute("startValue",startValue);

        // Validate the data
        boolean errorsFound = false;
        if(startType == null || !startType.matches("(fahrenheit|celsius|kelvin)")) {
            errorsFound = true;
            req.setAttribute("startTypeError", "<li>Starting Type is invalid</li>");
        }
        if(endType == null || !endType.matches("(fahrenheit|celsius|kelvin)")) {
            errorsFound = true;
            req.setAttribute("endTypeError", "<li>Ending Type is invalid</li>");
        }
        if(startValue == null || startValue.isEmpty()) {
            errorsFound = true;
            req.setAttribute("startValueError", "<li>A value must be inputted</li>");
        } else if(!isValidInteger(startValue)) {
            errorsFound = true;
            req.setAttribute("startValueError", "<li>Inputted value is not a valid integer</li>");
        }

        // Validate Temperature
        HunterTemperature temperature = null;
        if(!errorsFound) {
            try {
                temperature = new HunterTemperature(Integer.parseInt(startValue),startType);
            } catch(Exception e) {
                errorsFound = true;
                req.setAttribute("generalError", "<li>The value entered was to lower please try again.</li>");
            }
        }

        // Produce the results
        if(!errorsFound) {
            String endValue = "";
            switch(endType) {
                case "fahrenheit":
                    endValue = String.valueOf(temperature.convertToFahrenheit());
                    break;
                case "celsius":
                    endValue = String.valueOf(temperature.convertToCelsius());
                    break;
                case "kelvin":
                    endValue = String.valueOf(temperature.convertToKelvin());
                    break;
            }

            req.setAttribute("endValue", endValue);
        }

        // Forward the request and response objects to the JSP
        req.getRequestDispatcher("WEB-INF/hunterTemperature.jsp").forward(req, resp);
    }
}
