package edu.kirkwood.controller;

import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.dao.MovieDAOFactory;
import edu.kirkwood.dao.impl.JsonMovieDAO;
import edu.kirkwood.dao.impl.MySQLMovieDAO;
import edu.kirkwood.dao.impl.XmlMovieDAO;
import edu.kirkwood.model.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/movies")
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. Get the parameter "username"
        String search = req.getParameter("search");

        // 2. Set it as an attribute (same name or any name you want)
        req.setAttribute("search", search);

        // Forward (example)
        req.getRequestDispatcher("WEB-INF/movie.jsp").forward(req, resp);
    }


    public static List<Movie> getResults(String search) {
        try {
            MovieDAO movieDAO = MovieDAOFactory.getMovieDAO();
            List<Movie> movies = new ArrayList<>();
            if(movieDAO instanceof XmlMovieDAO) {
                movies.addAll(((XmlMovieDAO)movieDAO).search(search));
            } else if(movieDAO instanceof MySQLMovieDAO) {
                movies.addAll(((MySQLMovieDAO)movieDAO).search(search));
            } else if(movieDAO instanceof JsonMovieDAO) {
                movies.addAll(((JsonMovieDAO)movieDAO).search(search));
            }

            return movies;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}