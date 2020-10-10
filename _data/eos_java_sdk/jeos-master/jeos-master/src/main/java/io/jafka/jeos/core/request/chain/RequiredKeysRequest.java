package io.jafka.jeos.core.request.chain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
//在项目中主要使用Jackson进行JSON和Java对象转换，下

import io.jafka.jeos.core.common.transaction.PackedTransaction;

public class RequiredKeysRequest {

    private PackedTransaction transaction;

    private List<String> availableKeys;

    public RequiredKeysRequest(PackedTransaction transaction, List<String> availableKeys) {
        this.transaction = transaction;
        this.availableKeys = availableKeys;
    }

    public PackedTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(PackedTransaction transaction) {
        this.transaction = transaction;
    }

    @JsonProperty("available_keys")
    public List<String> getAvailableKeys() {
        return availableKeys;
    }

    public void setAvailableKeys(List<String> availableKeys) {
        this.availableKeys = availableKeys;
    }
}
