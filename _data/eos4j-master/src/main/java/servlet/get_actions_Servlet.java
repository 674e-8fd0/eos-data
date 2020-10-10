package servlet;

import com.google.gson.Gson;
import io.eblock.eos4j.ese.Action;
import io.eblock.eos4j.get_actions;
import io.eblock.eos4j.query;
import io.eblock.eos4j.utils.WalletApi.action.Actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class get_actions_Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String wa=request.getParameter("account");
        Integer pos= Integer.valueOf(request.getParameter("pos"));
        Integer offset= Integer.valueOf(request.getParameter("offset"));


        if(wa.isEmpty()||pos==null||offset==null){
            out.print("查询account,pos,offset名字为空");
        }
        else{
            Gson gson=new Gson();
            Actions res= get_actions.Get_action(wa,pos,offset);

            out.println(gson.toJson(res));

        }

    }
}
