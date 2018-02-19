package com.model

import grails.validation.ValidationException
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

class CarController {

    CarService carService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond carService.list(params), model: [carCount: carService.count()]
    }

    @Secured('IS_AUTHENTICATED_ANONYMOUSLY')
    def show(Long id) {
        respond carService.get(id)
    }

    @Secured(['ROLE_WRITE'])
    def create() {
        respond new Car(params)
    }

    @Secured(['ROLE_WRITE'])
    def save(Car car) {
        if (car == null) {
            notFound()
            return
        }

        try {
            carService.save(car)
        } catch (ValidationException e) {
            respond car.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'car.label', default: 'Car'), car.id])
                redirect car
            }
            '*' { respond car, [status: CREATED] }
        }
    }

    @Secured(['ROLE_WRITE'])
    def edit(Long id) {
        respond carService.get(id)
    }

    @Secured(['ROLE_WRITE'])
    def update(Car car) {
        if (car == null) {
            notFound()
            return
        }

        try {
            carService.save(car)
        } catch (ValidationException e) {
            respond car.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'car.label', default: 'Car'), car.id])
                redirect car
            }
            '*' { respond car, [status: OK] }
        }
    }

    @Secured(['ROLE_WRITE'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        carService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'car.label', default: 'Car'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'car.label', default: 'Car'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
