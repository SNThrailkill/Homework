package com.model

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.hibernate.SessionFactory
import spock.lang.Specification

@Integration
@Rollback
class CarServiceSpec extends Specification {

    CarService carService
    SessionFactory sessionFactory

    private Long setupData() {
        Car car = new Car(year: 2000, make: "Toyota", model: "Supra").save(flush: true, failOnError: true)
        car.id
    }

    void "test get"() {
        setupData()

        expect:
        carService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Car> carList = carService.list()

        then:
        carList.size() == 1
    }

    void "test count"() {
        setupData()

        expect:
        carService.count() == 1
    }

    void "test delete"() {
        Long carId = setupData()

        expect:
        carService.count() == 1

        when:
        carService.delete(carId)
        sessionFactory.currentSession.flush()

        then:
        carService.count() == 0
    }

    void "test save"() {
        when:
        Car car = new Car(year: 2000, make: "Toyota", model: "Supra")
        carService.save(car)

        then:
        car.id != null
    }
}
