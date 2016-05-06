package ua.netcracker.model.entity;

/**
 * Created by Alyona on 06.05.2016.
 */
public class AnswerString extends Answer<String>{

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValueDBFormat() {
        return getValue();
    }
}
