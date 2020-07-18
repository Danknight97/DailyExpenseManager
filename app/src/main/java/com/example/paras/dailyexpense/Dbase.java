package com.example.paras.dailyexpense;

public class Dbase {
    int price;
    String cat, date, dis, payment;

    public Dbase(int price, String cat, String date, String dis, String payment) {
        this.price = price;
        this.cat = cat;
        this.date = date;
        this.dis = dis;
        this.payment = payment;
    }

    //todo show selected date in label
}