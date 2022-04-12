package com.ps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ps.domain.Ticket;
import com.ps.services.TicketServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/ticket" })
public class TicketController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ticket> tickets = null;
        try {
            String busId = req.getParameter("bus_id");

            logger.debug("Request recieved to get tickets for bus Id: "+busId);
            tickets = new TicketServices().getTicketsService(busId);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (
                JsonReader jReader = new JsonReader(req.getReader());) {
            logger.debug("Request to book tickets received successfully!!");
            List<String> ticketIds = new ArrayList<>();
            jReader.beginObject();
            if (jReader.nextName().equals("ticketIds")) {
                jReader.beginArray();
                while (jReader.hasNext()) {
                    ticketIds.add(jReader.nextString());
                }
                jReader.endArray();
            }
            logger.debug("Request Object read Successfully!! About to call bookTicketService");
            String name = jReader.nextName().equals("name") ? jReader.nextString() : null;
            String busId = jReader.nextName().equals("busId") ? jReader.nextString() : null;
            List<Ticket> tickets = new TicketServices().bookTicketService(ticketIds, name, busId);
            try (PrintWriter writer = resp.getWriter()) {
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                resp.setContentType("application/json");
                writer.println(new Gson().toJson(tickets));
                writer.flush();
            }
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            resp.sendError(500, "Error on booking Ticket");
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
}
