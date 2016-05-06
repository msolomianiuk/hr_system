package ua.netcracker.model.entity;

import java.util.ArrayList;

/**
 * Created by Admin on 06.05.2016.
 */
public class AnswerList extends Answer<ArrayList>{
    public void addValue(String value){
        super.getValue().add(value);
    }
}
