package servlet;
import com.google.gson.Gson;
import io.eblock.eos4j.query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Get_current_balance extends HttpServlet {

      //查询余额，输入的账户名字
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String wa=request.getParameter("account");
        String code=request.getParameter("code");
        String symbol=request.getParameter("symbol");
        if(wa.isEmpty()||code.isEmpty()||symbol.isEmpty()){
            out.print("查询账户名字为空");
        }
        else{
            List<String> res=new ArrayList<>();
            res=query.get_current_balcance(wa,code,symbol);
            Gson gson =new Gson();

            out.println(gson.toJson(res));

        }

    }
    }
