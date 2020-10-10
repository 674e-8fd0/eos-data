package io.jafka.jeos.util.utils;

import io.jafka.jeos.EosApi;
import io.jafka.jeos.EosApiFactory;
import io.jafka.jeos.core.response.chain.Block;
import io.jafka.jeos.core.response.chain.ChainInfo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class offline_create {
         EosApi rpc = EosApiFactory.create("http://127.0.0.1:8888");
    public SignParam getOfflineSignParams(Long exp) {
        SignParam params = new SignParam();
        ChainInfo info = rpc.getChainInfo();
         Long block = new Block2().getBlockNum(info.getLastIrreversibleBlockNum().toString());
        params.setChainId(info.getChainId());
        Date s1=Localdatetodate(info.getHeadBlockTime().toLocalDate());
        params.setHeadBlockTime(s1);
        params.setLastIrreversibleBlockNum(info.getLastIrreversibleBlockNum());
        params.setRefBlockPrefix(block);
        params.setExp(exp);
        return params;
    }

   public Date Localdatetodate(LocalDate s){
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDate localDate = s;

    ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

    Date date = Date.from(zdt.toInstant());

        System.out.println("LocalDate = " + localDate);
        System.out.println("Date = " + date);
          return  date;
   }

}
