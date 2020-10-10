package servlet;
import com.google.gson.Gson;
//import dao.XuankeDao;
//import domain.XuankeItem;
import io.eblock.eos4j.creat_account;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
@WebServlet(name = "Creat_account_Servlet")
public class Creat_account_Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // super.doGet(req, resp);
        PrintWriter re = response.getWriter();
        String res=request.getParameter("get");
        if(res.equals("get")) {

            re.print("doget");
        }
        else{
            re.print("doget error");
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter index = response.getWriter();

      String wallet_name="ls";
      String Account_name="ls";
      String wa=request.getParameter("wallet");
      String ac=request.getParameter("account");
        if( wa.isEmpty()||ac.isEmpty()){

           index.print("钱包或者账户名字为空");
        }

       else{
            response.setContentType("text/html");
            int rs=creat_account.creat(wa,ac);
            index.print(1);

            System.out.println(rs);

        }

    }
}
