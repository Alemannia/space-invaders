package lufti.invaders;

/**
 *
 * @author ubik
 */
public class Config {
	public static final int GAME_WIDTH = 4*224;
	public static final int GAME_HEIGHT = 4*170;
	public static final int WINDOW_MARGIN_HOR = 40;
	public static final int WINDOW_MARGIN_VERT = 10;
	public static final int WINDOW_WIDTH = GAME_WIDTH+2*WINDOW_MARGIN_HOR;
	public static final int WINDOW_HEIGHT = GAME_HEIGHT+2*WINDOW_MARGIN_VERT;
	
	public static final int GAME_LEFT = WINDOW_MARGIN_HOR;
	public static final int GAME_RIGHT = GAME_WIDTH + WINDOW_MARGIN_HOR;
	public static final int GAME_TOP = WINDOW_MARGIN_VERT;
	public static final int GAME_BOTTOM = GAME_HEIGHT + WINDOW_MARGIN_VERT;
	public static final int GAME_MID_HOR = GAME_WIDTH/2 + WINDOW_MARGIN_HOR;
	public static final int GAME_MID_VERT = GAME_HEIGHT/2 + WINDOW_MARGIN_VERT;
	
	public static final int INVADER_SPACING_HOR = 4*4;
	public static final int INVADER_SPACING_VERT = 4*8;
	public static final int INVADER_COLS = 11;
	public static final String[] INVADER_ROWS = new String[]{"InvaderC", "InvaderA", "InvaderA", "InvaderB", "InvaderB"};
	
	public static final int MAX_INVADER_WIDTH = 4*12;
	public static final int MAX_INVADER_HEIGHT = 4*8;
	
	public static final int INVADER_BLOCK_WIDTH = (INVADER_COLS-1) * (INVADER_SPACING_HOR+MAX_INVADER_WIDTH) + MAX_INVADER_WIDTH;
	public static final int INVADER_START_X = GAME_MID_HOR - INVADER_BLOCK_WIDTH/2; 
	public static final int INVADER_START_Y = GAME_BOTTOM - 4*150; 
	public static final int PLAYER_START_X = GAME_MID_HOR; 
	public static final int PLAYER_START_Y = GAME_BOTTOM - 4*24; 
	
	public static final int NUM_BUNKERS = 4; 
	public static final int BUNKER_START_X = 4*32; 
	public static final int BUNKER_START_Y = GAME_BOTTOM - 4*40; 
	public static final int BUNKER_SPACING = 4*23; 
	public static final int FLOOR = GAME_BOTTOM - 4*17; 
}
