package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import servlet.NewDayServlet;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public void newDay() {
        session.save(NewDayServlet.dailyReport);
        NewDayServlet.dailyReport = new DailyReport(0L, 0L);
    }

    public void deleteAll() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM DailyReport d").executeUpdate();
        session.createQuery("DELETE FROM Car c").executeUpdate();
        transaction.commit();
    }
}
