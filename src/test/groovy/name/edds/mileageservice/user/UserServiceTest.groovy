package name.edds.mileageservice.user

import groovy.transform.TypeChecked

/**
 *  Test the construction and use of a Car
 */
@TypeChecked
class UserServiceTest extends GroovyTestCase {

    void testEmailValidation() {
        UserService userService = new UserService()
        assertTrue(userService.isValidEmailAddress("user@example.com"))
        assertFalse(userService.isValidEmailAddress("user"))
    }
}
