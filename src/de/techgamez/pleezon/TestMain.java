package de.techgamez.pleezon;

public class TestMain {
    /*
    used for testing
     */
    public static void main(String[] args) {

        RSAKeyPair p = new RSAKeyPair();

        String enc = new RSA().encrypt("hello",p.publicKey);
        String dec = new RSA().decrypt(enc,p.privateKey);
        System.out.println(enc);
        System.out.println(dec);

    }


}
