package io.jafka.jeos.core.common.transaction;


import java.util.List;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TransactionAction {

    private String account;

    private String name;

    private List<TransactionAuthorization> authorization;

    private String data;


    public TransactionAction(String eosio, String newaccount, List<TransactionAuthorization> authorizations, String binargs) {
      this.account=eosio;
      this.name=newaccount;
      this.authorization=authorizations;
      this.data=binargs;
    }
}
