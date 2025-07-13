import Controllers.ProfileMenuControllers;
import Models.App;
import Models.PlayerStuff.Gender;
import Models.Result;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileMenuControllersTest {

    private ProfileMenuControllers controller;
    private User user;

    @BeforeEach
    public void setup() {
        controller = new ProfileMenuControllers();
        user = new User("testNick", "Password1!", "testUser", Gender.Male);
        user.setEmail("test@gmail.com");
        App.getInstance().addUser(user);
        App.getInstance().setCurrentUser(user);
    }

    @Test
    public void testChangeUsernameSuccess() {
        Result result = controller.changeUsername("newUser");
        assertTrue(result.state());
        assertEquals("the username changed successfully to newUser!", result.message());
        assertEquals("newUser", App.getInstance().getCurrentUser().getUsername());
    }

    @Test
    public void testChangeUsernameInvalidFormat() {
        Result result = controller.changeUsername("$$invalid$$");
        assertFalse(result.state());
        assertEquals("username format is invalid!", result.message());
    }

    @Test
    public void testChangeUsernameAlreadyTaken() {
        User other = new User("nick", "pass", "takenUser", Gender.Male);
        other.setEmail("test@mail.com");
        App.getInstance().addUser(other);

        Result result = controller.changeUsername("takenUser");
        assertFalse(result.state());
        assertTrue(result.message().contains("username already taken!"));
    }

    @Test
    public void testChangeUsernameSameAsBefore() {
        Result result = controller.changeUsername("testUser");
        assertFalse(result.state());
        assertEquals("the new username is the same as the previous username!", result.message());
    }

    @Test
    public void testChangeNicknameSuccess() {
        Result result = controller.changeNickName("newNick");
        assertTrue(result.state());
        assertEquals("the nickname changed successfully to newNick!", result.message());
    }

    @Test
    public void testChangeNicknameSameAsBefore() {
        Result result = controller.changeNickName("testNick");
        assertFalse(result.state());
        assertEquals("the new nickname is the same as the previous nickname!", result.message());
    }

    @Test
    public void testChangeEmailSuccess() {
        Result result = controller.changeEmail("new@mail.com");
        assertTrue(result.state());
        assertEquals("the email changed successfully to new@mail.com!", result.message());
    }

    @Test
    public void testChangeEmailInvalidFormat() {
        Result result = controller.changeEmail("not-an-email");
        assertFalse(result.state());
        assertEquals("email format is invalid!", result.message());
    }

    @Test
    public void testChangeEmailAlreadyTaken() {
        User user1 = new User("user2", "pass", "nick", Gender.Male);
        App.getInstance().addUser(user1);
        user1.setEmail("taken@mail.com");
        Result result = controller.changeEmail("taken@mail.com");
        assertFalse(result.state());
        assertEquals("email already taken!", result.message());
    }

    @Test
    public void testChangeEmailSameAsBefore() {
        Result result = controller.changeEmail("test@mail.com");
        assertFalse(result.state());
        assertEquals("the new email is the same as the previous email!", result.message());
    }

    @Test
    public void testChangePasswordSuccess() {
        Result result = controller.changePassword("NewPass1!", "Password1!");
        assertTrue(result.state());
        assertEquals("the password changed successfully to NewPass1!", result.message());
    }

    @Test
    public void testChangePasswordWeak() {
        Result result = controller.changePassword("123", "Password1!");
        assertFalse(result.state());
        assertTrue(result.message().contains("password is weak!"));
    }

    @Test
    public void testChangePasswordSameAsBefore() {
        Result result = controller.changePassword("Password1!", "Password1!");
        assertFalse(result.state());
        assertEquals("the new password is the same as the previous password!", result.message());
    }

    @Test
    public void testChangePasswordWrongOld() {
        Result result = controller.changePassword("NewPass1!", "WrongOldPass!");
        assertFalse(result.state());
        assertEquals("the oldPassword us incorrect!", result.message());
    }

    @Test
    public void testUserInfo() {
        user.gold = 10;
        user.games = 5;
        Result result = controller.userInfo();
        assertTrue(result.state());
        assertTrue(result.message().contains("USER NAME : testUser"));
        assertTrue(result.message().contains("golds : 10"));
        assertTrue(result.message().contains("game played : 5"));
    }

    @Test
    public void testEnterMainMenuSuccess() {
        Result result = controller.enterMenu("main menu");
        assertTrue(result.state());
        assertEquals("you are now in MAIN MENU.", result.message());
    }

    @Test
    public void testEnterMainMenuFail() {
        Result result = controller.enterMenu("profile menu");
        assertFalse(result.state());
        assertEquals("you should go to MAIN MENU first.", result.message());
    }
}
