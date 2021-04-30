package de.techgamez.pleezon;

import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {
    /**
     * @Author Pleezon
     * toy RSA implimentation.
     * Cryptographically somewhat secure
     */
    public RSA() {

    }

    public BigInteger encrypt(RSAKeyPair.RSAPublicKey key, BigInteger message) {
        // message can't be larger than N because N is min 10.000 & message is value of encoding with max value < 10.000, no need to throw an exception.
        return message.modPow(key.e,key.n);
    }

    public BigInteger decrypt(RSAKeyPair.RSAPrivateKey key, BigInteger message) {
        return message.modPow(key.d,key.n);
    }




}

