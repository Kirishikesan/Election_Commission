package com.crypto.electionCommission.AES;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class Test {

    public static void main(String[] args) {
        AES aes = new AES("746875766172616b616e746875766172746875766172616b616e746875766172");
        CBC cbc = new CBC();
        cbc.setAes(aes);
        JSONObject jsonObject = cbc.encrypt("{\"name\":\"John\", \"age\":30, \"car\":null}");
        String plaintext = cbc.decrypt(jsonObject);
        logger.info(plaintext);


    }
    private static final Logger logger = LogManager.getLogger(Test.class);
}
