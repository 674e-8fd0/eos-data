package io.eblock.eos4j;

import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.Eosapi;
import io.eblock.eos4j.utils.WalletApi.TransactionRequest;

public class Transaction {
    private static String API_URL_1 = new String("http://182.92.171.204:8889");
    private static String API_URL_2= new String("http://182.92.171.204:8879");

  public static void main(String[] args){

      Object res=getTransaction("dbe78aae37173a1dda17ec94286872694c3f38c123ffe6d724e667fd02e3b7a1");
      System.out.println(res);
  }

     public static Object getTransaction(String id){
        Eosapi eosapi= ApiFactory.create(API_URL_2);
        TransactionRequest req=new TransactionRequest(id);
        Object res=eosapi.getTransaction(req);
        System.out.println(res);
        return res;

    }
}
