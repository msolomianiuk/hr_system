package ua.netcracker.model.service.date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.impl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.time.LocalDate;

/**
* Created by Legion on 04.05.2016.
*/
@Service("caclulation setting")
public class CalculateSetting {

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    @Autowired
    private InterviewDaysDetailsDAOImpl interviewDaysDetailsDAO;

    @Autowired
    private DateService myDate;

    public int getDay (){

        CourseSetting courseSetting = courseSettingService.getLastSetting();

        LocalDate startInterviewDay = myDate.getDate(courseSetting.getInterviewStartDate());
        LocalDate endInterviewDay = myDate.getDate(courseSetting.getInterviewEndDate());
        LocalDate startRegistrationDay = myDate.getDate(courseSetting.getRegistrationStartDate());
        LocalDate endRegistrationDay = myDate.getDate(courseSetting.getRegistrationEndDate());

        int maxStudentForInterview = courseSetting.getStudentInterviewCount();
        int maxStudentForCourse = courseSetting.getStudentCourseCount();

        int timeForInterview = courseSetting.getInterviewTime();



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

