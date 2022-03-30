package com.ps.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

@WebServlet(urlPatterns = {"/bus1"})
public class TicketController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(InputStream iStream=req.getInputStream();
            JsonReader jReader=new Gson().newJsonReader(req.getReader());
        ){
           
        }
    }
}
