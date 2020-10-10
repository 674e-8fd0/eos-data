package io.eblock.eos4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.eblock.eos4j.utils.balacne_order;

public class JDBC {

       public static void main(String[] args){
              walletANDower res= JDBC2("ly");
            System.out.println(res.ower+"\n"+res.wallet_passord);

       }

       public static void INSER(String s){

              Connection conn=null;
              Statement stmt=null;
              PreparedStatement pstmt1=null;
              try{
                     Class.forName("com.mysql.jdbc.Driver");
                     String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/zhishu_blockchain_system";
                     String user="root";
                     String password="19990206lyz";
                      conn =DriverManager.getConnection(url,user,password);
                     String sql="INSERT INTO Wallet(wallet_name,wallet_password) values(?,?)";
                     pstmt1=conn.prepareStatement(sql);
                     pstmt1.setString(1,"defult");
                     pstmt1.setString(2,"PW5JhUSR2uAKWTLiNLUTuN1SpRngKfLQy2H46R2rH58iKRKshbL8S");
                     int res=pstmt1.executeUpdate();
                     if(res>0){
                            System.out.println("nice");
                     }
                     pstmt1.close();
                     conn.close();
              } catch (ClassNotFoundException ex) {
                     ex.printStackTrace();
              } catch (SQLException ex) {
                     ex.printStackTrace();
              }
}
public static int update(String account,String balance){
              String str="";
              Connection conn=null;
              Statement stmt=null;
              PreparedStatement pstmt1=null;
              int rs =0;
              int res= 0;
              try{
                     Class.forName("com.mysql.jdbc.Driver");
                     String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/ly";
                     String user="root";
                     String password="19990206lyz";
                     conn = DriverManager.getConnection(url,user,password);
                     String sql="UPDATE balance SET balance= ?" +"WHERE account_name = ?;";
                     pstmt1=conn.prepareStatement(sql);

                     pstmt1.setString(2,account);
                     pstmt1.setString(1,balance);
                     //  int res=pstmt1.executeUpdate();
                     rs = pstmt1.executeUpdate();

                     if(true){
                            System.out.println("nice");
                     }
                     pstmt1.close();
                     conn.close();
              } catch (ClassNotFoundException ex) {
                     ex.printStackTrace();
              } catch (SQLException ex) {
                     ex.printStackTrace();
              }
              return  res;

       }
       public static walletANDower JDBC2(String string){
              String str="";
              Connection conn=null;
              Statement stmt=null;
              PreparedStatement pstmt1=null;
              ResultSet rs = null;
              walletANDower res= new walletANDower();
              try{
                     Class.forName("com.mysql.jdbc.Driver");
                     String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/ly";
                     String user="root";
                     String password="19990206lyz";
                     conn = DriverManager.getConnection(url,user,password);
                     String sql=" SELECT pk,wallet_password from eos_wallet where account_name = ?";
                     pstmt1=conn.prepareStatement(sql);

                     pstmt1.setString(1,string);

                   //  int res=pstmt1.executeUpdate();
                     rs = pstmt1.executeQuery();
                     while(rs.next()){

                            res.ower=rs.getString("pk");
                            String s=rs.getString("wallet_password");
                            res.wallet_passord=stringReplace(rs.getString("wallet_password"));
                            System.out.println(res);
                           System.out.println(s);
                     }
                     if(true){
                            System.out.println("nice");
                     }
                     pstmt1.close();
                     conn.close();
              } catch (ClassNotFoundException ex) {
                     ex.printStackTrace();
              } catch (SQLException ex) {
                     ex.printStackTrace();
              }
              return  res;
       }
       public static String stringReplace(String string) {
              //去掉" "号
              string= string.replace("\"", "");
              return string ;
       }
       //下面是获得banlance_order
       public static List<balacne_order> balacne_orders(){
              String str="";
              Connection conn=null;
              Statement stmt=null;
              PreparedStatement pstmt1=null;
              ResultSet rs = null;
              List<balacne_order> res= new ArrayList<balacne_order>();
              try{
                     Class.forName("com.mysql.jdbc.Driver");
                     String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/ly";
                     String user="root";
                     String password="19990206lyz";
                     conn = DriverManager.getConnection(url,user,password);
                     String sql=" SELECT account_name, balance FROM balance ORDER BY balance  DESC";
                     pstmt1=conn.prepareStatement(sql);


                     //  int res=pstmt1.executeUpdate();
                     rs = pstmt1.executeQuery();
                     while(rs.next()){
                            balacne_order ba=new balacne_order();
                            ba.account=rs.getString("account_name");
                            ba.balance=rs.getString("balance");
                            res.add(ba);
                     }
                     if(true){
                            System.out.println("nice");
                     }
                     pstmt1.close();
                     conn.close();
              } catch (ClassNotFoundException ex) {
                     ex.printStackTrace();
              } catch (SQLException ex) {
                     ex.printStackTrace();
              }
              return  res;
       }

}

