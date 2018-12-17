package name.edds.mileageservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    public static String CURRENT_USER_EMAIL;

    @Value("${mileage.current_user_email}")
    public void setCurrentUserEmail(String currentUserEmail) {
        CURRENT_USER_EMAIL = currentUserEmail;
    }

}
