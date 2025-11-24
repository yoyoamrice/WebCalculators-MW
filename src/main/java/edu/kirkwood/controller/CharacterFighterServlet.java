package edu.kirkwood.controller;

import edu.kirkwood.model.CharacterFighter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.CharacterFighterComparator.getComparisonResults;

@WebServlet(value="/CharacterFighter")
public class CharacterFighterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/CharacterFighter.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get parameters
        String p1MoveStartupStr = req.getParameter("p1MoveStartup");
        String p2MoveStartupStr = req.getParameter("p2MoveStartup");
        String p1CharacterStatus = req.getParameter("p1CharacterStatus");
        String p2CharacterStatus = req.getParameter("p2CharacterStatus");
        String p1MoveType = req.getParameter("p1MoveType");
        String p2MoveType = req.getParameter("p2MoveType");

        int p1MoveStartup = 0;
        int p2MoveStartup = 0;
        boolean errorsFound = false;


        // Step 2: Parse and validate that integers are 0 or above
        try {
            p1MoveStartup = Integer.parseInt(p1MoveStartupStr);
            if (p1MoveStartup < 0) {
                errorsFound = true;
                req.setAttribute("p1MoveStartupError", "<li>Value must be 0 or above</li>");
            }
        } catch (NumberFormatException e) {
            errorsFound = true;
            req.setAttribute("p1MoveStartupError", "<li>Invalid number</li>");
        }

        try {
            p2MoveStartup = Integer.parseInt(p2MoveStartupStr);
            if (p2MoveStartup < 0) {
                errorsFound = true;
                req.setAttribute("p2MoveStartupError", "<li>Value must be 0 or above</li>");
            }
        } catch (NumberFormatException e) {
            errorsFound = true;
            req.setAttribute("p2MoveStartupError", "<li>Invalid number</li>");
        }

        // Step 3: Set attributes for the form
        req.setAttribute("p1MoveStartup", p1MoveStartup);
        req.setAttribute("p2MoveStartup", p2MoveStartup);
        req.setAttribute("p1CharacterStatus", p1CharacterStatus);
        req.setAttribute("p2CharacterStatus", p2CharacterStatus);
        req.setAttribute("p1MoveType", p1MoveType);
        req.setAttribute("p2MoveType", p2MoveType);

        p1CharacterStatus = p1CharacterStatus.toLowerCase();
        p2CharacterStatus = p2CharacterStatus.toLowerCase();
        p1MoveType = p1MoveType.toLowerCase();
        p2MoveType = p2MoveType.toLowerCase();

        // Step 4: Validate The Character Fighters
        CharacterFighter player1 = null;
        CharacterFighter player2 = null;

        if (!errorsFound) {
            try {
                player1 = new CharacterFighter(p1MoveStartup, p1CharacterStatus, p1MoveType);
            } catch (ArithmeticException e) {
                errorsFound = true;
                req.setAttribute("player1Error", "<li>Error creating Player 1</li>");
            }
            try {
                player2 = new CharacterFighter(p2MoveStartup, p2CharacterStatus, p2MoveType);
            } catch (ArithmeticException e) {
                errorsFound = true;
                req.setAttribute("player2Error", "<li>Error creating Player 2</li>");
            }
        }

        if (errorsFound) {
            req.getRequestDispatcher("/WEB-INF/CharacterFighter.jsp").forward(req, resp);
            return;
        }

        // Step 5: Produce the Result
        String comparisonResult = getComparisonResults(player1, player2);
        req.setAttribute("comparisonResult", comparisonResult);

        // Step 6: Forward the request and response objects to the JSP
        req.getRequestDispatcher("/WEB-INF/CharacterFighter.jsp").forward(req, resp);

    }
}
