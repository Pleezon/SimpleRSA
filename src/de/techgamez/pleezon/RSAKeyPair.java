package de.techgamez.pleezon;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RSAKeyPair {
    /**
     * @Author Pleezon
     */
    public RSAPrivateKey privateKey;
    public RSAPublicKey publicKey;

    RSAKeyPair() {
        genKeyPair();
    }

    private void genKeyPair() {
        SecureRandom r = new SecureRandom();
        BigInteger p;
        while(!((p = new BigInteger(String.valueOf(Math.abs(r.nextLong())))).isProbablePrime(1))){}
        BigInteger q;
        while(!((q = new BigInteger(String.valueOf(Math.abs(r.nextLong())))).isProbablePrime(1)) || p.compareTo(q)==0){}
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;
        while((e=new BigInteger(String.valueOf(Math.abs(r.nextLong())+1))).compareTo(phi)>=0 || e.gcd(phi).compareTo(BigInteger.ONE)!=0){};
        BigInteger d = e.modInverse(phi);
        this.privateKey = new RSAPrivateKey(d,n);
        this.publicKey = new RSAPublicKey(e,n);
    }

    public static class RSAPrivateKey{
        BigInteger d;
        BigInteger n;
        private RSAPrivateKey(BigInteger d, BigInteger n){
            this.d = d;
            this.n = n;
        }
    }
    public static class RSAPublicKey {
        BigInteger e;
        BigInteger n;
        private RSAPublicKey(BigInteger e, BigInteger n){
            this.e = e;
            this.n = n;
        }
    }

    public boolean returnPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }


}
