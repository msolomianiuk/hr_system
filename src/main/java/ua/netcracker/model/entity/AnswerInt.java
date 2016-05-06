package ua.netcracker.model.entity;

/**
 * Created by Alyona on 06.05.2016.
 */
public class AnswerInt extends Answer<Integer> {
    public void setValue(String value){
        super.setValue(Integer.parseInt(value));
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public String getValueDBFormat() {
        return getValue().toString();
    }
}
