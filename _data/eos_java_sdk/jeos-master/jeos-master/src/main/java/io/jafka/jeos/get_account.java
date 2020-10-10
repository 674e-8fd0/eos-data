package io.jafka.jeos;

import io.jafka.jeos.core.response.chain.account.Account;
import io.jafka.jeos.impl.EosApiServiceGenerator;
import io.jafka.jeos.impl.EosWalletApiService;

public class get_account {

    static void create(EosApi client) throws Exception {
        final String get_account_name= "eosio";
        Account account = client.getAccount(get_account_name);

    }

    public static void main(String[] args) throws Exception {

        EosApi client = EosApiFactory.create("http://127.0.0.1:8888");
        create(client);
    }


}
