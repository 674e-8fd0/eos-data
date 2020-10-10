package io.jafka.jeos.core.common;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * some args for signature
 * 
 * @author adyliu (imxylz@gmail.com)
 * @since 2018年9月9日
 */
@Data
public class SignArg {

    Long headBlockNum;
    Long lastIrreversibleBlockNum;
    Long refBlockPrefix;
    LocalDateTime headBlockTime;
    String chainId;
    //
    int expiredSecond;

    public Long getHeadBlockNum() {
        return headBlockNum;
    }

    public void setHeadBlockNum(Long headBlockNum) {
        this.headBlockNum = headBlockNum;
    }

    public Long getLastIrreversibleBlockNum() {
        return lastIrreversibleBlockNum;
    }

    public void setLastIrreversibleBlockNum(Long lastIrreversibleBlockNum) {
        this.lastIrreversibleBlockNum = lastIrreversibleBlockNum;
    }

    public Long getRefBlockPrefix() {
        return refBlockPrefix;
    }

    public void setRefBlockPrefix(Long refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    public LocalDateTime getHeadBlockTime() {
        return headBlockTime;
    }

    public void setHeadBlockTime(LocalDateTime headBlockTime) {
        this.headBlockTime = headBlockTime;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public int getExpiredSecond() {
        return expiredSecond;
    }

    public void setExpiredSecond(int expiredSecond) {
        this.expiredSecond = expiredSecond;
    }
}
