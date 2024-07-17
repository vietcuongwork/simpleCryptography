package com.vietcuong.simpleCryptography;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component

public class ServerRSAUtil {

    private PrivateKey privateKey;
    private PublicKey serverPublicKey;
    private PublicKey clientPublicKey;
    private static final String SERVER_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCekQxU8x6IuUXo3+wefWIUNFnYYht31WawSuZZYHyeiXwGRj/w/ZDKFKTgbb/XGH5o2FyJwne8eoWziqQpp4+lHmmLz7snxXX6vmV+lTemmNut/477CGYFuCGXnea/ZjjtUaSNbONEyUZPz00k0A16r4npY2yolddADM3u05+7hQIDAQAB";
    private static final String SERVER_PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ6RDFTzHoi5Rejf7B59YhQ0WdhiG3fVZrBK5llgfJ6JfAZGP/D9kMoUpOBtv9cYfmjYXInCd7x6hbOKpCmnj6UeaYvPuyfFdfq+ZX6VN6aY263/jvsIZgW4IZed5r9mOO1RpI1s40TJRk/PTSTQDXqvieljbKiV10AMze7Tn7uFAgMBAAECgYADdRw0ufxfdNK+aiuG2qsJz75pxnHc4g1UKL3vEviEqHDI3j1ErvK6RUr3Dc2SDhmrhZQ2+MONm4pQyOQdeGbcZPzIiVmULf0oXte2O/t8mD0KlDpPa5Iifmc402Dp+lvVPjMrnOqTJGSePJmYlQGJ+V1yQV/rWP9pBYwCGCjNYQJBALKmgED1iMwNNk4k9Hf396jjsLS53xL/Ii+WgemQh3wm/YcA/9OzLrFIlPWOYI4lz7j/Qz902KU07NK5IMQRjd0CQQDjOHiiRzpfGpheUp6XeftNjFrwBm//30N1ms8DYw2WQ3PRA5vqINkwk73bYzC2VA4MUncOnkn0FMvMy/Tnza3JAkBrNtdzpKdzZl536V+5+9vqxLLQ01fYC7vVeWr/5BgbXz753ZSq1TcpeBjFgavaw0B1kuQl2hcGlsul7yqF4M8VAkAZQey+9TX63mTzjzGL2wZhT2nIZp7KjZHdH9FFKhyXiHX2VPVe6DncWX/wRC1lByDso7oSSOwlkq443K8jmUxpAkEAjCQs3jxJMSjCtoNqEh9c2KFT+AZBwJvM68YIDIngGrLbtNJuIonVCvoxRxDbgoqC6OtlAo8NvYJ0AbYnu8Kk1g==";
    private static final String CLIENT_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoYcjnlq8KoxHQZUm1xfENVbd3cOtbiwp4ZfYKfVJyJGY6xaQqVkMhRVG2dscNLsZHaypN7SzyjJL7cHqcQkpHb5yZ+nQ5fsUWwVF8xGKhSAqQuMEtiP/toSsJ107w1FWGdbZnSKEaWwRh0vMl67Ckp4dz19d0FM+6lvBYoOziGwIDAQAB";

/*    public void init() {
        try {

            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");  // KeyPairGenerator instance for generating RSA key pairs
            generator.initialize(1024);  // Initialize the key pair generator with key size of 1024 bits
            KeyPair pair = generator.generateKeyPair();  // Generate the key pair
            privateKey = pair.getPrivate();  // Extract private and public keys from the generated key pair
            publicKey = pair.getPublic();
        } catch (Exception ignored) {
        }
    }*/

    public void initKeys() {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode(CLIENT_PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode(SERVER_PRIVATE_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            clientPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String encrypt(String message, PublicKey publicKey) throws Exception {  // Method to encrypt a message using the public key
        byte[] messageToByte = message.getBytes();  // Convert the message to bytes

/*        for(byte b : messageToByte){
            System.out.print(b + " ");
        }*/
        // Create a Cipher instance configured for RSA encryption
        // The transformation string "RSA/ECB/PKCS1Padding" specifies:
        // - RSA algorithm for encryption
        // - ECB (Electronic Codebook) mode of operation
        // - PKCS1Padding (Padding Scheme) to ensure the message length is compatible with RSA block size
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Initialize the cipher for encryption mode, providing the public key
        // This sets up the cipher to use the public key for the encryption process
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Perform the encryption operation on the byte array of the message
        // The doFinal method completes the encryption operation and returns the encrypted data as a byte array
        byte[] encryptedBytes = cipher.doFinal(messageToByte);

/*        System.out.println();
        for(byte b : encryptedBytes){
            System.out.print(b + " ");
        }
        System.out.println();*/

        // Encode the encrypted byte array into a Base64 string to make it easily readable and transferable
        // Base64 encoding is used here to convert binary data into a text format
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {  // Method to decrypt an encrypted message using the private key
        byte[] encryptedBytes = decode(encryptedMessage);   // Decode the Base64 encoded encrypted message to bytes
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // Cipher instance for RSA decryption with ECB mode and PKCS1Padding
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  // Initialize the cipher for decryption with the private key
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);  // Decrypt the encrypted bytes
        return new String(decryptedMessage, "UTF8");  // Convert the decrypted bytes to a string using UTF-8 encoding and return
    }

    private String encode(byte[] data) {  // Method to encode byte array to Base64 string
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {  // Method to decode a Base64 string to byte array
        return Base64.getDecoder().decode(data);
    }

    public void printKeys(PublicKey publicKey, PrivateKey privateKey) {
        System.out.println("Public key\n" + encode(publicKey.getEncoded()));
        System.out.println("Private key\n" + encode(privateKey.getEncoded()));
    }

    public PublicKey getClientPublicKey() {
        return clientPublicKey;
    }

}
