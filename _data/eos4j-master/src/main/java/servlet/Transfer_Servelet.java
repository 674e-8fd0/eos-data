package servlet;

import io.eblock.eos4j.Transfer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Transfer_Servelet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter index = response.getWriter();
        String from=request.getParameter("from");
        String to=request.getParameter("to");
        String qu=request.getParameter("quantity");
        String memo=request.getParameter("memo");
         if(from.isEmpty()||to.isEmpty()){
             index.print("请输入转入或转出账户");
         }
         else if(qu.isEmpty()){
             index.print("请输入转账金额");
         }
         else{
            System.out.println(qu);

           int s= Transfer.Transfer(from,to,qu,memo);
           index.print(s);
         }
    }

}
