package com.lesson6.avia_dz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class Filter {
    private Date dateFlight;
    private Date dateFrom;
    private Date dateTo;
    private String cityFrom;
    private String cityTo;
    private String model;

    public Filter() {
    }

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    public Date getDateFlight() {
        return dateFlight;
    }
   @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    public Date getDateFrom() {
        return dateFrom;
    }

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    public Date getDateTo() {
        return dateTo;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public String getModel() {
        return model;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setModel(String model) {
        this.model = model;
    }

   /* @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("dateFlight: ")
                .append(this.dateFlight).append("\n")
                .append("dateFrom: ")
                .append(this.dateFrom).append("\n")
                .append("dateTo: ")
                .append(this.dateTo).append("\n")
                .append("cityFrom: ")
                .append(this.cityFrom).append("\n")
                .append("cityTo: ")
                .append(this.cityTo).append("\n")
                .append("model: ")
                .append(this.model).append("\n");
        return stringBuilder.toString();
    }*/

    @Override
    public String toString() {
        return "Filter{" +
                "dateFlight=" + dateFlight +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
