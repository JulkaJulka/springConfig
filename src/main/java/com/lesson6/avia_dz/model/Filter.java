package com.lesson6.avia_dz.model;

import java.util.Date;

public class Filter {
    private Date dateFlight;
    private Date dateFrom;
    private Date dateTo;
    private String cityFrom;
    private String cityTo;
    private String model;

    public Filter(Date dateFlight, Date dateFrom, Date dateTo, String cityFrom, String cityTo, String model) {
        this.dateFlight = dateFlight;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.model = model;
    }

    public Date getDateFlight() {
        return dateFlight;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

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
}
