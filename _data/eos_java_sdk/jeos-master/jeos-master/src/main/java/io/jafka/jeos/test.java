package io.jafka.jeos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jafka.jeos.core.common.transaction.PackedTransaction;
import io.jafka.jeos.core.common.transaction.SignedPackedTransaction;
import io.jafka.jeos.core.common.transaction.TransactionAction;
import io.jafka.jeos.core.common.transaction.TransactionAuthorization;
import io.jafka.jeos.core.request.chain.json2bin.BuyRamArg;
import io.jafka.jeos.core.request.chain.json2bin.CreateAccountArg;
import io.jafka.jeos.core.request.chain.json2bin.Creatwallet;
import io.jafka.jeos.core.request.chain.json2bin.DelegatebwArg;
import io.jafka.jeos.core.response.chain.AbiJsonToBin;
import io.jafka.jeos.core.response.chain.Block;
import io.jafka.jeos.core.response.chain.ChainInfo;
import io.jafka.jeos.core.response.chain.account.Key;
import io.jafka.jeos.core.response.chain.account.RequiredAuth;
import io.jafka.jeos.core.response.chain.transaction.PushedTransaction;
import io.jafka.jeos.exception.EosApiException;
import io.jafka.jeos.impl.EosApiServiceGenerator;
import io.jafka.jeos.impl.EosWalletApiService;
import io.jafka.jeos._Generator_private_keys;
import io.jafka.jeos.util.utils.TxSign;
import retrofit2.Call;
import retrofit2.Response;
import java.time.LocalDateTime;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {
    //创建以一个钱包并导入密钥
     private static String API_URL_1 = new String("http://127.0.0.1:8889");
    private static String API_URL_2= new String("http://127.0.0.1:8888");
    public static final String create_wallet_name = "kvfsss";

    private static String wallet_private_key_1;
    private static String wallet_private_key_2;
    private static String wallet_public_key_1;
    private static String wallet_public_key_2;

    final static String creator = "eosio";
    final static String newAccountName = "ijne";
    static _Generator_private_keys _Creat_private_keys = new _Generator_private_keys();
    private static EosWalletApiService eosWalletApiService;
    //实例化一个接口

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = EosApiServiceGenerator.getMapper();

        //先生成一个密钥
        wallet_private_key_1 = _Creat_private_keys.Create_prviate_keys();
        wallet_public_key_1 = _Creat_private_keys.Create_Public_keys();
        wallet_private_key_2 = _Creat_private_keys.Create_prviate_keys();
        wallet_public_key_2 = _Creat_private_keys.Create_Public_keys();
        System.out.println(wallet_private_key_1+"\n"+wallet_private_key_2+"\n"+wallet_public_key_1+"\n"+wallet_public_key_2);

        EosApi creat_wallet = EosApiFactory.create(API_URL_1);
        String gson=new Gson().toJson(creat_wallet.createWallet(create_wallet_name));
        //返回的是一个response.body()即钱包密码
        System.out.println("钱包密码"+gson);
        //现在给钱包导入密钥
        creat_wallet.importKeyIntoWallet(create_wallet_name,wallet_private_key_1);
        creat_wallet.importKeyIntoWallet(create_wallet_name,wallet_private_key_2);
        //现在创建账户
        //creat_wallet.
        //下面那步是做成json格式并变成bin格式的
        CreateAccountArg createAccountArg = new CreateAccountArg();
        {
            createAccountArg.setCreator(creator);
            createAccountArg.setName(newAccountName);
            // set the owner key
            RequiredAuth owner = new RequiredAuth();
            owner.setThreshold(1L);
            owner.setAccounts(Collections.EMPTY_LIST);
            owner.setWaits(Collections.EMPTY_LIST);
            owner.setKeys(Arrays.asList(new Key(wallet_public_key_1, 1)));

            createAccountArg.setOwner(owner);
            // ------------------------------------------------------
            // set the active key
            RequiredAuth active = new RequiredAuth();
            active.setThreshold(1L);
            active.setAccounts(Collections.EMPTY_LIST);
            active.setWaits(Collections.EMPTY_LIST);
            active.setKeys(Arrays.asList(new Key(wallet_public_key_2, 1)));

            createAccountArg.setActive(active);
        }
        EosApi creat_wallet_2= EosApiFactory.create(API_URL_2);
        AbiJsonToBin createAccountData = creat_wallet_2.abiJsonToBin("eosio", "newaccount", createAccountArg);
        System.out.println("create account bin= " + createAccountData.getBinargs());
       //现在是第一步将区块的abi_json_ti_bin
        //简书 https://www.jianshu.com/p/84f6d031c0cb
        //chain是nodeos命令进行交互和wallet是不一样的端口号
        // ② build binary of ram
//       //找不到这个buyRambytes这个动作
//        BuyRamArg buyRamArg = new BuyRamArg(creator,newAccountName, 1024 * 8);//8k memory
//        AbiJsonToBin buyRamData = creat_wallet_2.abiJsonToBin("eosio","buyrambytes", buyRamArg);
//         System.out.println("buy ram bin= "+ buyRamData.getBinargs());

        // ③ delegatebw cpu and net
//        DelegatebwArg delegatebwArg = new DelegatebwArg(creator, newAccountName, "0.1000 EOS", "0.1000 EOS", 0L);
//        AbiJsonToBin delegatebwData = creat_wallet_2.abiJsonToBin("eosio", "delegatebw", delegatebwArg);
//        System.out.println("delegatebw bin= "+delegatebwData.getBinargs());


        // ④ get the latest block info
        Block block = creat_wallet_2.getBlock(creat_wallet_2.getChainInfo().getHeadBlockId());
        System.out.println("\n"+"ahfsufhvuig"+"\n"+"\n"+"\n");
        System.out.println("113fytfytfblockNum=" + block.getBlockNum());

        ChainInfo chainInfo=creat_wallet_2.getChainInfo();
        String chain_id=chainInfo.getChainId();

        // ⑤ create the authorization
        List<TransactionAuthorization> authorizations = Arrays.asList(new TransactionAuthorization(creator, "active"));
       TransactionAction array=new TransactionAction("eosio","newaccount",authorizations, createAccountData.getBinargs());
        // ⑥ build the all actions


        List<TransactionAction> actions = Arrays.asList(
                new TransactionAction("eosio","newaccount",authorizations, createAccountData.getBinargs())

//                ,new TransactionAction("eosio","buyrambytes",authorizations, buyRamData.getBinargs())//
//                ,new TransactionAction("eosio","delegatebw",authorizations, delegatebwData.getBinargs())//
        );
      // actions.add(array);

        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix());
        packedTransaction.setRefBlockNum(block.getBlockNum());
        // expired after 3 minutes
        LocalDateTime  expiration = LocalDateTime.parse(ZonedDateTime.now(ZoneId.of("GMT")).plusMinutes(3)//
                .truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        packedTransaction.setExpiration(expiration);
        packedTransaction.setRegion("0");
        packedTransaction.setMaxNetUsageWords(0);
        packedTransaction.setMaxCpuUsageMs(0);
        packedTransaction.setActions(actions);
        try {
            creat_wallet.unlockWallet("default", "PW5JhUSR2uAKWTLiNLUTuN1SpRngKfLQy2H46R2rH58iKRKshbL8S");
        } catch (EosApiException ex) {
            System.err.println(ex.getMessage());
        }

        // ⑨ sign the transaction
        System.out.println(wallet_public_key_2);
        SignedPackedTransaction signedPackedTransaction = creat_wallet.signTransaction(packedTransaction, //
                Arrays.asList("EOS8HfadwqVGTqdr89VNeARjc2x7dAWB4Li3cH1XPC4nVrpjECVtD"), //第二个active权限
                chain_id);

        createofflinsign sign=new createofflinsign();
        String pk="5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";

        String s=sign.signTransaction(pk,new TxSign(signParam.getChainId(), tx));
        List<String> s1 =new ArrayList();
        s1.add(s);
        System.out.println(s);
        signedPackedTransaction.setSignatures(s1);
     //SIG_K1_KiUUL4pSc82JKThxNfjsxXVPRxVSsjv4UPEHNjkxEwVXGaGfN7rJj3eHfd2wZ5t7f4Xot2FMTbEkFFfpKE5FrgrbRwhHZs

        System.out.println("signedPackedTransaction=" + mapper.writeValueAsString(signedPackedTransaction));
        System.out.println("\n--------------------------------\n");

        // ⑩ push the signed transaction
        PushedTransaction pushedTransaction = creat_wallet_2.pushTransaction("none", signedPackedTransaction);
        System.out.println("pushedTransaction=" + mapper.writeValueAsString(pushedTransaction));













//        SignedPackedTransaction signedPackedTransaction = creat_wallet_2.signTransaction(packedTransaction, //
//                Arrays.asList("EOS7XP7Ks7j68Uh64HGTEeiaMsgAgKcuZbYAAf86SoPLBpxcBX5it"), //
//                "038f4b0fc8ff18a4f0842a8f0564611f6e96e8535901dd45e43ac8691a1c4dca");
//
//        System.out.println("signedPackedTransaction=" + mapper.writeValueAsString(signedPackedTransaction));
//        System.out.println("\n--------------------------------\n");
//













    }
}

