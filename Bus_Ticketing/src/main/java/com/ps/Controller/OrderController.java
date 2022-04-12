package com.ps.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ps.services.PropertiesService;
import com.ps.services.RazorPayIntegrationService;
import com.razorpay.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/pay"})
public class OrderController extends HttpServlet{
    
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(
            JsonReader reader=new JsonReader(req.getReader());
        ){
            reader.beginObject();
            long amount=(reader.nextName().equals("amount"))?reader.nextLong():0;
            String receipt=(reader.nextName().equals("receipt"))?reader.nextString():null;
            reader.endObject();
            String currency="INR";
            String clientId=PropertiesService.getProperty("CLIENT_ID");
            String clientSecret = PropertiesService.getProperty("CLIENT_SECRET");

            logger.debug("Received payment info: "+amount+" "+receipt);

            Order order=RazorPayIntegrationService.createOrder(clientId, clientSecret, amount, currency, receipt);

            try(
                PrintWriter writer=resp.getWriter();
            ){
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                resp.setContentType("application/json");
                resp.setStatus(200);

                writer.write(new Gson().toJson(order));
                writer.flush();
            }

        }catch(Exception e){
            logger.error(e);
            logger.catching(e);
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            resp.setHeader("Access-Control-Allow-Header", "Content-Type");
            resp.setStatus(500);
            resp.sendError(500,"Error creating order Id");
        }
    }
}
