package com.example.application.travelue;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Adri on 26/01/2017.
 */

/**
 * Created by Adri on 26/01/2017.
 */

public class Route {
    public String distance;
    public String duration;
    public String endAddress;
    public LatLng endLocation;//
    public String startAddress;
    public LatLng startLocation;//
    public boolean allowEating, allowSmoking;
    public int numberOfPasangers;
    public String typeOfUser, carModel,typeOfInsurance,hour,startDay,finisDay;
    public List<LatLng> points;//no


    public Route(){}
/**
    public Route(String distance, String duration, String endAddress, String startAddress, String typeOfUser, String carModel, String typeOfInsurance, String hour, String startDay, String finisDay, boolean allowEating, boolean allowSmoking, int numberOfPasangers) {
        this.endAddress = endAddress;
        this.startAddress = startAddress;
        this.allowEating = allowEating;
        this.allowSmoking = allowSmoking;
        this.numberOfPasangers = numberOfPasangers;
        this.typeOfUser = typeOfUser;
        this.carModel = carModel;
        this.typeOfInsurance = typeOfInsurance;
        this.hour = hour;
        this.startDay = startDay;
        this.finisDay = finisDay;
    }
*/
    public Route(String distance, String duration, String endAddress, String startAddress, String typeOfUser, String carModel, String typeOfInsurance, String hour, String startDay, String finisDay, boolean allowEating, boolean allowSmoking, int numberOfPasangers) {
        this.distance = distance;
        this.duration = duration;
        this.endAddress = endAddress;
        this.startAddress = startAddress;
        this.allowEating = allowEating;
        this.allowSmoking = allowSmoking;
        this.numberOfPasangers = numberOfPasangers;
        this.typeOfUser = typeOfUser;
        this.carModel = carModel;
        this.typeOfInsurance = typeOfInsurance;
        this.hour = hour;
        this.startDay = startDay;
        this.finisDay = finisDay;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public boolean isAllowEating() {
        return allowEating;
    }

    public void setAllowEating(boolean allowEating) {
        this.allowEating = allowEating;
    }

    public boolean isAllowSmoking() {
        return allowSmoking;
    }

    public void setAllowSmoking(boolean allowSmoking) {
        this.allowSmoking = allowSmoking;
    }

    public int getNumberOfPasangers() {
        return numberOfPasangers;
    }

    public void setNumberOfPasangers(int numberOfPasangers) {
        this.numberOfPasangers = numberOfPasangers;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(String typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getFinisDay() {
        return finisDay;
    }

    public void setFinisDay(String finisDay) {
        this.finisDay = finisDay;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}