package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.InterviewDaysDetailsDAO;
import ua.netcracker.model.dao.impl.InterviewDaysDetailsDAOImpl;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.CourseSetting;
import ua.netcracker.model.entity.InterviewDaysDetails;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.service.CourseSettingService;
import ua.netcracker.model.service.InterviewDaysDetailsService;
import ua.netcracker.model.service.ReportService;
import ua.netcracker.model.service.date.DateService;
import ua.netcracker.model.utils.JdbcTemplateFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by MaXim on 05.05.2016.
 */
@Service("Interview Service")
public class InterviewDaysDetailsServiceImpl implements InterviewDaysDetailsService {
    static final Logger LOGGER = Logger.getLogger(InterviewDaysDetailsServiceImpl.class);

//    private static final String INTERVIEW_DETAILS_ADDRESS_SQL =
//            "SELECT hr_system.interview_days_details.id, date, start_time, end_time, hr_system.address.address, hr_system.address.room_capacity, count_students, count_personal" +
//                    " FROM hr_system.interview_days_details" +
//                    " LEFT JOIN hr_system.address" +
//                    " ON hr_system.interview_days_details.address_id=hr_system.address.id" +
//                    " WHERE course_id = ? " +
//                    " ORDER BY hr_system.interview_days_details.date ";
//    private static final String INTERVIEW_DETAILS_ADDRESS_BY_ID_SQL =
//            "SELECT hr_system.interview_days_details.id, date, start_time, end_time, hr_system.address.address, hr_system.address.room_capacity, count_students, count_personal" +
//                    " FROM hr_system.interview_days_details" +
//                    " LEFT JOIN hr_system.address" +
//                    " ON hr_system.interview_days_details.address_id=hr_system.address.id" +
//                    " WHERE hr_system.interview_days_details.id = ? " +
//                    " ORDER BY hr_system.interview_days_details.date ";

    private static final String REMOVE_SQL_BY_COURSE_ID = "DELETE FROM \"hr_system\".interview_days_details WHERE course_id = ?";

    @Autowired
    private InterviewDaysDetailsDAO interviewDaysDetailsDAO;

    @Autowired
    private JdbcTemplateFactory jdbcTemplateFactory;

    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DateService dateService;

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private CandidateServiceImpl candidateService;

    Status status;

    @Autowired
    CourseSettingService courseSettingService;

