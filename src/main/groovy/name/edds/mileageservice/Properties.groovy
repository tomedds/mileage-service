package name.edds.mileageservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Properties {

    public static String CURRENT_USER_EMAIL;

    @Value('${mileage.current_user_email}')
    void setCurrentUserEmail(String currentUserEmail) {
        CURRENT_USER_EMAIL = currentUserEmail
    }

}
