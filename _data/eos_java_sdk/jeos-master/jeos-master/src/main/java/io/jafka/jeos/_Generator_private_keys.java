package io.jafka.jeos;

public class _Generator_private_keys {
    String privateKey;
    String publicKey;

    public String Create_prviate_keys(){
        LocalApi api = EosApiFactory.createLocalApi();
         this.privateKey = api.createPrivateKey();


        return this.privateKey;
    }
    public String Create_Public_keys(){
        LocalApi api = EosApiFactory.createLocalApi();
        this.publicKey = api.toPublicKey(Create_prviate_keys());
      //  System.out.println(this.publicKey + " " + Create_prviate_keys());
        return this.publicKey;
    }

    public static void Create_private_key(){
        LocalApi api = EosApiFactory.createLocalApi();
        String privateKey = api.createPrivateKey();
        String publicKey = api.toPublicKey(privateKey);
        System.out.println(publicKey + " " + privateKey);

    }


}
