package save_hash_to_chain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eblock.eos4j.Ecc;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.service.RpcService;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.api.vo.Block;
import io.eblock.eos4j.api.vo.ChainInfo;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import io.eblock.eos4j.api.vo.transaction.push.Tx;
import io.eblock.eos4j.api.vo.transaction.push.TxAction;
import io.eblock.eos4j.api.vo.transaction.push.TxRequest;
import io.eblock.eos4j.api.vo.transaction.push.TxSign;
import io.eblock.eos4j.ese.Action;
import io.eblock.eos4j.ese.DataParam;
import io.eblock.eos4j.ese.DataType;
import io.eblock.eos4j.utils.WalletApi.ApiFactory;
import io.eblock.eos4j.utils.WalletApi.Eosapi;
import io.eblock.eos4j.utils.WalletApi.action.AbiJsonToBin;

import java.text.SimpleDateFormat;
import java.util.*;

public class save_text_to_hash {
    private static String API_URL_2= new String("http://182.92.171.204:8879");


    public static void main(String[] args) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Eosapi eosapi= ApiFactory.create(API_URL_2);
        ChainInfo info = eosapi.getChainInfo();
        System.out.println(info);
        Block block = eosapi.getBlock(info.getLastIrreversibleBlockNum().toString());
        //AbiJsonToBin Bin=eosapi.abiJsonToBin("bob","hi",dataMap);
       // System.out.println(Bin);
      // tx
        Tx tx = new Tx();
        tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
        tx.setRef_block_num(info.getLastIrreversibleBlockNum());
        tx.setRef_block_prefix(block.getRefBlockPrefix());
        tx.setNet_usage_words(0l);
        tx.setMax_cpu_usage_ms(0l);
        tx.setDelay_sec(0l);

        List<TxAction> actions = new ArrayList<>();
        // data
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("name","idea");

        TxAction action = new TxAction("bob", "bob", "hi", dataMap); //交易账户，合约账户，交易的动作名称，交易数据
        actions.add(action);
        tx.setActions(actions);
        // sgin
        String pk="5JRUXvBt8PJVupQxMUHHYFiuEKzxV8PjY8UAGoX2AtiwyWGSbuD";
        String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
        System.out.println(sign);

        Rpc rpc=new Rpc(API_URL_2);
        String  data="0000000000000e3d";
        action.setData(data);
        // reset expiration
        tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
        Transaction t1 = null;
        try {
            t1 = rpc.pushTransaction("none", tx, new String[] { sign });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(t1);


        System.out.println("\n"+block.getTimestamp()+"\n"+block.getRefBlockPrefix()+"\n"+info.getChainId());

    }



}
