package com.ps.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ps.services.BusServices;

@WebServlet(urlPatterns = {"/bus"})
public  class BusController extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res){
        BusServices busServices=new BusServices();
        busServices.getBusesService();
        try (PrintWriter out = res.getWriter()) {
            out.println("Ok");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res){
        
        try (PrintWriter out = res.getWriter()) {
            out.println("Ok");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
