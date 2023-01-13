package com.example.nermaxcooking1;

public class ControllerFilter {
    private static ControllerFilter controllerFilter;
    private double fromCall = 0, toCall = Double.POSITIVE_INFINITY,
    fromProteins = 0, toProteins = Double.POSITIVE_INFINITY,
    fromFats = 0, toFats = Double.POSITIVE_INFINITY,
    fromCarbohydrates = 0, toCarbohydrates = Double.POSITIVE_INFINITY;

    private ControllerFilter(){}

    public static synchronized ControllerFilter getController(){
        if(controllerFilter == null)
            controllerFilter = new ControllerFilter();
        return controllerFilter;
    }

    public double getFromCall() {
        return fromCall;
    }

    public void setFromCall(double fromCall) {
        this.fromCall = fromCall;
    }

    public double getToCall() {
        return toCall;
    }

    public void setToCall(double toCall) {
        this.toCall = toCall;
    }

    public double getFromProteins() {
        return fromProteins;
    }

    public void setFromProteins(double fromProteins) {
        this.fromProteins = fromProteins;
    }

    public double getToProteins() {
        return toProteins;
    }

    public void setToProteins(double toProteins) {
        this.toProteins = toProteins;
    }

    public double getFromFats() {
        return fromFats;
    }

    public void setFromFats(double fromFats) {
        this.fromFats = fromFats;
    }

    public double getToFats() {
        return toFats;
    }

    public void setToFats(double toFats) {
        this.toFats = toFats;
    }

    public double getFromCarbohydrates() {
        return fromCarbohydrates;
    }

    public void setFromCarbohydrates(double fromCarbohydrates) {
        this.fromCarbohydrates = fromCarbohydrates;
    }

    public double getToCarbohydrates() {
        return toCarbohydrates;
    }

    public void setToCarbohydrates(double toCarbohydrates) {
        this.toCarbohydrates = toCarbohydrates;
    }

    public void clear(){
        fromCall = 0; toCall = Double.POSITIVE_INFINITY;
        fromProteins = 0; toProteins = Double.POSITIVE_INFINITY;
        fromFats = 0; toFats = Double.POSITIVE_INFINITY;
        fromCarbohydrates = 0; toCarbohydrates = Double.POSITIVE_INFINITY;
    }
}
