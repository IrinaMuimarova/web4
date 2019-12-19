package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import servlet.NewDayServlet;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public Car get(long id) {
        return (Car) session.get(Car.class, id);
    }

    public List<Car> getAll() {
        return (List<Car>) session.createCriteria(Car.class).list();

    }

    public Long addCar(Car car) {
        return (Long) session.save(car);
    }

    public void sellCar(Long id) {
        Transaction transaction = session.beginTransaction();
        Car car = session.get(Car.class, id);
        session.delete(car);
        transaction.commit();
        NewDayServlet.dailyReport.setSoldCars(NewDayServlet.dailyReport.getSoldCars() + 1);
        NewDayServlet.dailyReport.setEarnings(NewDayServlet.dailyReport.getEarnings() + car.getPrice());
    }

    public Long getCarId(String brand, String model, String licensePlate) {
        Criteria criteria = session.createCriteria(Car.class);
        List<Car> list = criteria.add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model))
                .add(Restrictions.eq("licensePlate", licensePlate)).list();
        if (list.size() > 0) {
            return list.get(0).getId();
        } else {
            return -1L;
        }
    }

    public int getModelCarCount(String brand, String model) {
        Criteria criteria = session.createCriteria(Car.class);
        List<Car> list = criteria.add(Restrictions.eq("brand", brand))
                .add(Restrictions.eq("model", model)).list();
        return list.size();
    }
}
