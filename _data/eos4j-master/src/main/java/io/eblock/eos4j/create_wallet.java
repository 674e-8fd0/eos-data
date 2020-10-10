package io.eblock.eos4j;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class create_wallet {
    public static final String API_URL = "http://127.0.0.1:8889";
    public static final String create_wallet_name = "qc";
    private static String wallet_private_key;
    private static String wallet_public_key;
   // private static EosWalletApiService eosWalletApiService;
   private static OkHttpClient httpClient;
    //实例化一个接口
   private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        mapper.findAndRegisterModules();
        //
        httpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
    }

    public static void main(String[] args) throws IOException {
        //先生成一个钱包
        Retrofit retrofit =new Retrofit.Builder()
                                .baseUrl(API_URL)
                                .addConverterFactory(JacksonConverterFactory.create(mapper))
                                .build();
        WalletRPC walletRPC=retrofit.create(WalletRPC.class);
        Call call=walletRPC.createWallet("name");
        Response response=call.execute();
        System.out.println(response.body());


        Retrofit retrofit1=new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();;
        WalletRPC walletRPC2=retrofit.create(WalletRPC.class);
        List<String> keys=new ArrayList<>();
        keys.add(wallet_private_key);
         Call call1=walletRPC2.importKey(keys);
        Response response1=call1.execute();
        System.out.println(response1.body());

    }
    public interface WalletRPC{
        @POST("/v1/wallet/create")
        Call<String> createWallet(@Body String walletName);
        @POST("/v1/wallet/import_key")
        Call<Void> importKey(@Body List<String> requestFields);

    }

}
