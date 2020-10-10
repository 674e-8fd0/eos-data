package io.jafka.jeos;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//import org.apache.log4j.BasicConfigurator;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jafka.jeos.EosApi;
import io.jafka.jeos.EosApiFactory;
import io.jafka.jeos.core.common.transaction.PackedTransaction;
import io.jafka.jeos.core.common.transaction.SignedPackedTransaction;
import io.jafka.jeos.core.common.transaction.TransactionAction;
import io.jafka.jeos.core.common.transaction.TransactionAuthorization;
import io.jafka.jeos.core.request.chain.AbiJsonToBinRequest;
import io.jafka.jeos.core.request.chain.json2bin.BuyRamArg;
import io.jafka.jeos.core.request.chain.json2bin.CreateAccountArg;
import io.jafka.jeos.core.request.chain.json2bin.DelegatebwArg;
import io.jafka.jeos.core.response.chain.AbiJsonToBin;
import io.jafka.jeos.core.response.chain.Block;
import io.jafka.jeos.core.response.chain.account.Key;
import io.jafka.jeos.core.response.chain.account.RequiredAuth;
import io.jafka.jeos.core.response.chain.transaction.PushedTransaction;
import io.jafka.jeos.exception.EosApiException;
import io.jafka.jeos.impl.EosApiServiceGenerator;

public class CreateAccount {

    static void create(EosApi client) throws Exception {
        ObjectMapper mapper = EosApiServiceGenerator.getMapper();

        final String creator = "shijiebanggg";
        final String newAccountName = "womenshi1111";
        // ① build binary of create account
        CreateAccountArg createAccountArg = new CreateAccountArg();
        {
            createAccountArg.setCreator(creator);
            createAccountArg.setName(newAccountName);
            // set the owner key
            RequiredAuth owner = new RequiredAuth();
            owner.setThreshold(1L);
            owner.setAccounts(Collections.EMPTY_LIST);
            owner.setWaits(Collections.EMPTY_LIST);
            owner.setKeys(Arrays.asList(new Key("EOS7XP7Ks7j68Uh64HGTEeiaMsgAgKcuZbYAAf86SoPLBpxcBX5it", 1)));

            createAccountArg.setOwner(owner);
            // ------------------------------------------------------
            // set the active key
            RequiredAuth active = new RequiredAuth();
            active.setThreshold(1L);
            active.setAccounts(Collections.EMPTY_LIST);
            active.setWaits(Collections.EMPTY_LIST);
            active.setKeys(Arrays.asList(new Key("EOS7XP7Ks7j68Uh64HGTEeiaMsgAgKcuZbYAAf86SoPLBpxcBX5it", 1)));

            createAccountArg.setActive(active);
        }

        AbiJsonToBin createAccountData = client.abiJsonToBin("eosio", "newaccount", createAccountArg);
        System.out.println("create account bin= " + createAccountData.getBinargs());

        // ② build binary of ram
        BuyRamArg buyRamArg = new BuyRamArg(creator,newAccountName, 1024 * 8);//8k memory
        AbiJsonToBin buyRamData = client.abiJsonToBin("eosio","buyrambytes", buyRamArg);
        System.out.println("buy ram bin= "+ buyRamData.getBinargs());

        // ③ delegatebw cpu and net
        DelegatebwArg delegatebwArg = new DelegatebwArg(creator, newAccountName, "0.1000 EOS", "0.1000 EOS", 0L);
        AbiJsonToBin delegatebwData = client.abiJsonToBin("eosio", "delegatebw", delegatebwArg);
        System.out.println("delegatebw bin= "+delegatebwData.getBinargs());


        // ④ get the latest block info
        Block block = client.getBlock(client.getChainInfo().getHeadBlockId());
        System.out.println("blockNum=" + block.getBlockNum());

        // ⑤ create the authorization
        List<TransactionAuthorization> authorizations = Arrays.asList(new TransactionAuthorization(creator, "active"));

        // ⑥ build the all actions
        List<TransactionAction> actions = Arrays.asList(//
                new TransactionAction("eosio","newaccount",authorizations, createAccountData.getBinargs())//
                ,new TransactionAction("eosio","buyrambytes",authorizations, buyRamData.getBinargs())//
                ,new TransactionAction("eosio","delegatebw",authorizations, delegatebwData.getBinargs())//
        );

        // ⑦ build the packed transaction
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix());
        packedTransaction.setRefBlockNum(block.getBlockNum());
        // expired after 3 minutes
        LocalDateTime expiration = LocalDateTime.parse(ZonedDateTime.now(ZoneId.of("GMT")).plusMinutes(3)//
                .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        packedTransaction.setExpiration(expiration);
        packedTransaction.setRegion("0");
        packedTransaction.setMaxNetUsageWords(0);
        packedTransaction.setMaxCpuUsageMs(0);
        packedTransaction.setActions(actions);

        // ⑧ unlock the creator's wallet
        try {
            client.unlockWallet("default", "PW5KGXiGoDXEM54YWn6yhjCmNkAwpyDemLUqRaniAwuhTArciS6j9");
        } catch (EosApiException ex) {
            System.err.println(ex.getMessage());
        }

        // ⑨ sign the transaction
        SignedPackedTransaction signedPackedTransaction = client.signTransaction(packedTransaction, //
                Arrays.asList("EOS7XP7Ks7j68Uh64HGTEeiaMsgAgKcuZbYAAf86SoPLBpxcBX5it"), //
                "038f4b0fc8ff18a4f0842a8f0564611f6e96e8535901dd45e43ac8691a1c4dca");

        System.out.println("signedPackedTransaction=" + mapper.writeValueAsString(signedPackedTransaction));
        System.out.println("\n--------------------------------\n");

        // ⑩ push the signed transaction
        PushedTransaction pushedTransaction = client.pushTransaction("none", signedPackedTransaction);
        System.out.println("pushedTransaction=" + mapper.writeValueAsString(pushedTransaction));

    }

    public static void main(String[] args) throws Exception {
       // BasicConfigurator.configure();
        EosApi client = EosApiFactory.create("http://127.0.0.1:8888"
                );;
        // ------------------------------------------------------------------------
        create(client);
    }

}