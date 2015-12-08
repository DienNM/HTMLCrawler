package com.myprj.crawler.domain.target;

import java.util.List;

import com.myprj.crawler.annotation.DataTransfer;
import com.myprj.crawler.model.target.TabletProductModel;
import com.myprj.crawler.util.converter.EntityConverter;

/**
 * @author DienNM (DEE)
 */

public class TabletProductData extends ProductData {

    private static final long serialVersionUID = 1L;

    @DataTransfer("brand")
    private String brand;

    @DataTransfer("manufacturer")
    private String manufacturer;

    @DataTransfer("color")
    private String color;

    @DataTransfer("weight")
    private String weight;

    @DataTransfer("resolution")
    private String resolution;

    @DataTransfer("screen_type")
    private String screenType;

    @DataTransfer("chip_name")
    private String chipName;

    @DataTransfer("chip_type")
    private String chipType;

    @DataTransfer("chip_speed")
    private String chipSpeed;

    @DataTransfer("os")
    private String os;

    @DataTransfer("ram")
    private String ram;

    @DataTransfer("individual_ctg")
    private String individualCtg;

    public TabletProductData() {
    }

    public static void toDatas(List<TabletProductModel> sources, List<TabletProductData> dests) {
        for (TabletProductModel source : sources) {
            TabletProductData dest = new TabletProductData();
            toData(source, dest);
            dests.add(dest);
        }
    }

    public static void toData(TabletProductModel source, TabletProductData dest) {
        EntityConverter.convert2Data(source, dest);
    }

    public static void toModels(List<TabletProductData> sources, List<TabletProductModel> dests) {
        for (TabletProductData source : sources) {
            TabletProductModel dest = new TabletProductModel();
            toModel(source, dest);
            dests.add(dest);
        }
    }

    public static void toModel(TabletProductData source, TabletProductModel dest) {
        EntityConverter.convert2Data(source, dest);
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