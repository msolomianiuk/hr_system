package ua.netcracker.model.service.date;

import org.springframework.beans.factory.annotation.Autowired;
import ua.netcracker.model.dao.impl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.service.impl.CourseSettingServiceImpl;

import java.util.Calendar;

/**
* Created by Legion on 04.05.2016.
*/
public class CalculateSetting {

    @Autowired
    private CourseSettingServiceImpl courseSettingService;

    @Autowired
    private InterviewDaysDetailsDAOImpl interviewDaysDetailsDAO;

    @Autowired
    private MyDate myDate;

    public int getDay (){

        CourseSetting courseSetting = courseSettingService.getLastSetting();
        Calendar startInterviewDay = myDate.getDateInCalendar(courseSetting.getInterviewStartDate());
        Calendar endInterviewDay = myDate.getDateInCalendar(courseSetting.getInterviewEndDate());



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
