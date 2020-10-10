package io.eblock.eos4j;

import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.EosApiException;
import io.eblock.eos4j.utils.WalletApi.Eosapi;

import java.math.BigInteger;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transfer {


    private static String API_URL_1 = new String("http://182.92.171.204:8889");
    private static String API_URL_2= new String("http://182.92.171.204:8879");
    private  static String wallet_name="rtssss";
    private  static String wallet_passord="rtssss";

    private static String pk="5JofnTPwe4HRfk8G7KEhQkxK5K6m7aUP6z6aXB8jWG6cua6Y4ZX";//需要转账账户的ower的私钥权限
    private static String from="ly";  //转账账户
    private static String to="lyz";   //接收账户
    private static String quantity="120.00";
    private static String memo="idea";
    public static void main(String[] args){
      int s=  Transfer(from,to,quantity,"idea");
    }


    public static int  Transfer(String from,String to,String quantity,String memo){
       System.out.println(quantity);
  // public static void main(String[] args){
        walletANDower res= new walletANDower();
         List<String> balance=new ArrayList<>();
         balance=query.get_current_balcance(from); //网络错误呢；// 查询余额
         String num1=  String_to_Int(balance.toString());   //余额转换成数字
        //String num2= DoubletoString(quantity);
        String num2= quantity;
         System.out.println(num2);
          int s=0;
          //比较大小
         double f1= Double.valueOf(num1).doubleValue();
         double f2=Double.valueOf(num2).doubleValue();

           //钱包和账户名字相同

          if(f2>f1){
              s=-1;
             return s;//余额不足返回-1
          }
         else{
             //查询钱包的秘钥和转账账户的ower
             JDBC jdbc =new JDBC();
            res= jdbc.JDBC2(from);

              Eosapi eosapi= ApiFactory.create(API_URL_1);
              try{
                 // 钱包和账户名字相同
                  eosapi.unlockWallet(from,res.wallet_passord);
              }
              catch (EosApiException ex) {
                  System.err.println(ex.getMessage());
              }
              Rpc rpc=new Rpc(API_URL_2);
             //给num2加zero
              num2=zero(num2);

              String coin=" LYSSS";
              num2=num2+coin;
              System.out.println(quantity);
              try {
                  System.out.println(num2);
                  Transaction t1 = rpc.transfer(res.ower,"eosio.toke n", from,to, num2, memo);
                  System.out.println("转账成功 = " + t1.getTransactionId()+" \n ");
                  System.out.println("转账成功 = " + t1+" \n ");

                  List<String> update_balance=new ArrayList<>();
                   update_balance=query.get_current_balcance(from);
                  String from_balance=  String_to_Int(balance.toString());
                  double acc1= Double.valueOf(from_balance).doubleValue();
                  JDBC.update(from,from_balance);

                  List<String> update_to_balance=new ArrayList<>();
                  update_to_balance=query.get_current_balcance(to);
                  String to_balance=  String_to_Int(update_to_balance.toString());
                  double acc2= Double.valueOf(to_balance).doubleValue();
                  JDBC.update(to,to_balance);
                  System.out.println(acc1+" \n "+acc2);

                  s=1;
              }catch(Exception ex) {
                  ex.printStackTrace();
                  return s;
                  //出错返回0
              }
             //return 1;//成功返回 1
          }
         return s;
    }
    public static String String_to_Int(String str){
        Pattern compile = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String num = matcher.group();//提取匹配到的结果
       //System.out.println(num);//0.00
        return num;

    }
    public static String stringReplace(String string) {
        //去掉" "号
         string= string.replace("\"", "");
        return string ;
    }
    public static String zero(String string){
        //处理字符串
        String [] q=new String[] {"0","00","000","0000","00000","000000","0000000","00000000","000000000","0000000000"};
        String strOrig = string;
        int intIndex = strOrig.indexOf(".");
        int intIndex1 = strOrig.length();
        int L=intIndex1-intIndex-1;

        if(intIndex == - 1){
            String qw1=strOrig+"."+q[9];
            System.out.println(qw1);
            return qw1;
        }
        else if (L==10)
        {
            return string;
        }
        else{

            String qw=strOrig+q[9-L];
            System.out.println("Hello 字符串位置:" + intIndex+" "+intIndex1+" "+L);
            System.out.println("\n"+qw);
            return qw;
        }
    }
    /*
     * double转为string 并且保留两位小数
     * */
//    public static String DoubletoString(double dou){
//        DecimalFormat df = new DecimalFormat("#.00");
//        String str = df.format(dou);
//        String str1=" "+dou;
//        return str1.trim() ;
//    }
//    public static String JDBC(String string){
//         String str="";
//        Connection conn=null;
//        Statement stmt=null;
//        PreparedStatement pstmt1=null;
//        ResultSet rs = null;
//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/zhishu_blockchain_system";
//            String user="root";
//            String password="19990206lyz";
//            conn = DriverManager.getConnection(url,user,password);
//            String sql=" select pk from eos_wallet where accountname = ?";
//            pstmt1=conn.prepareStatement(sql);
//
//            pstmt1.setString(1,string);
//
//            int res=pstmt1.executeUpdate();
//              rs = pstmt1.executeQuery();
//            if(res>0){
//                System.out.println("nice");
//            }
//            pstmt1.close();
//            conn.close();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return  str;
//    }

}
