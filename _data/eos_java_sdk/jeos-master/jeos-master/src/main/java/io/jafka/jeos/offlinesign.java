package eos.sample;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import io.jafka.jeos.EosApi;
import io.jafka.jeos.EosApiFactory;
import io.jafka.jeos.LocalApi;
import io.jafka.jeos.core.common.SignArg;
import io.jafka.jeos.core.common.transaction.PackedTransaction;
import io.jafka.jeos.core.common.transaction.SignedPackedTransaction;
import io.jafka.jeos.core.request.chain.transaction.PushTransactionRequest;
import io.jafka.jeos.core.response.chain.transaction.PushedTransaction;

/**
 *
 * @author adyliu (imxylz@gmail.com)
 * @since Sep 28, 2018
 */
public class offlinesign {


    public static void main(String[] args) throws Exception{

        // --- get the current state of blockchain
        EosApi eosApi = EosApiFactory.create("http://127.0.0.1:8888");
        SignArg arg = eosApi.getSignArg(1200);
        System.out.println(eosApi.getObjectMapper().writeValueAsString(arg));

        // --- sign the transation of token tansfer
        String privateKey = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";//replace the real private key
        String from = "shijiebangmm";
        String to = "womenshi1111";
        String quantity = "0.2345 EOS";
        String memo = "sent by eos sdk (https://github.com/adyliu/jeos";
        LocalApi localApi = EosApiFactory.createLocalApi();
        PushTransactionRequest req = localApi.transfer(arg, privateKey, from, to, quantity, memo);
        System.out.println(localApi.getObjectMapper().writeValueAsString(req));


        // --- push the signed-transaction to the b9lockchain
        PushedTransaction pts = eosApi.pushTransaction(req);
        System.out.println(localApi.getObjectMapper().writeValueAsString(pts));


    }

}