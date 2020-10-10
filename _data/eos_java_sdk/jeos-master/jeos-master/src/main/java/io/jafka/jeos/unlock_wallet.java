package io.jafka.jeos;

public class unlock_wallet {
    public static String Base_Url="http://127.0.0.1:8889";
    public static String walletName="szmm";
    public static String walletPassword="PW5JjTfeZ3kJxtqx5wZHodwbv5SVCmC5pm2wXik6zkFCE6s4Hz6a7";
    public static void main(String[] args){
        EosApi unlock_wallet=EosApiFactory.create(Base_Url);
        unlock_wallet.unlockWallet(walletName,walletPassword);
    }
}
