package com.vietcuong.simpleCryptography.ClientSide;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class clientRSAUtil {
    // Private and public keys for RSA encryption/decryption
    private PrivateKey privateKey;
    private PublicKey clientPublicKey;
    private PublicKey serverPublicKey;

    private static final String CLIENT_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoYcjnlq8KoxHQZUm1xfENVbd3cOtbiwp4ZfYKfVJyJGY6xaQqVkMhRVG2dscNLsZHaypN7SzyjJL7cHqcQkpHb5yZ+nQ5fsUWwVF8xGKhSAqQuMEtiP/toSsJ107w1FWGdbZnSKEaWwRh0vMl67Ckp4dz19d0FM+6lvBYoOziGwIDAQAB";
    private static final String CLIENT_PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKhhyOeWrwqjEdBlSbXF8Q1Vt3dw61uLCnhl9gp9UnIkZjrFpCpWQyFFUbZ2xw0uxkdrKk3tLPKMkvtwepxCSkdvnJn6dDl+xRbBUXzEYqFICpC4wS2I/+2hKwnXTvDUVYZ1tmdIoRpbBGHS8yXrsKSnh3PX13QUz7qW8Fig7OIbAgMBAAECgYAB9e3kzem9Jf/sGlqOy8CPQ8Q4rqTNfTQLXyiwjDcmu5W2AQ+TyqBxLkgXknV3cpZ/7MI5vgIVKNIv3qwuVkjYgwjle5maDfqKQUNH8vczbP3Ujns1I7UFJwaB6yaEeuEVByCRtXQ0xqHRhbC4ll+CIJyJQQ0/1YwMoF3mJY+0qQJBAO9gB79mEdA3lppahfOyQ2t2i6cuk+o4uaISBWt4zJVdTYHyVoe6omgICIJfF/2S3Ppe+RncAhfOcbgPgnPVFnUCQQC0E4gsTAbdoiZdXStEqJIMV3ubUG1l5iIeyT8RxoyvsLK01XFPBfxw0b50T2JasYBGFxxkJx/Y9M3Zcka3qKRPAkBWbfb+La1638dZ/htljVT4hToRaCjyJf8ovJcGYfyEZcbph4WkxkeDhnsED6/K1+8/23DX3G9JBzpaOgdhKWxNAkB6XFtHXlG8ABZ5knl4KeMzp65CFyB/ASVmLfFBLt0h9Ls5zV81ByhFsP/tIJQAdGRpPAL6uusVDSNjAhezbFBZAkEAihFjbL6XqtHDz1Mj7HghXqjnsHvWCmpRf487MQ0CQiai/iz6KZRSwrs62D/h2xZESYzCl6+tmqk3kt4XwWHVhA==";
    private static final String SERVER_PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCekQxU8x6IuUXo3+wefWIUNFnYYht31WawSuZZYHyeiXwGRj/w/ZDKFKTgbb/XGH5o2FyJwne8eoWziqQpp4+lHmmLz7snxXX6vmV+lTemmNut/477CGYFuCGXnea/ZjjtUaSNbONEyUZPz00k0A16r4npY2yolddADM3u05+7hQIDAQAB";


/*    public void init() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");  // KeyPairGenerator instance for generating RSA key pairs
            generator.initialize(1024);  // Initialize the key pair generator with key size of 1024 bits
            KeyPair pair = generator.generateKeyPair();  // Generate the key pair
            privateKey = pair.getPrivate();  // Extract private and public keys from the generated key pair
            publicKey = pair.getPublic();
            printKeys();
        } catch (Exception ignored) {
        }
    }*/

    public void initFromStrings() {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode(SERVER_PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode(CLIENT_PRIVATE_KEY_STRING));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            serverPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (Exception e) {
            System.err.println("Error initializing keys: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }

    public void printKeys(PublicKey publicKey, PrivateKey privateKey) {
        System.out.println("Public key\n" + encode(publicKey.getEncoded()));
        System.out.println("Private key\n" + encode(privateKey.getEncoded()));
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

    private String encode(byte[] data) {  // Method to encode byte array to Base64 string
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {  // Method to decode a Base64 string to byte array
        return Base64.getDecoder().decode(data);
    }

    public String decrypt(String encryptedMessage) throws Exception {  // Method to decrypt an encrypted message using the private key
        byte[] encryptedBytes = decode(encryptedMessage);   // Decode the Base64 encoded encrypted message to bytes
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // Cipher instance for RSA decryption with ECB mode and PKCS1Padding
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  // Initialize the cipher for decryption with the private key
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);  // Decrypt the encrypted bytes
        return new String(decryptedMessage, "UTF8");  // Convert the decrypted bytes to a string using UTF-8 encoding and return
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }
}
