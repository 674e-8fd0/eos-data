package io.eblock.eos4j.utils.WalletApi.action;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Actions {

    private List<Action> actions;

    private Long lastIrreversibleBlock;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Long getLastIrreversibleBlock() {
        return lastIrreversibleBlock;
    }

    @JsonProperty("last_irreversible_block")
    public void setLastIrreversibleBlock(Long lastIrreversibleBlock) {
        this.lastIrreversibleBlock = lastIrreversibleBlock;
    }
}
