package io.eblock.eos4j;

import com.google.gson.Gson;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.Eosapi;

import java.sql.*;
import java.util.UUID;

public class creat_account {

    private static String API_URL_1 = new String("http://182.92.171.204:8889");
    private static String API_URL_2= new String("http://182.92.171.204:8879");
   // private  static String wallet_name="ly";
    private static String creator="eosio";
    private static String creator_pk="5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
   // private static String newaccountname="lyssss";
    private static String wallet_password="";

    public static int creat(String wallet_name,String new_account_name){

        Eosapi eosapi= ApiFactory.create(API_URL_1);

        System.out.println("============= 通过种子生成私钥 ===============");
        //UUID生成种子
        String pk = Ecc.seedPrivate(UUID.randomUUID().toString());
        String pk1 = Ecc.seedPrivate(UUID.randomUUID().toString());
        System.out.println("private key :" + pk +"\n");
        System.out.println("private1 key :" + pk1 +"\n");

        System.out.println("============= 通过私钥生成公钥 ===============");
        String pu = Ecc.privateToPublic(pk);
        String pu1 = Ecc.privateToPublic(pk);
        System.out.println("public key :" + pu + " \n ");
        System.out.println("public1 key :" + pu + " \n ");

        System.out.println("============= 创建钱包 ===============");
       // eosapi.createWallet(wallet_name);
        String gson=new Gson().toJson(eosapi.createWallet(wallet_name));
        wallet_password=gson;
        System.out.println("钱包密码"+gson);
        System.out.println("============= 导入私钥到钱包 ===============");
        eosapi.importKeyIntoWallet(wallet_name,pk);
        eosapi.importKeyIntoWallet(wallet_name,pk1);
        Rpc rpc=new Rpc(API_URL_2);
        System.out.println("============= 创建账户不抵押 ===============");
        try {
            Transaction t3 = rpc.createAccount(creator_pk,creator,new_account_name,pu,pu1,8192l);
            System.out.println("创建成功 = " + t3.getTransactionId()+" \n ");
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        JDBC(wallet_name,pk,pk1,pu,pu1,new_account_name);
        int s=1;
        return s;
    }
    public static void JDBC(String wallet_name,String pk,String pk1,String pu,String pu1,String account_name){
        Connection conn=null;
        Statement stmt=null;
        PreparedStatement pstmt1=null;
        PreparedStatement pstmt2=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://cdb-box97608.bj.tencentcdb.com:10188/ly";
            String user="root";
            String password="19990206lyz";
            conn = DriverManager.getConnection(url,user,password);
            String sql="INSERT INTO eos_wallet(wallet_name,wallet_password,pk,pk1,pu,pu1,account_name) values(?,?,?,?,?,?,?)";
            pstmt1=conn.prepareStatement(sql);
            pstmt1.setString(1,wallet_name);
            pstmt1.setString(2,wallet_password);
            pstmt1.setString(3,pk);
            pstmt1.setString(4,pk1);
            pstmt1.setString(5,pu);
            pstmt1.setString(6,pu1);
            pstmt1.setString(7,account_name);

            pstmt1.executeUpdate();

            String sql_1="INSERT INTO balance(account_name,balance) values(?,?);";
            pstmt2=conn.prepareStatement(sql_1);
            pstmt2.setString(1,account_name);
            Double ba=0.0;
            pstmt2.setString(2,"0");  //初始账户的余额为零
            int res=pstmt2.executeUpdate();


            if(res>1){
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
}
