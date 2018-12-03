package name.edds.mileageservice.user

import groovy.transform.TypeChecked
import org.junit.Test

/**
 *  Test the construction and use of a Car
 */
@TypeChecked
class UserServiceTest extends GroovyTestCase {

    @Test
    void testEmailValidation() {
        UserService userService = new UserService()
        assertTrue(userService.isValidEmailAddress("user@example.com"))
        assertFalse(userService.isValidEmailAddress("user"))
    }
}
