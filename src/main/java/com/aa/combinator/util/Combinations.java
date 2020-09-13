package com.aa.combinator.util;

import com.aa.combinator.exception.ErrorMessage;
import com.aa.combinator.exception.InvalidMessageFormat;
import com.aa.combinator.vo.Output;
import com.aa.combinator.vo.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.paukov.combinatorics3.Generator;

/**
 * Created by mural on 12/09/2020.
 * class that builds combination of array string using paukov combinatorics library
 */
public class Combinations {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String process(String sqsInput) {
        Output result = null;
        try {
            Request input = gson.fromJson(sqsInput, Request.class);
            result = new Output(getArrayCombinations(input.getInput()));
        } catch (JsonSyntaxException e) {
            throw new InvalidMessageFormat(ErrorMessage.INVALID_FORMAT);
        }
        return gson.toJson(result);
    }

    private String[] getArrayCombinations(String inputArr[]) {
        if (inputArr == null) {
            return null;
        }
        return Generator.subset(inputArr).simple().stream()
                .map(strings -> strings.stream().reduce("", String::concat))
                .toArray(String[]::new);
    }

}
