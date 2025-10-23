// CompteCourantController.java
package com.example.centralisateur.controller;

import com.example.centralisateur.dto.CompteCourantDTO;
import com.example.centralisateur.service.CompteCourantService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/compte-courant/comptes")
public class CompteCourantController extends HttpServlet {

    @Inject
    private CompteCourantService compteService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<CompteCourantDTO> comptes = compteService.getAllComptes();
        request.setAttribute("comptes", comptes);
        
        request.getRequestDispatcher("/compte-courant/comptes.jsp").forward(request, response);
    }
}