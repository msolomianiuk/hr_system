package ua.netcracker.model.service.date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Legion on 03.05.2016.
 */
@Service
@Scope("prototype")
public class MyDate {

    private Calendar calendar = new GregorianCalendar();

    public Calendar getDateInCalendar (String s){
        String [] date = s.split(" ");
        calendar.set(Integer.valueOf(date[0]),Integer.valueOf(date[1])-1,Integer.valueOf(date[2]));
        return calendar;
    }

    public long getCurrentTime(){
        return calendar.getTimeInMillis();
    }

    public int getCurrentYear (){
        calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

    public int getCurrentMonth(){
        return calendar.get(Calendar.MONTH);
    }

    public int getCurrentDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getSFTime (String timeM){
        SimpleDateFormat formating = new SimpleDateFormat("YYYY MM dd");
        Date date = new Date();
        date.setTime(Long.parseLong(timeM));
        return formating.format(date);
    }


}
