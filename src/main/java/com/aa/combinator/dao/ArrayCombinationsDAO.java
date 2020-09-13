package com.aa.combinator.dao;

import com.aa.combinator.exception.DBException;
import com.aa.combinator.vo.DBInput;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Created by mural on 12/09/2020.
 * store permutation & combination array messages to dynamo db
 */
public class ArrayCombinationsDAO {

    public void process(DBInput dbInput) {

        try {
            AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
            DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
            Table table = dynamoDB.getTable("arrayCombinations");
            Item item = new Item().withPrimaryKey("messageId", dbInput.getMessageId())
                    .withStringSet("combinations", dbInput.getCombinations());
            table.putItem(item);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer(" Database storage failure for messageId - ")
                    .append(dbInput.getMessageId());
            throw new DBException(stringBuffer.toString(), e);
        }
    }

}
