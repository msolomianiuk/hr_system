package ua.netcracker.model.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import ua.netcracker.model.entity.Answer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JsonParsing {
    public static Collection<Answer> parseJsonString(String answersJsonString) {
        Collection<Answer> listAnswers = new ArrayList<>();
        JSONObject obj = new JSONObject(answersJsonString);
        Iterator<?> keys = obj.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (obj.get(key) instanceof JSONArray) { // if JSONArray - question have got several answers
                JSONArray array = (JSONArray) obj.get(key);
                for (int i = 0; i < array.length(); i++) {
                    Answer answer = new Answer();
                    answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
                    answer.setValue(array.getString(i));
                    listAnswers.add(answer);
                }
            } else {
                Answer answer = new Answer();
                answer.setQuestionId(Integer.valueOf(key.replace("question-", "")));
                answer.setValue((String) obj.get(key));
                listAnswers.add(answer);
            }
        }
        return listAnswers;
    }
}
