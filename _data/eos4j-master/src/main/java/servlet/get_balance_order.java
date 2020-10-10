package servlet;

import com.google.gson.Gson;
import io.eblock.eos4j.JDBC;
import io.eblock.eos4j.utils.balacne_order;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class get_balance_order extends GenericServlet {

    @Override
    public void service(ServletRequest Request, ServletResponse Response) throws ServletException, IOException {
        Response.setContentType("text/html;charset=UTF-8");
        PrintWriter res = Response.getWriter();

//        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();

        List<balacne_order> balacne_orders=new ArrayList<balacne_order>();
        balacne_orders= JDBC.balacne_orders();
        System.out.println(gson.toJson(balacne_orders));
        res.println(gson.toJson(balacne_orders));
    }
}
