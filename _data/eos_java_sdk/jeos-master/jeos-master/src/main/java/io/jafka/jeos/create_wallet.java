package io.jafka.jeos;
import com.google.gson.Gson;
import io.jafka.jeos.core.request.chain.json2bin.Creatwallet;
import io.jafka.jeos.exception.EosApiException;
import io.jafka.jeos.impl.EosApiServiceGenerator;
import io.jafka.jeos.impl.EosWalletApiService;
import io.jafka.jeos._Generator_private_keys;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class create_wallet {
    //创建以一个钱包并导入密钥
    public static final String API_URL = "http://127.0.0.1:8889";
    public static final String create_wallet_name = "qwerts";

    private static String wallet_private_key;

    private static String wallet_public_key;
    static _Generator_private_keys _Creat_private_keys = new _Generator_private_keys();
    private static EosWalletApiService eosWalletApiService;
    //实例化一个接口

    public static void main(String[] args) {
        //先生成一个密钥
        wallet_private_key = _Creat_private_keys.Create_prviate_keys();
        wallet_public_key = _Creat_private_keys.Create_Public_keys();

        EosApi creat_wallet = EosApiFactory.create(API_URL);
        String gson=new Gson().toJson(creat_wallet.createWallet(create_wallet_name));

        //返回的是一个response.body()即钱包密码
        System.out.println(gson);
        //现在给钱包导入密钥
        creat_wallet.importKeyIntoWallet(create_wallet_name,wallet_private_key);


    }
}

