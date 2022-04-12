package com.ps.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ps.domain.Bus;
import com.ps.services.BusService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/bus"})
public  class BusController extends HttpServlet{
    private static final Logger logger=LogManager.getLogger();

    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try {
            String startTerminal=req.getParameter("startterminal");
            String endTerminal=req.getParameter("endterminal");
            String date=req.getParameter("date");

            logger.debug("request recieved startterminal:"+startTerminal+" endTerminal "+endTerminal+" date: "+date);

            BusService busService=new BusService();
            List<Bus> buses=busService.getBusesService(date,startTerminal,endTerminal);
            try (PrintWriter out = res.getWriter()) {
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                res.setHeader("Access-Control-Allow-Header", "Content-Type");
                res.setContentType("application/json");
                String response=new Gson().toJson(buses);
                out.println(response);
                out.flush();
            }
        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
        }
    }

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try(JsonReader reader=new JsonReader(req.getReader())){
            Bus bus=new Gson().fromJson(reader, Bus.class);
            UUID uniqueId=UUID.randomUUID();
            bus.setId(uniqueId.toString());
            bus.createTickets();
            logger.debug("request object read successfully!! : "+bus);
            // Bus bus=new Bus(registrationNumber,name,startTerminal,endTerminal,type,date,noOfTickets);

            BusService busService=new BusService();
            bus=busService.createBusService(bus);
            System.out.println(bus);

            try (PrintWriter out = res.getWriter()) {
                res.setContentType("application/json");
                out.println(new Gson().toJson(bus));
                out.flush();
            } catch (IOException e) {
                logger.error(e);
                logger.catching(e);
                res.setStatus(500);
                res.sendError(1,"Couldn't create bus");
            }

        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            res.sendError(500,"Couldn't create bus");
        }
    }


    @Override
    public void doPut(HttpServletRequest req,HttpServletResponse res) throws IOException{
        try(InputStream iStream=req.getInputStream();
            javax.json.JsonReader jsonObject = Json.createReader(iStream);
        ){
            JsonObject json=jsonObject.readObject();
            String busId=json.getString("busId");
            String startTerminal=json.getString("startterminal");
            String endTerminal=json.getString("endterminal");
            String date=json.getString("date");
            logger.debug("request object read successfully!! "+busId);

            BusService busService=new BusService();
            Bus bus=busService.updateBusService(busId,startTerminal,endTerminal,date);

            try (PrintWriter out = res.getWriter()) {
                res.setContentType("application/json");
                out.println(new Gson().toJson(bus));
                out.flush();
            }

        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            res.sendError(500,"Couldn't update bus");
        }
    }

    @Override
    public void doDelete(HttpServletRequest req,HttpServletResponse res) throws IOException{
        try(InputStream iStream=req.getInputStream();
        javax.json.JsonReader jsonObject = Json.createReader(iStream);
        ){
            JsonObject json=jsonObject.readObject();
            String busId=json.getString("busId");

            logger.debug("request object read successfully!! "+busId);

            BusService busServices=new BusService();
            Bus bus=busServices.deleteBusService(busId);

            try (PrintWriter out = res.getWriter()) {
                res.setContentType("application/json");
                out.println(new Gson().toJson(bus));
                out.flush();
            }

        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            res.setStatus(500);
            res.sendError(500,"Couldn't delete bus");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
}
