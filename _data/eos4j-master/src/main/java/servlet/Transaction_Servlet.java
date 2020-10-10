package servlet;

import com.google.gson.Gson;
import io.eblock.eos4j.Transaction;
import io.eblock.eos4j.query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Transaction_Servlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id=request.getParameter("id");
        if(id.isEmpty()){
            out.print("查询交易id名字为空");
        }
        else{
            Object res= Transaction.getTransaction(id);
            Gson gson=new Gson();

            out.println(gson.toJson(res));
        }

    }
}

