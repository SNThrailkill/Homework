package Homework

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache = true, includeNames = true, includePackage = false)
class UserROLE_WRITE implements Serializable {

    private static final long serialVersionUID = 1

    User user
    ROLE_WRITE ROLE_WRITE

    UserROLE_WRITE(User u, ROLE_WRITE r) {
        this()
        user = u
        ROLE_WRITE = r
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof UserROLE_WRITE)) {
            return false
        }

        other.user?.id == user?.id && other.ROLE_WRITE?.id == ROLE_WRITE?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (ROLE_WRITE) builder.append(ROLE_WRITE.id)
        builder.toHashCode()
    }

    static UserROLE_WRITE get(long userId, long ROLE_WRITEId) {
        criteriaFor(userId, ROLE_WRITEId).get()
    }

    static boolean exists(long userId, long ROLE_WRITEId) {
        criteriaFor(userId, ROLE_WRITEId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long ROLE_WRITEId) {
        UserROLE_WRITE.where {
            user == User.load(userId) &&
                    ROLE_WRITE == ROLE_WRITE.load(ROLE_WRITEId)
        }
    }

    static UserROLE_WRITE create(User user, ROLE_WRITE ROLE_WRITE, boolean flush = false) {
        def instance = new UserROLE_WRITE(user: user, ROLE_WRITE: ROLE_WRITE)
        instance.save(flush: flush, insert: true)
        instance
    }

    static boolean remove(User u, ROLE_WRITE r, boolean flush = false) {
        if (u == null || r == null) return false

        int rowCount = UserROLE_WRITE.where { user == u && ROLE_WRITE == r }.deleteAll()

        if (flush) {
            UserROLE_WRITE.withSession { it.flush() }
        }

        rowCount
    }

    static void removeAll(User u, boolean flush = false) {
        if (u == null) return

        UserROLE_WRITE.where { user == u }.deleteAll()

        if (flush) {
            UserROLE_WRITE.withSession { it.flush() }
        }
    }

    static void removeAll(ROLE_WRITE r, boolean flush = false) {
        if (r == null) return

        UserROLE_WRITE.where { ROLE_WRITE == r }.deleteAll()

        if (flush) {
            UserROLE_WRITE.withSession { it.flush() }
        }
    }

    static constraints = {
        ROLE_WRITE(validator: {
            ROLE_WRITE r, UserROLE_WRITE ur ->
                if (ur.user == null || ur.user.id == null) return
                boolean existing = false
                UserROLE_WRITE.withNewSession {
                    existing = UserROLE_WRITE.exists(ur.user.id, r.id)
                }
                if (existing) {
                    return 'userRole.exists'
                }
        })
    }

    static mapping = {
        id composite: ['user', 'ROLE_WRITE']
        version false
    }
}
