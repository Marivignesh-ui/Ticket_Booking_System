package com.ps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ps.domain.Ticket;
import com.ps.services.TicketServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/user/ticket" })
public class TicketController1 extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ticket> tickets = null;
        try {
            String userId = req.getParameter("user_id");
            logger.debug("Request Object read Successfully!! About to call GetTicketService");
            tickets = new TicketServices().getTicketsByUserIdService(userId);
            resp.setStatus(200);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            resp.setStatus(500);
        }
        try (PrintWriter writer = resp.getWriter()) {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Header", "Content-Type");
            resp.setContentType("application/json");
            writer.println(new Gson().toJson(tickets));
            writer.flush();
        }
    }

}
