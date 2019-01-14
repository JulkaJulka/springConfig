package com.lesson6.avia_dz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "FLIGHT")
public class Flight {
    private Long id;
    private Plane plane;
    private Set<Passenger> passenger = new HashSet<>();
    private Date dateFlight;
    private String cityFrom;
    private  String cityTo;

    public Flight() {
    }

    @Id
    @SequenceGenerator(name = "SEQ_FLT", sequenceName = "SEQ_FLT", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_FLT", strategy = GenerationType.SEQUENCE)
    @Column(name = "FLIGHT_ID")
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANE_ID")
    public Plane getPlane() {
        return plane;
    }



    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FLIGHT_PASSENGER", joinColumns =
            {@JoinColumn(name = "FLIGHT_ID")}, inverseJoinColumns = {@JoinColumn(name = "PASSENGER_ID")})
    /*@JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "flights",cascade = CascadeType.ALL)*/
    public Set<Passenger> getPassenger() {
        return passenger;
    }

    /*@OneToMany( targetEntity = Passenger.class, mappedBy = "flights", fetch = FetchType.LAZY)
    public List<Passenger> getPassenger() {
        return passenger;
    }*/

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "DATE_FLIGHT")
    public Date getDateFlight() {
        return dateFlight;
    }

    @Column(name = "CITY_FROM")
    public String getCityFrom() {
        return cityFrom;
    }

    @Column(name = "CITY_TO")
    public String getCityTo() {
        return cityTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setPassenger(Set<Passenger> passenger) {
        this.passenger = passenger;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", dateFlight=" + dateFlight +
                ", cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(plane, flight.plane) &&
                Objects.equals(dateFlight, flight.dateFlight) &&
                Objects.equals(cityFrom, flight.cityFrom) &&
                Objects.equals(cityTo, flight.cityTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(plane, dateFlight, cityFrom, cityTo);
    }
}
