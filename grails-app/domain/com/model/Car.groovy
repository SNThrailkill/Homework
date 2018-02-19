package com.model

class Car {

    String model
    String make
    int year

    static constraints = {
        //Since these are properties "blank" defaults to false
        year range: 1885..2018, nullable: false
        model nullable: false
        make nullable: false
    }
}
