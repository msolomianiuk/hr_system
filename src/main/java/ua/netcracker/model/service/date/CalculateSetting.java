package ua.netcracker.model.service.date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;
import ua.netcracker.model.service.impl.InterviewDaysDetailsServiceImpl;

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
    private InterviewDaysDetailsServiceImpl interviewService;

    @Autowired
    private DateService myDate;

    public int getDay() {

        CourseSetting courseSetting = courseSettingService.getLastSetting();
//
//        LocalDate startInterviewDay = myDate.getDate(courseSetting.getInterviewStartDate());
//        LocalDate endInterviewDay = myDate.getDate(courseSetting.getInterviewEndDate());
//        LocalDate startRegistrationDay = myDate.getDate(courseSetting.getRegistrationStartDate());
//        LocalDate endRegistrationDay = myDate.getDate(courseSetting.getRegistrationEndDate());

        int maxStudentForInterview = courseSetting.getStudentInterviewCount();
        int maxStudentForCourse = courseSetting.getStudentCourseCount();

        int timeForInterview = courseSetting.getInterviewTime();


        /**
         * Start interview hour - timeInterview [0]
         * End interview hour - timeInterview [3]
         *
         * Start interview min - timeInterview [2]
         * End interview min - timeInterview [4]
         */
//        int [] timeInterview = {Integer.parseInt(myDate.getTime(interviewService.getStartTimeofInterview())[0]),
//                Integer.parseInt(myDate.getTime(interviewService.getStartTimeofInterview())[1]),
//                Integer.parseInt(myDate.getTime(interviewService.getEndTimeofInterview())[0]),
//                Integer.parseInt(myDate.getTime(interviewService.getEndTimeofInterview())[1])};
//
//        int interviewTime=0;
//        if (timeInterview [4] < timeInterview [2]) {
//            interviewTime = timeInterview [2] + timeInterview [4];
//        } else {
//            interviewTime = (timeInterview [3] - timeInterview [0]) * 60
//                    + timeInterview [2] - timeInterview [4];
////        }
//        // кількість днів інтерв'ю -1
//        Period period = startInterviewDay.until(endInterviewDay);
//
//        int studentFromDay = maxStudentForInterview/(period.getDays()+1);

//        int

//        int st = 600;
//
//        int tr = 10;
//
//        int per = t*60/10;
//
//        int sInDay = 600/d;
//
//        int df = sInDay/per;

        return 0;
    }

}
