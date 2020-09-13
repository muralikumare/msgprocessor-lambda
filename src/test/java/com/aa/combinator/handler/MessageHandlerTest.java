package com.aa.combinator.handler;

import com.aa.combinator.dao.ArrayCombinationsDAO;
import com.aa.combinator.exception.DBException;
import com.aa.combinator.exception.ErrorMessage;
import com.aa.combinator.exception.InvalidMessageFormat;
import com.aa.combinator.util.Combinations;
import com.aa.combinator.vo.DBInput;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by mural on 12/09/2020.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageHandler.class, Context.class, SQSEvent.class})
public class MessageHandlerTest {

    private MessageHandler sqsMsgHandler;

    @Mock
    Context context;

    @Mock
    SQSEvent sqsEvent;

    @Mock
    Combinations combinations;

    @Mock
    ArrayCombinationsDAO combinationsDAO;

    @AfterClass
    public static void tearDown() {
    }

    @Before
    public void setup() throws Exception{
        sqsMsgHandler = new MessageHandler();
        PowerMockito.whenNew(ArrayCombinationsDAO.class).withAnyArguments().thenReturn(combinationsDAO);
        PowerMockito.whenNew(Combinations.class).withAnyArguments().thenReturn(combinations);

    }

    @Test(expected = InvalidMessageFormat.class)
    public void test_InvalidMessage() {
        //pass in an empty msg list
        when(sqsEvent.getRecords()).thenReturn(new ArrayList<SQSEvent.SQSMessage>());
        sqsMsgHandler.handleRequest(sqsEvent, context);
    }

    @Test(expected = DBException.class)
    public void testMessageHandler_Exception() {
        when(sqsEvent.getRecords()).thenReturn(getInputMsgList());
        when(combinations.process(anyString())).thenReturn(getCombinations());
        //inject exception
        doThrow(new DBException(ErrorMessage.PERSIST_ERROR)).when(combinationsDAO).process(Mockito.any(DBInput.class));
        sqsMsgHandler.handleRequest(sqsEvent, context);
    }

    @Test
    public void test_validMessage() {
        when(sqsEvent.getRecords()).thenReturn(getInputMsgList());
        when(combinations.process(anyString())).thenReturn(getCombinations());
        doNothing().when(combinationsDAO).process(getDBInput());
        assertEquals( "success", sqsMsgHandler.handleRequest(sqsEvent, context));
    }

    private String getCombinations() {
        String response[] = {"A", "B", "C", "AB", "C", "CB", "AC", "ABC"};
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(response);
    }

    private List<SQSEvent.SQSMessage> getInputMsgList() {
        List<SQSEvent.SQSMessage> msgList = new ArrayList<>();
        SQSEvent.SQSMessage inputMsg = new SQSEvent.SQSMessage();
        inputMsg.setMessageId("2c48f5ed-7c30-40bf-acda-95a94aeb6a08");
        inputMsg.setBody("{\"input\":[\"A\",\"B\",\"C\",\"D\"]}");
        msgList.add(inputMsg);
        return msgList;
    }

    private DBInput getDBInput() {
        return new DBInput("2c48f5ed-7c30-40bf-acda-95a94aeb6a08", getCombinations());
    }
}

