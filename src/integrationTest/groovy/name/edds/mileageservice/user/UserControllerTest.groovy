package name.edds.mileageservice.user

import groovy.transform.TypeChecked
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 *  Test the queries to the UserController
 *
 *  FIXME: this currently depends on MongoDB running, so is better placed as a integration test.
    The build currently doesn't provide a lot of the dependencies to this sourceSet.
    Hold off on this test until we research best practices rather than copy the full set of dependencies.
 */
@TypeChecked
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends GroovyTestCase {

    @Autowired
    private MockMvc mvc

    /**
     *  Test UserController
     */

    @Test
    void getUsersTest() {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(MockMvcResultMatchers.status().isOk())
             /*   .andExpect(content().string("Hello World")); */

    }
}
