package io.jafka.jeos;

import com.google.gson.Gson;
import io.jafka.jeos.EosApiFactory;
import io.jafka.jeos.LocalApi;
import io.jafka.jeos.impl.EosWalletApiService;

public class  Main{
    public static final String API_URL = "http://127.0.0.1:8889";
    public static final String create_wallet_name = "newtest";

    final String creator = "shijiebanggg";
    final String newAccountName = "womenshi1111";
    private static String wallet_private_key_1;
    private static String wallet_private_key_2;
    private static String wallet_public_key_1;
    private static String wallet_public_key_2;
    static _Generator_private_keys _Creat_private_keys = new _Generator_private_keys();
    private static EosWalletApiService eosWalletApiService;
    public static void main(String[] args) {



        //先生成一个密钥
        wallet_private_key_1 = _Creat_private_keys.Create_prviate_keys();
        wallet_public_key_1 = _Creat_private_keys.Create_Public_keys();
        wallet_private_key_2 = _Creat_private_keys.Create_prviate_keys();
        wallet_public_key_2 = _Creat_private_keys.Create_Public_keys();

        EosApi creat_wallet = EosApiFactory.create(API_URL);
        String gson=new Gson().toJson(creat_wallet.createWallet(create_wallet_name));
        //返回的是一个response.body()即钱包密码
        System.out.println("钱包密码"+gson);
        //现在给钱包导入密钥
        creat_wallet.importKeyIntoWallet(create_wallet_name,wallet_private_key_1);
        creat_wallet.importKeyIntoWallet(create_wallet_name,wallet_private_key_2);
        //现在创建账户
        //creat_wallet.
        System.out.println( wallet_private_key_1+wallet_public_key_1+ wallet_private_key_2+ wallet_public_key_2);


    }


}

