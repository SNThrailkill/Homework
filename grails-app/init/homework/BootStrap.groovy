package homework

import Homework.ROLE_WRITE
import Homework.User
import Homework.UserROLE_WRITE

class BootStrap {

    def springSecurityService
    def init = { servletContext ->
        def role = new ROLE_WRITE("ROLE_WRITE").save()
        def user = new User("admin", springSecurityService.encodePassword("admin")).save()
        UserROLE_WRITE.create(user, role)
    }
    def destroy = {
    }
}
