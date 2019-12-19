package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }


    public List<Car> getAllCars() {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        List<Car> list = new ArrayList<>();
        list = carDao.getAll();
        session.close();
        return list;
    }

    public Long addCar(Car car) {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        if (carDao.getModelCarCount(car.getBrand(), car.getModel()) < 10) {
            Long id = carDao.addCar(car);
            session.close();
            return id;
        } else {
            return -1L;
        }
    }

    public void sellCar(String brand, String model, String licensePlate) throws SQLException {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        Long id = carDao.getCarId(brand, model, licensePlate);
        if (id == -1) {
            throw new SQLException();
        }
        carDao.sellCar(id);
        session.close();
    }


    public Long getCarId(String brand, String model, String licensePlate) {
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);
        Long id = carDao.getCarId(brand, model, licensePlate);
        session.close();
        return id;
    }
}
