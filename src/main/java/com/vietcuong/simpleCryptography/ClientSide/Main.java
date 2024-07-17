package com.vietcuong.simpleCryptography.ClientSide;

import com.google.gson.Gson;
import com.vietcuong.simpleCryptography.entiity.Message;
import okhttp3.*;

public class Main {
    public static void main(String[] args) {
        clientRSAUtil rsa = new clientRSAUtil();
        rsa.initFromStrings();
/*        try {
            String encryptedMessage = rsa.encrypt("test");
            System.out.println();
            System.out.println(rsa.decrypt(encryptedMessage));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }*/

        String requestMessage = "345678901234";
        try {
            String encryptedMessage = rsa.encrypt(requestMessage, rsa.getServerPublicKey());
            Message message = new Message(encryptedMessage);

            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            String json = gson.toJson(message);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder().url("http://localhost:8080/getCitizenName")
                    .post(body).build();

            Response response = client.newCall(request).execute();
            String encryptedResponse = response.body().string();
            String decrypredResponse = rsa.decrypt(encryptedResponse);
            // Print the response body if successful
            if (response.isSuccessful()) {
                System.out.println(encryptedResponse);
                System.out.println(decrypredResponse);
            } else {
                System.err.println("Request failed: " + response.message());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

