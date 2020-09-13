package com.aa.combinator.handler;

import com.aa.combinator.dao.ArrayCombinationsDAO;
import com.aa.combinator.exception.ErrorMessage;
import com.aa.combinator.exception.InvalidMessageFormat;
import com.aa.combinator.util.Combinations;
import com.aa.combinator.vo.DBInput;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by mural on 12/09/2020.
 * Message handler to handle input message from SQS, build combinations of array and persist
 */
public class MessageHandler implements RequestHandler<SQSEvent, String> {

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {

        String sqsMsg = null;
        String sqsMsgId = null;

        if (sqsEvent == null || sqsEvent.getRecords() == null || sqsEvent.getRecords().size() == 0) {
            throw new InvalidMessageFormat(ErrorMessage.INVALID_FORMAT);
        }

        sqsMsg = sqsEvent.getRecords().get(0).getBody();
        sqsMsgId = sqsEvent.getRecords().get(0).getMessageId();

        if (StringUtils.isBlank(sqsMsg)) {
            throw new InvalidMessageFormat(ErrorMessage.INVALID_FORMAT);
        }

        //get all combinations from array string
        String processedMessage = new Combinations().process(sqsMsg);

        //persist to dynamo
        new ArrayCombinationsDAO().process(new DBInput(sqsMsgId, processedMessage));

        return Constants.SUCCESS;
    }
}
