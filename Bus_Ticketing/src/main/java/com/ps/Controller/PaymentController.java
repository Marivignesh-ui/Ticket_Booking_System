package com.ps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ps.domain.PaymentResponse;
import com.ps.domain.Ticket;
import com.ps.services.PropertiesService;
import com.ps.services.TicketServices;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/paymentResult"})
public class PaymentController extends HttpServlet{
    
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String razorpay_payment_id = req.getParameter("razorpay_payment_id");
        String razorpay_order_id = req.getParameter("razorpay_order_id");
        String razorpay_signature = req.getParameter("razorpay_signature");

        boolean isSignatureValid = isSignatureValid(razorpay_order_id, razorpay_payment_id, razorpay_signature);
        List<Ticket> tickets=null;
        if(isSignatureValid){
            try(JsonReader reader=new JsonReader(req.getReader())){
                logger.debug("Request to book tickets received successfully!!");
                List<String> ticketIds=new ArrayList<>();
                reader.beginObject();
                if(reader.nextName().equals("ticketIds")){
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ticketIds.add(reader.nextString());   
                    }
                    reader.endArray();
                }
                logger.debug("Request Object read Successfully!! About to call bookTicketService");
                String name=reader.nextName().equals("userId")?reader.nextString():null;
                String busId=reader.nextName().equals("busId")?reader.nextString():null;
                tickets=new TicketServices().bookTicketService(ticketIds, name, busId);
                try(PrintWriter writer=resp.getWriter()){
                    resp.setHeader("Access-Control-Allow-Origin", "*");
                    resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                    resp.setContentType("application/json");
                    writer.println(new Gson().toJson(tickets));
                    writer.flush();
                }
            }catch(Exception e){
                logger.error(e);
                logger.catching(e);
                resp.setStatus(500);
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                resp.setHeader("Access-Control-Allow-Header", "Content-Type");
                resp.sendError(1,"Error on booking Ticket");
                e.printStackTrace();
            }
        }


        String respString = new Gson().toJson(new PaymentResponse(razorpay_payment_id, razorpay_order_id, isSignatureValid));
        resp.getWriter().write(respString);
        resp.getWriter().flush();
    }

    static boolean isSignatureValid(String razorpay_order_id, String razorpay_payment_id, String razorpay_signature) {
        String CLIENT_SECRET = PropertiesService.getProperty("CLIENT_SECRET");
        boolean isSignatureValid = false;

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            sha256_HMAC.init(new SecretKeySpec(CLIENT_SECRET.getBytes(), "HmacSHA256"));

            String data = razorpay_order_id + "|" + razorpay_payment_id;
            byte[] hmacSha256 = sha256_HMAC.doFinal(data.getBytes());
            String generated_signature = Hex.encodeHexString(hmacSha256);

            logger.info("generatedSignature: " + generated_signature);
            logger.info("razorpay_signature: " + razorpay_signature);

            System.out.println("generatedSignature: " + generated_signature);
            System.out.println("razorpay_signature: " + razorpay_signature);

            if (generated_signature.equals(razorpay_signature)) {
                isSignatureValid = true;
            }

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.catching(e);
            logger.error(e.getMessage());
        }
        System.out.println("isSignatureValid: " + isSignatureValid);
        return isSignatureValid;
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");   
        res.setHeader("Access-Control-Allow-Header", "Content-Type");
    }
}
