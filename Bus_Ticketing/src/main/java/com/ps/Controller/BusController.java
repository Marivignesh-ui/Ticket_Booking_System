package com.ps.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
// import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
// import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ps.domain.Bus;
import com.ps.services.BusServices;

@WebServlet(urlPatterns = {"/bus"})
public  class BusController extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try(InputStream iStream=req.getInputStream();
            JsonReader jReader=Json.createReader(iStream);
        ){
            JsonObject json=jReader.readObject();
            String startTerminal=json.getString("startterminal");
            String endTerminal=json.getString("endterminal");
            String date=json.getString("date");

            BusServices busServices=new BusServices();
            List<Bus> buses=busServices.getBusesService(date,startTerminal,endTerminal);
            try (PrintWriter out = res.getWriter()) {
                res.setContentType("application/json");
                String response=new Gson().toJson(buses);
                out.println(response);
                out.flush();
            }
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try(InputStream iStream=req.getInputStream();
            JsonReader jsonObject = Json.createReader(iStream);
        ){
            JsonObject json=jsonObject.readObject();
            String registrationNumber=json.getString("registrationnumber");
            String name=json.getString("name");
            String startTerminal=json.getString("startterminal");
            String endTerminal=json.getString("endterminal");
            String type=json.getString("type");
            String date=json.getString("date");
            int noOfTickets=json.getInt("nooftickets");

            Bus bus=new Bus(registrationNumber,name,startTerminal,endTerminal,type,date,noOfTickets);
            BusServices busServices=new BusServices();
            bus=busServices.createBusService(bus);
            System.out.println(bus);

            try (PrintWriter out = res.getWriter()) {
                out.println(bus);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
}
