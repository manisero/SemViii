package pl.edu.pw.elka.sag.gui.constants;

import java.awt.Color;

public interface MapPaintSettings
{
	public static final int FPS = 25;
	
	public static final double WINDOW_TO_SCREEN_SIZE_RATIO = 0.75;
	public static final double PANEL_TO_WINDOW_SIZE_RATIO = 0.8;
	public static final double STREET_WIDTH_TO_LENGTH_RATIO = 0.25;
	public static final int LANES_PER_STREET = 2;
	public static final double LANE_TO_SPACE_LENGTH_RATIO = 0.3;
	public static final double LIGHTS_TO_LANE_WIDTH_RATIO = 0.5;
	
	public static final Color STREET_COLOR = Color.BLACK;
	public static final Color LANE_COLOR = Color.YELLOW;
	public static final Color DEFAULT_CAR_COLOR = Color.WHITE;
	public static final Color LIGHTS_RED_COLOR = Color.RED;
	public static final Color LIGHTS_YELLOW_COLOR = Color.YELLOW;
	public static final Color LIGHTS_GREEN_COLOR = Color.GREEN;
}
