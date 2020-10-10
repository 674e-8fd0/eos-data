package io.jafka.jeos;

import io.jafka.jeos.util.ecc.Ecdsa;
//标记
import io.jafka.jeos.util.ecc.Ripemd160;
import io.jafka.jeos.util.ecc.Secp256k;
import io.jafka.jeos.util.utils.*;
import io.jafka.jeos.util.SHA;
import java.math.BigInteger;

public class createofflinsign {
    public static final Secp256k secp = new Secp256k();



    public static String signTransaction(String pk,String chain_id) throws Exception {
        // tx
        ByteBuffer bf = new ByteBuffer();
        ObjectUtils.writeBytes(chain_id, bf);//chain_id换成push
        byte[] real = bf.getBuffer();
        // append
        real = ByteUtils.concat(real, java.nio.ByteBuffer.allocate(33).array());

        // final byte [] b = real.clone();
        // int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
        // for(int i=1;i<=a.length;i++) {
        // System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
        // }
        return signHash(pk,real);
    }
    private static BigInteger privateKey(String pk) throws Exception {
        byte[] private_wif = Base58.decode(pk);
        byte version = (byte) 0x80;
        if (private_wif[0] != version) {
            throw new Exception("version_error");
        }
        byte[] private_key = ByteUtils.copy(private_wif, 0, private_wif.length - 4);
        byte[] new_checksum = SHA.sha256(private_key);
        new_checksum = SHA.sha256(new_checksum);
        new_checksum = ByteUtils.copy(new_checksum, 0, 4);
        byte[] last_private_key = ByteUtils.copy(private_key, 1, private_key.length - 1);
        BigInteger d = new BigInteger(Hex.bytesToHexString(last_private_key), 16);
        return d;
    }
    public static String signHash(String pk, byte[] b) throws Exception {
        String dataSha256 = Hex.bytesToHexString(SHA.sha256(b));
        BigInteger e = new BigInteger(dataSha256, 16);
        int nonce = 0;
        int i = 0;
        BigInteger d = privateKey(pk);
        io.jafka.jeos.util.ecc.Point Q = secp.G().multiply(d);

        nonce = 0;
        //标记
        Ecdsa ecd = new Ecdsa(secp);
        Ecdsa.SignBigInt sign;
        while (true) {
            sign = ecd.sign(dataSha256, d, nonce++);
            byte der[] = sign.getDer();
            byte lenR = der[3];
            byte lenS = der[5 + lenR];
            if (lenR == 32 && lenS == 32) {
                i = ecd.calcPubKeyRecoveryParam(e, sign, Q);
                i += 4; // compressed
                i += 27; // compact // 24 or 27 :( forcing odd-y 2nd key candidate)
                break;
            }
        }
        byte[] pub_buf = new byte[65];
        pub_buf[0] = (byte) i;
        ByteUtils.copy(sign.getR().toByteArray(), 0, pub_buf, 1, sign.getR().toByteArray().length);
        ByteUtils.copy(sign.getS().toByteArray(), 0, pub_buf, sign.getR().toByteArray().length + 1,
                sign.getS().toByteArray().length);

        byte[] checksum = Ripemd160.from(ByteUtils.concat(pub_buf, "K1".getBytes())).bytes();

        byte[] signatureString = ByteUtils.concat(pub_buf, ByteUtils.copy(checksum, 0, 4));
        System.out.println(Base58.encode(signatureString));
        return "SIG_K1_" + Base58.encode(signatureString);
    }
}
