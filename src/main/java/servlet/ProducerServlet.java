package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Car car = new Car(req.getParameter("brand")
                , req.getParameter("model")
                , req.getParameter("licensePlate")
                , Long.parseLong(req.getParameter("price")));
        Long id = CarService.getInstance().addCar(car);
        if (id == -1){
            resp.setStatus(403);
        } else {
            resp.setStatus(200);
        }
    }
}
