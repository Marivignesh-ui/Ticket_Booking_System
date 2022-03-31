package com.ps.Controller;

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

@WebServlet(urlPatterns = {"/bus1"})
public class TicketController extends HttpServlet{
    private static final Logger logger=LogManager.getLogger();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(
            JsonReader jReader=new Gson().newJsonReader(req.getReader());
        ){
            jReader.beginObject();
            String busId=(jReader.nextName().equals("busId"))?jReader.nextString():null;
            jReader.endObject();
            logger.debug("Request Object read Successfully!! About to call GetTicketService");
            List<Ticket> tickets=new TicketServices().getTicketsService(busId);
            try(PrintWriter writer=resp.getWriter()){
                resp.setContentType("application/json");
                writer.println(new Gson().toJson(tickets));
                writer.flush();
            }
        }catch(Exception e){
           logger.error(e);
           logger.catching(e);
           e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(
            JsonReader jReader=new JsonReader(req.getReader());
        ){
            logger.debug("Request to book tickets received successfully!!");
            List<String> ticketIds=new ArrayList<>();
            jReader.beginObject();
            if(jReader.nextName().equals("ticketIds")){
                jReader.beginArray();
                while (jReader.hasNext()) {
                    ticketIds.add(jReader.nextString());   
                }
                jReader.endArray();
            }
            logger.debug("Request Object read Successfully!! About to call bookTicketService");
            String name=jReader.nextName().equals("name")?jReader.nextString():null;
            String busId=jReader.nextName().equals("busId")?jReader.nextString():null;
            List<Ticket> tickets=new TicketServices().bookTicketService(ticketIds, name, busId);
            try(PrintWriter writer=resp.getWriter()){
                resp.setContentType("application/json");
                writer.println(new Gson().toJson(tickets));
                writer.flush();
            }
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            resp.sendError(1,"Error on booking Ticket");
            e.printStackTrace();
        }
    }
}
