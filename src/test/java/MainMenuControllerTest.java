package java;

import Controllers.MainMenuControllers;
import Models.App;
import Models.Menu;
import Models.Result;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuControllerTest {

    private MainMenuControllers controller;

    @BeforeEach
    void setUp() {
        controller = new MainMenuControllers();
        App.getInstance().setCurrentUser(null);
        App.getInstance().setCurrentMenu(null);
    }

    @Test
    void testEnterLoginMenu() {
        Result result = controller.enterMenu("login menu");
        assertTrue(result.state());
        assertEquals("you are now in LOGIN MENU.", result.message());
        assertEquals(Menu.LoginMenu, App.getInstance().getCurrentMenu());
    }

    @Test
    void testEnterGameMenu() {
        Result result = controller.enterMenu("game menu");
        assertTrue(result.state());
        assertEquals("you are now in GAME MENU.", result.message());
        assertEquals(Menu.GameMenu, App.getInstance().getCurrentMenu());
    }

    @Test
    void testEnterProfileMenu() {
        Result result = controller.enterMenu("profile menu");
        assertTrue(result.state());
        assertEquals("you are now in PROFILE MENU.", result.message());
        assertEquals(Menu.ProfileMenu, App.getInstance().getCurrentMenu());
    }

    @Test
    void testEnterInvalidMenu() {
        Result result = controller.enterMenu("invalid menu");
        assertFalse(result.state());
        assertTrue(result.message().contains("enter the correct MENU NAME"));
        assertNull(App.getInstance().getCurrentMenu());
    }

    @Test
    void testLogoutWithoutLogin() {
        App.getInstance().setCurrentUser(null);
        Result result = controller.logout();
        assertFalse(result.state());
        assertEquals("you should login first!", result.message());
    }

    @Test
    void testLogoutAfterLogin() {
        User dummyUser = new User("u", "p", "u", null);
        App.getInstance().setCurrentUser(dummyUser);
        App.getInstance().setCurrentMenu(Menu.GameMenu);

        Result result = controller.logout();

        assertTrue(result.state());
        assertEquals("you logged out successfully! you are now in LOGIN/REGISTER Menu!", result.message());
        assertNull(App.getInstance().getCurrentUser());
        assertEquals(Menu.LoginMenu, App.getInstance().getCurrentMenu());
    }
}
