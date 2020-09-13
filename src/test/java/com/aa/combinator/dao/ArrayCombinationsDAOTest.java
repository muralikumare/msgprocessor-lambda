package com.aa.combinator.dao;

import com.aa.combinator.exception.DBException;
import com.aa.combinator.vo.DBInput;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by mural on 12/09/2020.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AmazonDynamoDBClientBuilder.class})
public class ArrayCombinationsDAOTest {

    private ArrayCombinationsDAO arrayCombinationsDAO = null;

    @Mock
    private AmazonDynamoDB amazonDynamoDB;

    @Mock
    private Table dynamoTable;

    @Mock
    private PutItemResult dynamoItem;

    @Mock
    private DynamoDB dynamoDB;

    @AfterClass
    public static void tearDown() {
    }

    @Before
    public void setup() {
        arrayCombinationsDAO = new ArrayCombinationsDAO();
        PowerMockito.mockStatic(AmazonDynamoDBClientBuilder.class);
    }

    @Test(expected = DBException.class)
    public void testPersist_Exception() throws Exception {
        when(AmazonDynamoDBClientBuilder.defaultClient()).thenReturn(amazonDynamoDB);
        PowerMockito.whenNew(DynamoDB.class).withArguments(amazonDynamoDB).thenReturn(dynamoDB);
        when(dynamoDB.getTable(anyString())).thenReturn(dynamoTable);
        when(amazonDynamoDB.putItem(any(PutItemRequest.class))).thenThrow(new InternalServerErrorException("ServerError"));
        arrayCombinationsDAO.process(new DBInput("2c48f5ed-7c30-40bf-acda-95a94aeb6a08", getCombinations()));
    }

    @Test
    public void testPersist_validInput() throws Exception {
        PowerMockito.whenNew(DynamoDB.class).withArguments(amazonDynamoDB).thenReturn(dynamoDB);
        when(AmazonDynamoDBClientBuilder.defaultClient()).thenReturn(amazonDynamoDB);
        when(amazonDynamoDB.putItem(any(PutItemRequest.class))).thenReturn(dynamoItem);
        when(dynamoDB.getTable(anyString())).thenReturn(dynamoTable);
        arrayCombinationsDAO.process(new DBInput("2c48f5ed-7c30-40bf-acda-95a94aeb6a08", getCombinations()));
    }

    private String getCombinations() {
        String response[] = {"A", "B", "C", "AB", "C", "CB", "AC", "ABC"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }
}