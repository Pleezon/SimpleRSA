package de.techgamez.pleezon;


import java.io.InputStream;
import java.util.ArrayList;

public class RSA {
    /**
     * @Author Pleezon
     * toy RSA implimentation.
     * Cryptographically somewhat secure
     */
    public RSA() {

    }

    private long encrypt(RSAKeyPair.RSAPublicKey key, int message) {
        // message can't be larger than N because N is min 10.000 & message is value of encoding with max value < 10.000, no need to throw an exception.
        return modpow(message,key.e,key.n);
    }

    private int decrypt(RSAKeyPair.RSAPrivateKey key, long message) {
        return (int) modpow(message,key.d,key.n);
    }

    public String encrypt(String in, RSAKeyPair.RSAPublicKey key){
        String format = "%0"+String.valueOf(key.n).length()+"d";
        StringBuilder end = new StringBuilder();
        for (byte b : in.getBytes()) {
            long enc_byte = encrypt(key,b+128);
            end.append(String.format(format, enc_byte));
        }
        return end.toString();
    }
    public String decrypt(String in, RSAKeyPair.RSAPrivateKey key){
        ArrayList<Byte> end = new ArrayList<>();
        int delimiter = String.valueOf(key.n).length();
        for (String s : splitStringEvery(in, delimiter)) {
            long enc_byte = Long.parseLong(s);
            byte dec_byte = (byte) (decrypt(key,enc_byte) -128);
            end.add(dec_byte);
        }
        byte[] res = new byte[end.size()];
        for(int i = 0; i < end.size(); i++) {
            res[i] = end.get(i);
        }
        return new String(res);
    }


    /**
     *
     * from Stackoverflow for performance
     */
    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];
        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);
        return result;
    }




    /**
     * better way of doing modpow than Math.pow(message,key.d) % key.n
     * I didn't invent this, converted it from C++ code, though.
     */
    private long modpow(long base, long exp, long modulus){
        base %= modulus;
        long result = 1;
        while(exp>0){
            if ((exp & 1)==1) result = (result * base) % modulus;
            base = (base * base) % modulus;
            exp >>= 1;

        }
        return result;
    }



}

