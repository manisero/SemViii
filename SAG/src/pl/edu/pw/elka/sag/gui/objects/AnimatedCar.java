package pl.edu.pw.elka.sag.gui.objects;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.gui.constants.PaintSettings;
import pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyReceiver;

public class AnimatedCar extends Canvas implements IPaintablePropertyReceiver, ActionListener
{
	private static final long serialVersionUID = 3556163878139145448L;
	
	private Location currentLocation;
	private Location previousLocation;
	private long moveAnimationTimeInMillis;

	private IPaintablePropertyProvider propertiesProvider;
	
	private Integer carRadius;
	private Long frames;
	
	private Point previousPosition;
	private Point currentPosition;
	private Point nextPosition;
	
	private int pixelsToMove;
	private long currentFrames;
	private Timer timer;
	
	private List<Location> destinies = new LinkedList<Location>();

	/**
	 * Constructs animated car.
	 * 
	 * @param currentLocation heading {@link pl.edu.pw.elka.sag.entities.Location}
	 * @param previousLocation starting {@link pl.edu.pw.elka.sag.entities.Location}
	 * @param moveAnimationTimeInMillis how long to animate car's single movement
	 */
	public AnimatedCar(Location currentLocation, Location previousLocation, long moveAnimationTimeInMillis)
	{
		this.currentLocation = currentLocation;
		this.previousLocation = previousLocation;
		this.moveAnimationTimeInMillis = moveAnimationTimeInMillis;
	}

	/**
	 * Starts animating the car.
	 */
	private void startAnimation()
	{
		Location nextLocation = destinies.remove(0);
		
		previousPosition = propertiesProvider.getCarScreenPosition(currentLocation, previousLocation);
		nextPosition = propertiesProvider.getCarScreenPosition(nextLocation, currentLocation);
		currentPosition = previousPosition;
		
		pixelsToMove = Math.abs(nextPosition.x - previousPosition.x) + Math.abs(nextPosition.y - previousPosition.y);
		
		int frameLength = 1000 / PaintSettings.FPS;
		
		timer = new Timer(frameLength, this);
		timer.setRepeats(true);
		timer.start();
	}
	
	/**
	 * Sets properties provider.
	 * 
	 * @param propertiesProvider {@link pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyProvider} to set
	 */
	@Override
	public void setPropertyProvider(IPaintablePropertyProvider propertiesProvider)
	{
		this.propertiesProvider = propertiesProvider;
	}
	
	/**
	 * Returns an indication whether first move of animation should be alongside the x axis.
	 * 
	 * @return boolean is first move of the animation sentence alongside the x axis
	 */
	private boolean isFirstMoveAlongsideXAxis()
	{
		if (currentLocation.getX() - previousLocation.getX() != 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Lazy loading of number of frames.
	 * 
	 * @return long number of frames
	 */
	private long getNumberOfFrames()
	{
		if (frames == null)
		{
			frames = PaintSettings.FPS * moveAnimationTimeInMillis / 1000;
		}
		
		return frames;
	}
	
	/**
	 * Lazy loading of car radius.
	 * 
	 * @return int car object's radius
	 */
	private int getCarRadius()
	{
		if (carRadius == null)
		{
			carRadius = propertiesProvider.getCarBoundingBoxSize();
		}
		
		return carRadius;
	}
	
	/**
	 * Returns current car's location.
	 * 
	 * @return current {@link pl.edu.pw.elka.sag.entities.Location}
	 */
	public Location getCarLocation()
	{
		return currentLocation;
	}
	
	/**
	 * Adds new car's destination.
	 * 
	 * @param location car's destination {@link pl.edu.pw.elka.sag.entities.Location}
	 */
	public void addDestination(Location location)
	{
		destinies.add(location);
		
		startAnimation();
	}
	
	/**
	 * Lazy loading of car's current position.
	 * 
	 * @return current position of a car
	 */
	private Point getCurrentPosition()
	{
		if (currentPosition == null)
		{
			currentPosition = propertiesProvider.getCarScreenPosition(currentLocation, previousLocation);
		}
		
		return currentPosition;
	}
	
	/**
	 * Paints animated car.
	 * 
	 * @param graphics {@link java.awt.Graphics} instance
	 */
	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Point currentPosition = getCurrentPosition();
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.WHITE);
		graphics2D.fillOval(currentPosition.x, currentPosition.y, getCarRadius(), getCarRadius());
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		long frames = getNumberOfFrames();
		
		if (++currentFrames == frames)
		{
			timer.stop();
		}
		
		int singleMoveLengthInPixels = (int) (pixelsToMove / frames);
		int remainingPixels = pixelsToMove - (int) frames * singleMoveLengthInPixels;
		int remainingPixelsCycle = (int) frames / remainingPixels;
		
		if (currentFrames % remainingPixelsCycle == 0)
		{
			singleMoveLengthInPixels += 1;
		}
		
		int difference = isFirstMoveAlongsideXAxis() ? nextPosition.x - currentPosition.x : nextPosition.y
				- currentPosition.y;

		if (Math.abs(difference) >= singleMoveLengthInPixels)
		{
			if (isFirstMoveAlongsideXAxis())
			{
				currentPosition.x += difference > 0 ? singleMoveLengthInPixels : -singleMoveLengthInPixels;
			}
			else
			{
				currentPosition.y += difference > 0 ? singleMoveLengthInPixels : -singleMoveLengthInPixels;
			}
		}
		else
		{
			int remainingMoveLeft = singleMoveLengthInPixels - Math.abs(difference);
			
			int remainingDifference = isFirstMoveAlongsideXAxis() ? nextPosition.y - currentPosition.y : nextPosition.x
					- currentPosition.x;
			
			if (remainingDifference < remainingMoveLeft)
			{
				currentPosition.x = nextPosition.x;
				currentPosition.y = nextPosition.y;
			}
			else
			{
				if (isFirstMoveAlongsideXAxis())
				{
					currentPosition.x = nextPosition.x;
					currentPosition.y += remainingDifference > 0 ? remainingMoveLeft : -remainingMoveLeft;
				}
				else
				{
					currentPosition.y = nextPosition.y;
					currentPosition.x += remainingDifference > 0 ? remainingMoveLeft : -remainingMoveLeft;
				}
			}
		}
	}
}
