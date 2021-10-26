package com.crypto.electionCommission.AES;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.mapping.IdentifierBag;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class CBC {
    private AES aes;

    public CBC(){

    }

    public AES getAes() {
        return aes;
    }

    public void setAes(AES aes) {
        this.aes = aes;
    }

    public static String asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            String s= Integer.toHexString((int) chars[i]);
            if (s.length() == 1) {
                hex.append('0').append(s);
            } else {
                hex.append(s);
            }
        }
        return hex.toString();
    }

    public static String hexToAscii(String hexStr) {
        StringBuffer ascii = new StringBuffer();

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            ascii.append((char) Integer.parseInt(str, 16));
        }

        return ascii.toString();
    }



    private static Collection<String> splitStringBySize(String str, int size) {
        ArrayList<String> split = new ArrayList<>();
        for (int i = 0; i <= str.length() / size; i++) {
            split.add(str.substring(i * size, Math.min((i + 1) * size, str.length())));
        }
        return split;
    }

    private String getIvString() {
        String IVCHARS = "ABCDEF1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 32) { // length of the random string.
            int index = (int) (rnd.nextFloat() * IVCHARS.length());
            sb.append(IVCHARS.charAt(index));
        }
        String ivStr = sb.toString();
        return ivStr;

    }





    public JSONObject encrypt(String longString){
        String hexString = asciiToHex(longString);
        ArrayList<String> plainTexts = (ArrayList<String>) splitStringBySize(hexString, 32);

        String lastPlainText = plainTexts.get(plainTexts.size()-1);
        int lastPlainTextLen = lastPlainText.length();
        int paddingLastBlock;
        if(lastPlainTextLen== 0){
            plainTexts.remove(plainTexts.size()-1);
            paddingLastBlock = 0;
        }
        else {
            for (int i=lastPlainTextLen; i < 32; i++) {  lastPlainText = lastPlainText + "0";  }
            plainTexts.remove(plainTexts.size()-1);
            plainTexts.add(plainTexts.size(),lastPlainText);
            paddingLastBlock = 32 - lastPlainTextLen;
        }

        String iv = getIvString();
        JSONObject json = new JSONObject();
        json.put("iv", hexToAscii(iv));
        json.put("padding", paddingLastBlock);

        StringBuilder sb = new StringBuilder();

        for (String pt: plainTexts){
            String s = xorHex(pt, iv);
            String cipher = aes.encrypt(s);
            sb.append(cipher);
            iv = cipher;
        }

        json.put("cipher", hexToAscii(sb.toString()));

        return json;

    }

    public String decrypt(JSONObject jsonObject){
        String cipher = (String) jsonObject.get("cipher");
        String iv = asciiToHex((String) jsonObject.get("iv"));
        int padding = (int) jsonObject.get("padding");


        String hexString = asciiToHex(cipher);
        ArrayList<String> cipherTexts = (ArrayList<String>) splitStringBySize(hexString, 32);
        if (cipherTexts.get(cipherTexts.size()-1).length()==0){
            cipherTexts.remove(cipherTexts.size()-1);
        }
        StringBuilder sb = new StringBuilder();

        for (String ct: cipherTexts){

            String s = xorHex(iv, aes.decrypt(ct));
            sb.append(s);
            iv = ct;

        }
        String plaintext = sb.toString();
        logger.debug(plaintext);
        plaintext = hexToAscii(plaintext.substring(0, plaintext.length()-padding));

        return plaintext;


    }


    public String xorHex(String a, String b) {
        // TODO: Validation
        char[] chars = new char[a.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
        }
        return new String(chars);
    }

    private static int fromHex(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException();
    }

    private char toHex(int nybble) {
        if (nybble < 0 || nybble > 15) {
            throw new IllegalArgumentException();
        }
        return "0123456789ABCDEF".charAt(nybble);
    }

    private static final Logger logger = LogManager.getLogger(CBC.class);
}
