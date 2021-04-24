package de.techgamez.pleezon;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.IntStream;

public class RSAKeyPair {
    /**
     * @Author Pleezon
     */
    public RSAPrivateKey privateKey;
    public RSAPublicKey publicKey;

    /**
     *
     * @param exponentLimitation is to limit the exponent, saving processing power.
     */
    RSAKeyPair(int exponentLimitation) {
        genKeyPair(exponentLimitation);
    }
    RSAKeyPair() {
        genKeyPair(Long.MAX_VALUE);
    }

    private void genKeyPair(long limitation) {
        // cryptographically stronger than Random
        SecureRandom secureRandom = new SecureRandom();
        long p;
        while(!prime(p = Math.abs(secureRandom.nextInt(100000)+100))){} // keep generating random numbers until p is prime, limited because of max values.
        long q;
        while(!prime(q = Math.abs(secureRandom.nextInt(100000)+100)) | p==q){} // keep generating random numbers until q is prime, limited because of max values.
        long n = p*q;
        long phi = (p-1)*(q-1); // euler's phi function
        int e;
        while(!coprime((e=secureRandom.nextInt(Math.min(phi,limitation)>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)Math.min(phi,limitation))),phi)){} // keep generating random numbers smaller than phi (or the limitation) until phi and it are co-prime
        long[] exteuc = extendedEuclid(phi,e); // multiplicative inverse of e with respect to phi
        long d = exteuc[2];
        // stackoverflow way of dealing with negative d
        d = d % phi;
        if(d < 0)
            d += phi;
        this.privateKey = new RSAPrivateKey(d, n);
        this.publicKey = new RSAPublicKey(e, n);
    }


    /**
     * a > b: use T
     * a < b: use S
     */
    private long[] extendedEuclid(long a,long b){
        // recursive implementation of the extended euklid's algorithm. Pseudo-Code found on wikipedia.
        if(b==0)return new long[]{a,1,0};
        long[] dst1 = extendedEuclid(b,a%b);
        return new long[]{dst1[0],dst1[2],dst1[1]-(a/b)*dst1[2]};
    }

    private boolean prime(long n) {
        if (n == 1) { // 1 is never prime
            return false;
        } else if (n == 2) { // 2 is only even prime number
            return true;
        } else if((n & 1) == 0){
            return false;
        }
        //check every odd number num from 3 to sqrt(n) if n divisible by num. Multithreading to speed things up.
        return IntStream.rangeClosed(3, (int) Math.sqrt(n)).filter(num -> num % 2 != 0).parallel().noneMatch(num -> (n % num == 0));
    }
    private boolean coprime(long n1, long n2){
        // numbers are coprime when greatest common divisor = 1
        return BigInteger.valueOf(n1).gcd(BigInteger.valueOf(n2)).intValue() == 1;
    }

    public static class RSAPrivateKey{
        long d;
        long n;
        private RSAPrivateKey(long d, long n){
            this.d = d;
            this.n = n;
        }
    }
    public static class RSAPublicKey {
        long e;
        long n;
        private RSAPublicKey(long e, long n){
            this.e = e;
            this.n = n;
        }
    }



}
