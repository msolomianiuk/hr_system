package ua.netcracker.hr_system.model.service.date;

import ua.netcracker.hr_system.model.dao.daoImpl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.hr_system.model.service.serviceImpl.CourseSettingServiceImpl;
import ua.netcracker.hr_system.model.service.serviceImpl.InterviewDaysDetailsServiceImpl;

/**
 * Created by Legion on 04.05.2016.
 */
public class CalculateSetting {

    CourseSettingServiceImpl courseSettingService = null;
    MyDate myDate = new MyDate();
    InterviewDaysDetailsServiceImpl interviewDaysDetailsDAO = null;

    public int getDay (){
        courseSettingService.getIdLastSetting();
        String s = courseSettingService.getIdLastSetting().getCourseStartDate();
        String s1 = courseSettingService.getIdLastSetting().getInterviewEndDate();
        String s2 = courseSettingService.getIdLastSetting().getInterviewStartDate();

        String [] v1 = myDate.getSFTime(s).split(" ");
        String [] v2 = myDate.getSFTime(s1).split(" ");
        String [] v3 = myDate.getSFTime(s2).split(" ");

        String [] t1 = interviewDaysDetailsDAO.getStartTimeofInterview().split(":");
        String [] t2 = interviewDaysDetailsDAO.getEndTimeofInterview().split(":");

        int t = Integer.valueOf(t2[0]) - Integer.valueOf(t1[0]);
        int d = Integer.valueOf(v2[2]) - Integer.valueOf(v3[2]);

        int st = 600;

        int tr = 10;

        int per = t*60/10;

        int sInDay = 600/d;

        int df = sInDay/per;

        return 0;
    }

}
