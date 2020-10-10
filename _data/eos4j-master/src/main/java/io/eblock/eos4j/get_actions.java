package io.eblock.eos4j;

import com.google.gson.Gson;
import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.Eosapi;
import io.eblock.eos4j.utils.WalletApi.action.Action;
import io.eblock.eos4j.utils.WalletApi.action.Actions;

public class get_actions {
    private static String API_URL_1 = new String("http://182.92.171.204:8889");
    private static String API_URL_2= new String("http://182.92.171.204:8879");

    private static String  acountName="eosio.token";
    private static Integer pos=1;
    private static Integer offset=2;
    public static void main(String[] args){

        Actions actions= Get_action("ly",0,2);
        Gson gson = new Gson();
        System.out.println(gson.toJson(actions));
    }

    public static Actions Get_action(String acountName, Integer pos, Integer offset){

        Eosapi eosapi= ApiFactory.create(API_URL_2);
        Actions actions=eosapi.getActions(acountName,pos,offset);
        Gson gson = new Gson();
        System.out.println(gson.toJson(actions));

        return actions;
    }
}