    public InterviewDaysDetails setInterviewDateDetails(String id, String startTime, String endTime, int addressId) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
        interviewDaysDetails.setId(Integer.parseInt(id));
        interviewDaysDetails.setStartTime(startTime);
        interviewDaysDetails.setEndTime(endTime);
        interviewDaysDetails.setAddressId(addressId);
        interviewDaysDetails.setCountStudents(dateService.quantityStudent(interviewDaysDetails));
        interviewDaysDetails.setCountPersonal(dateService.getPersonal(interviewDaysDetails));
        return interviewDaysDetails;
    }

    @Override
    public boolean add(InterviewDaysDetails interviewDaysDetails) {
        return interviewDaysDetailsDAO.insert(interviewDaysDetails);
    }

    @Override
    public InterviewDaysDetails findById(int id) {
        return interviewDaysDetailsDAO.find(id);
    }

    @Override
    public List<InterviewDaysDetails> findAll() {
        return (List<InterviewDaysDetails>) interviewDaysDetailsDAO.findAll();
    }

    @Override
    public void update(InterviewDaysDetails interviewDaysDetails) {
        interviewDaysDetailsDAO.update(interviewDaysDetails);
    }

    @Override
    public void remove(int id) {
        interviewDaysDetailsDAO.remove(id);
    }

    @Override
    public void removeByCourseId(int course_id) {
        jdbcTemplateFactory.getJdbcTemplate(dataSource).update(REMOVE_SQL_BY_COURSE_ID, course_id);
    }

    @Override
    public void addDate(InterviewDaysDetails interviewDaysDetails) {
        interviewDaysDetailsDAO.insertDate(interviewDaysDetails);
    }

    public void addDateList(CourseSetting courseSetting) {
        InterviewDaysDetails interviewDaysDetails = new InterviewDaysDetails();
        int period = dateService.getPeriodDate(courseSetting);
        String currDate = courseSetting.getInterviewStartDate();
        interviewDaysDetails.setCourseId(courseSetting.getId());
        interviewDaysDetails.setInterviewDate(currDate);
        for (int i = 0; i < period; i++) {
            interviewDaysDetails.setInterviewDate(String.valueOf(dateService.getDate(currDate).plusDays(i)));
            addDate(interviewDaysDetails);
        }
    }

    @Override
    public String sortCandidateToDays(CourseSetting courseSetting) {
        if (isFiled()) {
            int countCandidateWithStatusInterviewDate = candidateService.getCandidateByStatus(status.Interview_dated.getStatus()).size();
            CourseSetting lastCourseSetting = courseSettingService.getLastSetting();
            int countFree = lastCourseSetting.getStudentInterviewCount() - countCandidateWithStatusInterviewDate;
            List<Candidate> candidateList = (ArrayList<Candidate>) candidateService.getCandidateByStatus(status.Interview.getStatus());
            int countMaxCandidatePerDay = dateService.studentPerDay();
            LocalDate dayOfInterview = LocalDate.parse(lastCourseSetting.getInterviewStartDate());
            int index = 0;
            int countDays;
            int remainder;
            if (countFree >= candidateList.size()) {
                countDays = countCandidateWithStatusInterviewDate / countMaxCandidatePerDay;
                remainder = countCandidateWithStatusInterviewDate % countMaxCandidatePerDay;
                if (remainder == 0) {
                    if (countDays != 0)
                        dayOfInterview = dayOfInterview.plusDays(countDays + 1);
                } else {
                    dayOfInterview = dayOfInterview.plusDays(countDays);
                }
                InterviewDaysDetails interviewDaysDetails = findByDate(String.valueOf(dayOfInterview));
                int countCandidatesPerDay = candidateService.getCandidateCountByInterviewId(interviewDaysDetails.getId());
                while (index < candidateList.size()) {
                    if ((countMaxCandidatePerDay - countCandidatesPerDay) == 0) {
                        dayOfInterview = dayOfInterview.plusDays(1);
                        interviewDaysDetails = findByDate(String.valueOf(dayOfInterview));
                        countCandidatesPerDay = candidateService.getCandidateCountByInterviewId(interviewDaysDetails.getId());
                    }
                    Candidate candidate = new Candidate();
                    candidate.setId(candidateList.get(index).getId());
                    candidate.setStatusId(status.Interview_dated.getId());
                    candidate.setInterviewDaysDetailsId(interviewDaysDetails.getId());
                    candidateService.updateCandidate(candidate);
                    index++;
                    countCandidatesPerDay++;
                }
                return "Success";

            } else {
                return "Limit Exceeded candidates! " + "You have " + countFree + " free places, but you want add "
                        + candidateList.size() + " candidates";
            }
        } else {
            return "Please filled all date settings";
        }

    }

    private boolean isFiled() {
        List<Map<String, Object>> listInterviewDate = findAllInterviewDetailsAddress();
        boolean isFilled = true;
        String r;
        for (Map<String, Object> row :
                listInterviewDate) {
            if (row.get("start_time") == null | row.get("end_time") == null) isFilled = false;
        }
        return isFilled;
    }

    public boolean timeIsFree(InterviewDaysDetails interviewDaysDetails) {
        interviewDaysDetails.setInterviewDate(findById(interviewDaysDetails.getId()).getInterviewDate());
        List<Map<String, Object>> listInterviewDate = findAllInterviewDetailsAddress();
        LocalTime startInterview = LocalTime.parse(interviewDaysDetails.getStartTime());
        LocalTime endInterview = LocalTime.parse(interviewDaysDetails.getEndTime());
        LocalTime compareStartTime;
        LocalTime compareEndTime;
        boolean isFree = true;
        for (Map<String, Object> row :
                listInterviewDate) {
            if (row.get("date").equals(interviewDaysDetails.getInterviewDate()))
                if (row.get("address") != null)
                    if (row.get("address").equals(addressService.findById(interviewDaysDetails.getAddressId()).getAddress())) {
                        compareStartTime = LocalTime.parse(row.get("start_time").toString());
                        compareEndTime = LocalTime.parse(row.get("end_time").toString());
                        if ((startInterview.isAfter(compareStartTime) & startInterview.isBefore(compareEndTime)) |
                                (endInterview.isAfter(compareStartTime) & endInterview.isBefore(compareEndTime)) |
                                (startInterview.isBefore(compareStartTime) & endInterview.isAfter(compareEndTime))) {
                            isFree = false;
                        }
                    }
        }
        return isFree;
    }

    @Override
    public InterviewDaysDetails findByDate(String date) {
        return interviewDaysDetailsDAO.findByDate(date);
    }

    public List<Map<String, Object>> findAllInterviewDetailsAddress() {
        return interviewDaysDetailsDAO.findAllInterviewDetailsAddress();
    }

    public Map<String, Object> findInterviewDetailsAddressById(Integer id) {
        return interviewDaysDetailsDAO.findInterviewDetailsAddressById(id);
    }
}
