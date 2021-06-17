package com.example.finalproject;

public class Data {
    private String region;
    private String distanceStep;
    private String period;
    private String peopleLimit;
    private String step;
    private String info;
    private boolean timeLimit;

    public void setRegion(String region) {
        this.region = region;
    }

    public void setDistanceStep(String distanceStep) {
        this.distanceStep = distanceStep;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setPeopleLimit(String peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTimeLimit(boolean timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getRegion() {
        return region;
    }

    public String getDistanceStep() {
        return distanceStep;
    }

    public String getPeriod() {
        return period;
    }

    public String getPeopleLimit() {
        return peopleLimit;
    }

    public String getStep() {
        return step;
    }

    public String getInfo() {
        return info;
    }

    public boolean isTimeLimit() {
        return timeLimit;
    }
}
