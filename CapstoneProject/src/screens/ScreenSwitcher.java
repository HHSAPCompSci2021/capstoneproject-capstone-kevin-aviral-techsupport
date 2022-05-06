package screens;
/**
 * 
 * @author Aviral Vaidya, Kevin Ren
 * The screenswitcher interface is used to change screens
 */
public interface ScreenSwitcher {

	public static final int MENU_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	/**
	 * 
	 * @param i screen number to switch to
	 */
	public void switchScreen(int i);

}
