package com.myprj.crawler.model.target;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author DienNM (DEE)
 */

@Entity
@Table(name = "tablet_product")
public class TabletProductModel extends ProductModel {

    private static final long serialVersionUID = 1L;

    @Column(name = "brand")
    private String brand;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private String weight;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "screen_type")
    private String screenType;

    @Column(name = "chip_name")
    private String chipName;

    @Column(name = "chip_type")
    private String chipType;

    @Column(name = "chip_speed")
    private String chipSpeed;

    @Column(name = "os")
    private String os;

    @Column(name = "ram")
    private String ram;

    @Column(name = "individual_ctg")
    private String individualCtg;

    public TabletProductModel() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getChipName() {
        return chipName;
    }

    public void setChipName(String chipName) {
        this.chipName = chipName;
    }

    public String getChipType() {
        return chipType;
    }

    public void setChipType(String chipType) {
        this.chipType = chipType;
    }

    public String getChipSpeed() {
        return chipSpeed;
    }

    public void setChipSpeed(String chipSpeed) {
        this.chipSpeed = chipSpeed;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getIndividualCtg() {
        return individualCtg;
    }

    public void setIndividualCtg(String individualCtg) {
        this.individualCtg = individualCtg;
    }

}
