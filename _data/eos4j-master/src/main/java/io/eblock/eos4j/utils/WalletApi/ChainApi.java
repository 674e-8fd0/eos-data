package io.eblock.eos4j.utils.WalletApi;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBin;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBinRequest;
import io.eblock.eos4j.utils.WalletApi.action.Actions;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

public interface ChainApi {
    @POST("/v1/chain/get_currency_balance")
    Call<List<String>> getCurrencyBalance(@Body Map<String, String> requestFields);

    @POST("/v1/history/get_transaction")
    Call<Object> getTransaction(@Body TransactionRequest transactionRequest);

    @POST("/v1/history/get_actions")
    Call<Actions> getActions(@Body Map<String, Object> requestFields);


    @POST("/v1/chain/abi_json_to_bin")
    Call<AbiJsonToBin> abiJsonToBin(@Body AbiJsonToBinRequest abiJsonToBinRequest);

    @GET("/v1/chain/get_info")
    Call<ChainInfo> getChainInfo();

    @POST("/v1/chain/get_block")
    Call<Block> getBlock(@Body Map<String, String> requestFields);
}
