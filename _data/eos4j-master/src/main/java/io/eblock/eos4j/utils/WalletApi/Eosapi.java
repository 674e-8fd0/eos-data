package io.eblock.eos4j.utils.WalletApi;

import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBin;
import io.eblock.eos4j.utils.WalletApi.action.Actions;

import java.util.List;

public interface Eosapi {


    String createWallet(String walletName);

    void importKeyIntoWallet(String walletName, String walletKey);

    void lockWallet(String walletName);

    void unlockWallet(String walletName, String walletPassword);

    List<String> getCurrencyBalance(String code, String accountName, String symbol);

    void openWallet(String walletName);
    //TODO: changed day by day
    Object getTransaction(TransactionRequest transactionRequest);

    Actions getActions(String accountName, Integer pos, Integer offset);

    <T> AbiJsonToBin abiJsonToBin(String code, String action, T args);

    ChainInfo getChainInfo();

    Block getBlock(String blockNumberOrId);

}
