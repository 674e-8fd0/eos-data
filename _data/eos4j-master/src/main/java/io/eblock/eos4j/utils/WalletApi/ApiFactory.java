package io.eblock.eos4j.utils.WalletApi;

import java.util.ArrayList;
import java.util.List;

public class ApiFactory  {
    public static Eosapi create(String baseUrl) {
        return new EosApiRestClientImpl(baseUrl);
    }
}
