package ua.netcracker.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Admin on 06.05.2016.
 */
public class AnswerList extends Answer<ArrayList<String>>{

    public void setValue(String value){
        super.setValue(parseValue(value));
    }

    private ArrayList<String> parseValue(String value){
        Pattern pattern = Pattern.compile("<%>");
        String[] answers = pattern.split(value);
        return new ArrayList<String>(Arrays.asList(answers));
    }

    @Override
    public String toString() {
        String value = "";
        ArrayList<String> answers = getValue();
        for (String answer : answers){
            value += answer + "/n";
        }
        return value;
    }

    @Override
    public String getValueDBFormat() {
        String value = "";
        ArrayList<String> answers = getValue();
        for (String answer : answers){
            value += answer + "<%>";
        }
        return value;
    }
}
