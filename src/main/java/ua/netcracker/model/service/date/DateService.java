package ua.netcracker.model.service.date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.InterviewDaysDetails;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Legion on 03.05.2016.
 */
@Service
@Scope("prototype")
public class DateService {

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    private CourseSetting courseSetting;

    private static final Logger LOGGER = Logger.getLogger(DateService.class);

    /**
     * Method returns setting
     * in current day
     *
     * @param interviewDaysDetails - current day
     * @return quantity personal and quantity of students pier day
     */
    private int[] getSettingDay(InterviewDaysDetails interviewDaysDetails) {

        courseSetting = courseSettingService.getLastSetting();

        int timeForInterview = courseSetting.getInterviewTime();

        // Start interview hour - timeInterview [0]
        // End interview hour - timeInterview [2]
        //
        // Start interview min - timeInterview [1]
        // End interview min - timeInterview [3]

        int interviewTime;
        int[] timeInterview = {
                Integer.parseInt(getTime(interviewDaysDetails.getStartTime())[0]),
                Integer.parseInt(getTime(interviewDaysDetails.getStartTime())[1]),
                Integer.parseInt(getTime(interviewDaysDetails.getEndTime())[0]),
                Integer.parseInt(getTime(interviewDaysDetails.getEndTime())[1])};
        if (timeInterview[0] == timeInterview[2] &&
                timeInterview[1] == timeInterview[3]) {
            interviewTime = 0;
        } else {
            if (timeInterview[0] == timeInterview[2]) {
                interviewTime = timeInterview[1] + timeInterview[3];
            } else {
                interviewTime=((timeInterview[2]-timeInterview[0])*60)+(timeInterview[1]+timeInterview[3]);
//                if (timeInterview[3] < timeInterview[1]) {
//                    interviewTime = timeInterview[1] - timeInterview[3] +
//                            (timeInterview[2] - timeInterview[0] - 1) * 60;
//                } else {
//                    interviewTime = (timeInterview[3] - timeInterview[0]) * 60
//                            + timeInterview[2] - timeInterview[4];
//                }
            }
        }

        int quantityPersonal;

        if (interviewTime == 0) {
            quantityPersonal = 0;
        } else {
            quantityPersonal = (int) Math.ceil(studentPerDay() / (interviewTime / timeForInterview));
        }

        int[] setting = {studentPerDay(), quantityPersonal};
        return setting;
    }

    private int studentPerDay() {
        courseSetting = courseSettingService.getLastSetting();

        int maxStudentForInterview = courseSetting.getStudentInterviewCount();

        return (int) Math.ceil(maxStudentForInterview / (getPeriodDate(courseSetting)));
    }

    public int getPeriod() {
        courseSetting = courseSettingService.getLastSetting();
        LocalDate startInterviewDay = getDate(courseSetting.getInterviewStartDate());
        LocalDate endInterviewDay = getDate(courseSetting.getInterviewEndDate());

        Period period = startInterviewDay.until(endInterviewDay);
        return period.getDays()+1;
    }

    public int getPeriodDate(CourseSetting courseSetting) {
        LocalDate startInterviewDay = getDate(courseSetting.getInterviewStartDate());
        LocalDate endInterviewDay = getDate(courseSetting.getInterviewEndDate());
        Period period = startInterviewDay.until(endInterviewDay);
        return period.getDays()+1;
    }

    public int getPersonal(InterviewDaysDetails interviewDaysDetails) {
        return getSettingDay(interviewDaysDetails)[1];
    }

    public int quantityStudent(InterviewDaysDetails interviewDaysDetails) {
        return studentPerDay();
    }

    public LocalDate getDate(String s) {
        LocalDate localDate = null;

        try {
            String[] date = s.split(" ");
            localDate = LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
            return localDate;
        } catch (Exception e) {
            LOGGER.error(e);
        }
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

    public String[] getTime(String time) {
        String[] timePars = time.split(":");
        return timePars;
    }

    public Map<String, String> mapDate() {

        courseSetting= courseSettingService.getLastSetting();

        HashMap<String, String> date = new HashMap<>();

        int r = getPeriod();

        String s = courseSetting.getInterviewStartDate();

        for (int i = 0; i < r; i++) {
            date.put(String.valueOf(getDate(s).plusDays(i)), String.valueOf(courseSetting.getId()));
        }
        return date;
    }

}