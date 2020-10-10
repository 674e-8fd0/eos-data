package io.eblock.eos4j;

import io.eblock.eos4j.api.vo.transaction.Transaction;

public class account {


    public static  void main(String[] args){
    Rpc rpc=new Rpc("http://127.0.0.1:8888");
        System.out.println("============= 创建账户不抵押 ===============");
        try {
            Transaction t3 = rpc.createAccount("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3","eosio","bqs", "EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx","EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx", 8192l);
            System.out.println("创建成功 = " + t3.getTransactionId()+" \n ");
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
