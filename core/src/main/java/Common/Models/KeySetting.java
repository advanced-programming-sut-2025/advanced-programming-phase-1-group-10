package Common.Models;

import com.badlogic.gdx.Input;

public class KeySetting {

    private final int friendshipMenu = Input.Keys.F1;
    private final int openQuestMenu = Input.Keys.F2;
    private final int talkNPCMenu = Input.Keys.F3;
    private final int openMapMenu = Input.Keys.F4;
    private final int openIventoryMenu = Input.Keys.F5;
    private final int giftHistoryMenu = Input.Keys.F6;
    private final int openNPCMenu = Input.Keys.F7;
    private final int openSkillMenu = Input.Keys.F8;
    private final int chatHistoryMenu = Input.Keys.F9;

    public int getFriendshipMenu() {
        return friendshipMenu;
    }

    public int getOpenQuestMenu() {
        return openQuestMenu;
    }

    public int getTalkNPCMenu() {
        return talkNPCMenu;
    }

    public int getOpenMapMenu() {
        return openMapMenu;
    }

    public int getOpenIventoryMenu() {
        return openIventoryMenu;
    }

    public int getGiftHistoryMenu() {
        return giftHistoryMenu;
    }
    public int getOpenNPCMenu() {
        return openNPCMenu;
    }
    public int getOpenSkillMenu() {
        return openSkillMenu;
    }

    public int getChatHistoryMenu() {
        return chatHistoryMenu;
    }
}
