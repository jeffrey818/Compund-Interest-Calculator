package com.javafx.ComInterestCal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Calculation {

    private SimpleDoubleProperty principal;
    private SimpleDoubleProperty interest;
    private SimpleIntegerProperty yearNum;
    private final SimpleDoubleProperty closingBalance;

    public Calculation(double principal, double interest, int yearNum, double closingBalance) {
        this.principal = new SimpleDoubleProperty(principal);
        this.interest = new SimpleDoubleProperty(interest);
        this.yearNum = new SimpleIntegerProperty(yearNum);
        this.closingBalance = new SimpleDoubleProperty(closingBalance);
    }

    public void setPrincipal(double principal) {
        this.principal.set(principal);
    }

    public void setInterest(double interest) {
        this.interest.set(interest);
    }

    public void setYearNum(int yearNum) {
        this.yearNum.set(yearNum);
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance.set(closingBalance);
    }

    public double getPrincipal() {
        return principal.get();
    }

    public SimpleDoubleProperty principalProperty() {
        return principal;
    }

    public double getInterest() {
        return interest.get();
    }

    public SimpleDoubleProperty interestProperty() {
        return interest;
    }

    public int getYearNum() {
        return yearNum.get();
    }

    public SimpleIntegerProperty yearNumProperty() {
        return yearNum;
    }

    public double getClosingBalance() {
        return closingBalance.get();
    }

    public SimpleDoubleProperty closingBalanceProperty() {
        return closingBalance;
    }
}
