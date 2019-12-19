package service;

import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import servlet.NewDayServlet;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        List<DailyReport> list = getAllDailyReports();
        return list.get(list.size() - 1);
    }

    public void newDay() {
        Session session = sessionFactory.openSession();
        DailyReportDao dailyReportDao = new DailyReportDao(session);
        dailyReportDao.newDay();
        session.close();
    }

    public void deleteReports() {
        Session session = sessionFactory.openSession();
        DailyReportDao dailyReportDao = new DailyReportDao(session);
        dailyReportDao.deleteAll();
        session.close();
    }
}
