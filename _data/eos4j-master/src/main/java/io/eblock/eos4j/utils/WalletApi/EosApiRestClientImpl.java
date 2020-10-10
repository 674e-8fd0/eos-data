package io.eblock.eos4j.utils.WalletApi;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBin;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBinRequest;
import io.eblock.eos4j.utils.WalletApi.action.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class EosApiRestClientImpl implements Eosapi {
    private final WalletApi walletApi;
    private final ChainApi chainapi;
    public  EosApiRestClientImpl(String baseUrl){
        //API接口实例化
        //API api=retofit.create(API.class)
        //Call<> call=api.接口名称.(传值)
        //Response response=call.execute()
        walletApi = EosApiServiceGenerator.createService(WalletApi.class, baseUrl);
         chainapi =EosApiServiceGenerator.createService(ChainApi.class, baseUrl);
    }



    @Override
    public String createWallet(String walletName) {
        return EosApiServiceGenerator.executeSync(walletApi.createWallet(walletName));
    }

    @Override
    public void importKeyIntoWallet(String walletName, String walletKey) {
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(walletKey);
        EosApiServiceGenerator.executeSync(walletApi.importKey(requestFields));
    }
    @Override
    public void unlockWallet(String walletName, String walletPassword){
        List<String> requestFields = new ArrayList<>(2);

        requestFields.add(walletName);
        requestFields.add(walletPassword);
        EosApiServiceGenerator.executeSync(walletApi.unlockWallet(requestFields));
    }
    @Override
    public void lockWallet(String walletName){
        EosApiServiceGenerator.executeSync(walletApi.lockWallet(walletName));
    }
    @Override
    public List<String> getCurrencyBalance(String code, String accountName, String symbol){
        LinkedHashMap<String, String> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("code", code);
        requestParameters.put("account", accountName);
        requestParameters.put("symbol", symbol);

        return EosApiServiceGenerator.executeSync(chainapi.getCurrencyBalance(requestParameters));
    }
    @Override
    public void openWallet(String walletName){
        EosApiServiceGenerator.executeSync(walletApi.openWallet(walletName));
    }
    @Override
    public Object getTransaction(TransactionRequest transactionRequest){

        return EosApiServiceGenerator.executeSync(chainapi.getTransaction(transactionRequest));
    }
    @Override
    public Actions getActions(String accountName, Integer pos, Integer offset){
        LinkedHashMap<String, Object> requestParameters = new LinkedHashMap<>(3);

        requestParameters.put("account_name", accountName);
        requestParameters.put("pos", pos);
        requestParameters.put("offset", offset);

        return EosApiServiceGenerator.executeSync(chainapi.getActions(requestParameters));
    }

    @Override
    public <T> AbiJsonToBin abiJsonToBin(String code, String action, T args) {
        return EosApiServiceGenerator.executeSync(chainapi.abiJsonToBin(new AbiJsonToBinRequest(code, action, args)));
    }
    @Override
    public ChainInfo getChainInfo(){
        return EosApiServiceGenerator.executeSync(chainapi.getChainInfo());
    }

    @Override
    public Block getBlock(String blockNumberOrId){
        return EosApiServiceGenerator.executeSync(chainapi.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
    }
}
