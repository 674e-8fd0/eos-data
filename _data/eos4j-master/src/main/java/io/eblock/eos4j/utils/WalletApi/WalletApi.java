package io.eblock.eos4j.utils.WalletApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface WalletApi {
        @POST("/v1/wallet/create")
        Call<String> createWallet(@Body String walletName);
        @POST("/v1/wallet/import_key")
        Call<Void> importKey(@Body List<String> requestFields);
        @POST("/v1/wallet/unlock")
        Call<Void> unlockWallet(@Body List<String> requestFields);
        @POST("/v1/wallet/lock")
        Call<Void> lockWallet(@Body String walletName);

        @POST("/v1/wallet/open")
        Call<Void> openWallet(@Body String walletName);
}
