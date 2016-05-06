package ua.netcracker.hr_system.model.service.date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Created by Legion on 03.05.2016.
 */
@Service
@Scope("prototype")
public class DateService {

    public LocalDate getDate(String s) {
        String[] date = s.split(" ");
        LocalDate localDate = LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[2]));
//        calendar.set(Integer.valueOf(date[0]),Integer.valueOf(date[1])-1,Integer.valueOf(date[2]));
        return localDate;
    }

    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    public int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }

    public boolean isDateValid(String year, String month, String date) {

        boolean dateIsValid = true;
        try {
            LocalDate.of(Integer.parseInt(year),
                    Integer.parseInt(month),
                    Integer.parseInt(date));
        } catch (DateTimeException e) {
            dateIsValid = false;
        }
        return dateIsValid;
    }


}
