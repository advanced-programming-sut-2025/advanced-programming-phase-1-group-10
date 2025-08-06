import Client.Controllers.RegisterManuController;
import Common.Models.App;
import Common.Models.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterManuControllerTest {

    private RegisterManuController controller;

    @BeforeEach
    public void setup() {
        controller = new RegisterManuController();
    }

    @Test
    public void testSuggestAlternativeUsernames() {
        List<String> suggestions = controller.suggestAlternativeUsernames("testuser");
        assertNotNull(suggestions);
        assertFalse(suggestions.isEmpty());
        for (String suggestion : suggestions) {
            assertTrue(suggestion.startsWith("testuser"));
        }
    }

    @Test
    public void testRegisterSuccess() {
        Result result = controller.register(
                "testUser",
                "StrongPass1!",
                "StrongPass1!",
                "nickname",
                "test@example.com",
                "male"
        );
        assertTrue(result.state());
        assertEquals("user registered successfully!", result.message());
        assertNotNull(App.getInstance().getUserByUserName("testUser"));
    }

    @Test
    public void testRegisterWithWeakPassword() {
        Result result = controller.register(
                "user6",
                "weak",
                "weak",
                "nickname",
                "test2@example.com",
                "female"
        );
        assertFalse(result.state());
        assertTrue(result.message().contains("password is weak!"));
    }

    @Test
    public void testRegisterWithMismatchedPassword() {
        Result result = controller.register(
                "user6",
                "StrongPass1!",
                "WrongPass1!",
                "nickname",
                "test3@example.com",
                "female"
        );
        assertFalse(result.state());
        assertTrue(result.message().contains("confirm password doesn't match"));
    }

    @Test
    public void testLoginSuccess() {
        controller.register("user7", "Password123!", "Password123!", "nick", "u4@example.com", "male");

        Result result = controller.login("user7", "Password123!", "yes");
        assertTrue(result.state());
        assertTrue(result.message().contains("logged in successfully"));
    }

    @Test
    public void testLoginWithWrongPassword() {
        controller.register("user8", "Password123!", "Password123!", "nick", "u5@example.com", "male");

        Result result = controller.login("user8", "WrongPassword!", "yes");
        assertFalse(result.state());
        assertTrue(result.message().contains("password is incorrect"));
    }

    @Test
    public void testPickQuestionAndAnswer() {
        controller.register("user9", "Password123!", "Password123!", "nick", "u6@example.com", "female");
        App.getInstance().setCurrentUser(App.getInstance().getUserByUserName("user9"));

        Result result = controller.pickQuestion(2, "Tehran");
        assertTrue(result.state());

        Result answerResult = controller.answerSecurityQuestion("Tehran");
        assertTrue(answerResult.state());
    }

    @Test
    public void testChangePasswordSuccess() {
        controller.register("user10", "Password123!", "Password123!", "nick", "u7@example.com", "male");
        App.getInstance().setCurrentUser(App.getInstance().getUserByUserName("user10"));

        Result result = controller.changePassword("NewPass123!", false);
        assertTrue(result.state());
        assertTrue(result.message().contains("your password changed successfully"));
    }
}
