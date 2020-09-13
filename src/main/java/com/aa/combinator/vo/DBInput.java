package com.aa.combinator.vo;

/**
 * Created by mural on 12/09/2020.
 */
public class DBInput {

    private String messageId;
    private String combinations;

    public DBInput(String messageId, String combinations) {
        this.messageId = messageId;
        this.combinations = combinations;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCombinations() {
        return combinations;
    }
}
