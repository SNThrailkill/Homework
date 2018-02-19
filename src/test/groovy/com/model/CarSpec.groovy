package com.model

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CarSpec extends Specification implements DomainUnitTest<Car> {

    def setup() {
    }

    def cleanup() {
    }

    void "Test object population"() {
        when: "A car is created using valid properties"
        int year = 2018
        String model = 'Test Model'
        String make = 'Test Make'
        Car c = new Car(year: year, model: model, make: make)
        then: "car is created with corresponding values set"
        c.year == year
        c.model == model
        c.make == make
    }

    void "Test that car year is in range"() {
        expect:
        new Car(year: year, make: "Toyota", model: "Supra").validate(['year']) == valid

        where:
        year   | valid
        null   | false
        0      | false
        1884   | false
        1885   | true
        2000   | true
        2018   | true
        2019   | false
        200000 | false
    }

    void "Test that properties are not nullable"() {
        when: "A car is created with null values"
        domain.year = null
        domain.model = null
        domain.make = null
        then: "validation fails"
        thrown IllegalArgumentException
    }

    void "Test that properties are not empty"() {
        when: "A car is created with empty values"
        domain.model = ''
        domain.make = ''
        domain.year = 1000
        then: "validation fails"
        !domain.validate()
    }
}
