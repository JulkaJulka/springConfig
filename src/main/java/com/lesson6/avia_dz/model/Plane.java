package com.lesson6.avia_dz.model;

import javax.persistence.*;

@Entity
@Table(name = "PLANE")
public class Plane {
    private Long id;
    private String model;
    private String code;
    private int yearProduced;
    private double avgFuelConsumption;

    public Plane() {
    }

    @Id
    @SequenceGenerator(name = "SEQ_PLANE", sequenceName = "SEQ_PLANE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_PLANE", strategy = GenerationType.SEQUENCE)
    @Column(name = "PLANE_ID")
    public Long getId() {
        return id;
    }

    @Column(name = "MODEL_PLANE")
    public String getModel() {
        return model;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    @Column(name = "YEAR_PRODUCED")
    public int getYearProduced() {
        return yearProduced;
    }

    @Column(name = "AVG_FUEL_CONSUMPTION")
    public double getAvgFuelConsumption() {
        return avgFuelConsumption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setYearProduced(int yearProduced) {
        this.yearProduced = yearProduced;
    }

    public void setAvgFuelConsumption(double avgFuelConsumption) {
        this.avgFuelConsumption = avgFuelConsumption;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", code='" + code + '\'' +
                ", yearProduced=" + yearProduced +
                ", avgFuelConsumption=" + avgFuelConsumption +
                '}';
    }
}
