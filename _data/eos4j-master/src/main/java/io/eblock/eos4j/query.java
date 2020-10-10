package io.eblock.eos4j;

import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.Eosapi;

import java.util.ArrayList;
import java.util.List;

public class query {

    //查询是根据链上的信息来的，api服务。

    private static String account="";  //账户名称
    private static String code="eosio.token";     //合约代码
    private static String symbol="LYSSS";   //币的种类
    private static String API_URL_2= new String("http://182.92.171.204:8879");
    public static List<String> get_current_balcance(String account,String code,String symbol){
        Eosapi eosapi= ApiFactory.create(API_URL_2);
        //(String code, String accountName, String symbol);
        List<String> res=new ArrayList<>();
         res= eosapi.getCurrencyBalance(code,account,symbol);
        int s=1;
        return res;
    }
    public static List<String> get_current_balcance(String account){
        Eosapi eosapi= ApiFactory.create(API_URL_2);
        //(String code, String accountName, String symbol);
        List<String> res=new ArrayList<>();
        res= eosapi.getCurrencyBalance(code,account,symbol);
        int s=1;
        return res;
    }
}
