package com.lesson6.avia_dz;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.avia_dz.DAO.FlightDaoImpl;
import com.lesson6.avia_dz.DAO.PlaneDaoImpl;
import com.lesson6.avia_dz.model.Filter;
import com.lesson6.avia_dz.model.Flight;
import com.lesson6.avia_dz.model.Passenger;
import com.lesson6.avia_dz.model.Plane;
import com.lesson6.avia_dz.service.FlightServiceImpl;
import com.lesson6.avia_dz.service.PassengerService;
import com.lesson6.avia_dz.service.PassengerServiceImpl;
import com.lesson6.avia_dz.service.PlaneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@EnableWebMvc

public class AviaController {

    private PlaneServiceImpl planeService;
    private FlightServiceImpl flightService;
    private PassengerServiceImpl passengerService;

    public AviaController(PlaneServiceImpl planeService, FlightServiceImpl flightService, PassengerServiceImpl passengerService) {
        this.planeService = planeService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findPlane", produces = "text/plain")
    public @ResponseBody
    String findPlane(HttpServletRequest req) {
        String idFind = req.getParameter("idPlane");
        Long id = Long.parseLong(idFind);

        Plane plane = planeService.findOne(id);

        if (plane == null)
            return "Plane id " + id + "doesn't exist in DB";
        return plane.toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Plane/save", produces = "application/json")
    public @ResponseBody
    String savePlane(HttpServletRequest req) {
        Plane plane = convertJSONStringToPlane(req);

        return planeService.save(plane).toString() + "was saving successful";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Plane/update", produces = "application/json")
    public @ResponseBody
    String updatePlane(HttpServletRequest req) {

        Plane plane = convertJSONStringToPlane(req);
        try {
            planeService.update(plane);
            return plane.toString() + "was updating successful";

        } catch (BadRequestException e) {
            return "Update unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Plane/delete", produces = "application/json")
    public @ResponseBody
    String deletePlane(HttpServletRequest req) {

        String id = req.getParameter("idPlane");
        Long idPlane = Long.parseLong(id);

        try {
            planeService.delete(idPlane);
            return "OK" + "Plane id " + idPlane + " was deleted successfully";

        } catch (BadRequestException e) {
            e.printStackTrace();
            return "Deleting unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Plane/old")
    public @ResponseBody
    List<Plane> oldPlanes() {

        return planeService.oldPlanes();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Plane/regular")
    public @ResponseBody
    List<Plane> regularPlanes(HttpServletRequest req) {
        String year = req.getParameter("year");
        int yearInt = Integer.parseInt(year);

        return planeService.regularPlane(yearInt);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findPassenger", produces = "text/plain")
    public @ResponseBody
    String findPassenger(HttpServletRequest req) {
        String idFind = req.getParameter("idPassenger");
        Long id = Long.parseLong(idFind);

        Passenger passenger = passengerService.findOne(id);

        if (passenger == null)
            return "Passenger id " + id + " doesn't exist in DB";
        return passenger.toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Passenger/save", produces = "application/json")
    public @ResponseBody
    String savePassenger(HttpServletRequest req) {
        Passenger passenger = convertJSONStringToPassenger(req);

        return passengerService.save(passenger).toString() + "was saving successful";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Passenger/update", produces = "application/json")
    public @ResponseBody
    String updatePassenger(HttpServletRequest req) {
        Passenger passenger = convertJSONStringToPassenger(req);

        try {
            passengerService.update(passenger);
            return passenger.toString() + "was updating successful";

        } catch (BadRequestException e) {
            return "Update unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Passenger/delete", produces = "application/json")
    public @ResponseBody
    String deletePassenger(HttpServletRequest req) {

        String id = req.getParameter("idPassenger");
        Long idPassenger = Long.parseLong(id);

        try {
            passengerService.delete(idPassenger);
            return "OK" + "Passenger id " + idPassenger + " was deleted successfully";

        } catch (BadRequestException e) {
            e.printStackTrace();
            return "Deleting unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Passenger/regular")
    public @ResponseBody
    List<Passenger> regularPassengers(HttpServletRequest req) {
        String year = req.getParameter("year");
        int yearInt = Integer.parseInt(year);

        return passengerService.regularPassengers(yearInt);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/Flight/save", produces = "application/json")
    public @ResponseBody
    String saveFlight(HttpServletRequest req) {
        Flight flight = convertJSONStringToFlight(req);

        return flightService.save(flight).toString() + "was saving successful";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findFlight", produces = "text/plain")
    public @ResponseBody
    String findFlight(HttpServletRequest req) {
        String idFind = req.getParameter("idFlight");
        Long id = Long.parseLong(idFind);
        Flight flight = flightService.findOne(id);
        if (flight == null)
            return "Flight id " + id + " doesn't exist in DB";
        return flight.toString();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/Flight/update", produces = "application/json")
    public @ResponseBody
    String updateFlight(HttpServletRequest req) {
        Flight flight = convertJSONStringToFlight(req);
        try {
            flightService.update(flight);
            return flight.toString() + "was updating successful";

        } catch (BadRequestException e) {
            return "Update unsuccessful " + e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Flight/delete", produces = "application/json")
    public @ResponseBody
    String deleteFlight(HttpServletRequest req) {

        String id = req.getParameter("idFlight");
        Long idFlight = Long.parseLong(id);

        try {
            flightService.delete(idFlight);
            return "OK" + "Flight id " + idFlight + " was deleted successfully";

        } catch (BadRequestException e) {
            e.printStackTrace();
            return "Deleting unsuccessful " + e.getMessage();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/Flight/mostPopularTo")
    public @ResponseBody
    List<Flight> mostPopularTo(HttpServletRequest req) {
        String city = req.getParameter("city");

        return flightService.mostPopularTo(city);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/Flight/mostPopularFrom", produces = "application/json")
    public @ResponseBody
    List<Flight> mostPopularFrom(HttpServletRequest req) {
        String city = req.getParameter("city");

        return flightService.mostPopularFrom(city);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/Flight/flightsByDate")
    public @ResponseBody
    List<Flight> flightsByDate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Filter filter = convertJSONStringToFilter(req);

       return flightService.flightsByDate(filter);
    }
    private Filter convertJSONStringToFilter(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
     //   mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        //DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
      //  mapper.setDateFormat(df);

        try (InputStream is = req.getInputStream()) {
            Filter filter = mapper.readValue(is, Filter.class);

            return filter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Passenger convertJSONStringToPassenger(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = req.getInputStream()) {
            Passenger passenger = mapper.readValue(is, Passenger.class);

            return passenger;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Flight convertJSONStringToFlight(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = req.getInputStream()) {
            Flight flight = mapper.readValue(is, Flight.class);

            return flight;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Plane convertJSONStringToPlane(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = req.getInputStream()) {
            Plane plane = mapper.readValue(is, Plane.class);

            return plane;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
