package ua.netcracker.hr_system.model.service.date;

import org.springframework.beans.factory.annotation.Autowired;
import ua.netcracker.hr_system.model.dao.daoImpl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.hr_system.model.entity.CourseSetting;
import ua.netcracker.hr_system.model.service.serviceImpl.CourseSettingServiceImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
