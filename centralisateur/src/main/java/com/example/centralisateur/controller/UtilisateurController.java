package com.example.centralisateur.controller;

import java.io.IOException;

import com.example.centralisateur.dto.UtilisateurDTO;
import com.example.centralisateur.service.UtilService;
import com.example.centralisateur.service.UtilisateurService;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response;

@WebServlet("/login")
public class UtilisateurController extends HttpServlet {

    @Inject
    private UtilisateurService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            service.logout();
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Response result = service.authentifier(login, password);

        if (result.getStatus() == 200) {
            UtilisateurDTO utilisateur = result.readEntity(UtilisateurDTO.class);
            service.addSession(utilisateur);
            response.sendRedirect("index.jsp");
        } else {
            String erreur = result.hasEntity() ? result.readEntity(String.class) : "Erreur inconnue";
            request.setAttribute("error", erreur);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
