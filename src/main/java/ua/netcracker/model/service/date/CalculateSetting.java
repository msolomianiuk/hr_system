package ua.netcracker.model.service.date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.InterviewDaysDetails;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by Legion on 04.05.2016.
 */
@Service("caclulation setting")
public class CalculateSetting {

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    @Autowired
    private DateService myDate;

    private CourseSetting courseSetting;

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
                Integer.parseInt(myDate.getTime(interviewDaysDetails.getStartTime())[0]),
                Integer.parseInt(myDate.getTime(interviewDaysDetails.getStartTime())[1]),
                Integer.parseInt(myDate.getTime(interviewDaysDetails.getEndTime())[2]),
                Integer.parseInt(myDate.getTime(interviewDaysDetails.getEndTime())[3])};

        if (timeInterview[0] == timeInterview[2] &&
                timeInterview[0] == timeInterview[2]) {
            interviewTime = 0;
        } else {
            if (timeInterview[0] == timeInterview[2]) {
                interviewTime = timeInterview[1] + timeInterview[3];
            } else {
                if (timeInterview[3] < timeInterview[1]) {
                    interviewTime = timeInterview[1] - timeInterview[3] +
                            (timeInterview[2] - timeInterview[0] - 1) * 60;
                } else {
                    interviewTime = (timeInterview[3] - timeInterview[0]) * 60
                            + timeInterview[2] - timeInterview[4];
                }
            }
        }

        int quantityPersonal;

        if (interviewTime == 0) {
            quantityPersonal = 0;
        } else {
        // кількість персоналу на співбесіду
            quantityPersonal = (int) Math.ceil(getStudent() / (interviewTime / timeForInterview));
        }

        int[] setting = {getStudent(), quantityPersonal};
        return setting;
    }

    private int getStudent() {
        courseSetting = courseSettingService.getLastSetting();

        LocalDate startInterviewDay = myDate.getDate(courseSetting.getInterviewStartDate());
        LocalDate endInterviewDay = myDate.getDate(courseSetting.getInterviewEndDate());

        int maxStudentForInterview = courseSetting.getStudentInterviewCount();

        // кількість днів інтерв'ю -1
        Period period = startInterviewDay.until(endInterviewDay);

        // кількість студентів на день
        return (int) Math.ceil(maxStudentForInterview / (period.getDays() + 1));
    }

    public int getPersonal(InterviewDaysDetails interviewDaysDetails) {
        return getSettingDay(interviewDaysDetails)[1];
    }

    public int quantityStudent(InterviewDaysDetails interviewDaysDetails) {
        return getStudent();
    }

}
