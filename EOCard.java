import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class EOCard extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static enum Value
	{
		ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}

	public static enum Suit
	{
		SPADES, CLUBS, DIAMONDS, HEARTS
	}

	private Suit _suit;

	private Value _value;

	private Boolean _faceup;

	private Point _location; // location relative to container

	private Point whereAmI; // used to create abs postion rectangle for contains
	// functions

	private int x; // used for relative positioning within CardStack Container
	private int y;

	private final int x_offset = 10;
	private final int y_offset = 20;
	private final int new_x_offset = x_offset + (CARD_WIDTH - 30);
	final static public int CARD_HEIGHT = 150;

	final static public int CARD_WIDTH = 100;

	final static public int CORNER_ANGLE = 25;

	EOCard(Suit suit, Value value)
	{
		_suit = suit;
		_value = value;
		_faceup = false;
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();
	}

	EOCard()
	{
		_suit = EOCard.Suit.CLUBS;
		_value = EOCard.Value.ACE;
		_faceup = false;
		_location = new Point();
		x = 0;
		y = 0;
		_location.x = x;
		_location.y = y;
		whereAmI = new Point();
	}

	public Suit getSuit()
	{
		switch (_suit)
		{
			case HEARTS:
				break;
			case DIAMONDS:
				break;
			case SPADES:
				break;
			case CLUBS:
				break;
		}
		return _suit;
	}

	public Value getValue()
	{
		switch (_value)
		{
			case ACE:
				break;
			case TWO:
				break;
			case THREE:
				break;
			case FOUR:
				break;
			case FIVE:
				break;
			case SIX:
				break;
			case SEVEN:
				break;
			case EIGHT:
				break;
			case NINE:
				break;
			case TEN:
				break;
			case JACK:
				break;
			case QUEEN:
				break;
			case KING:
				break;
		}
		return _value;
	}

	public void setWhereAmI(Point p)
	{
		whereAmI = p;
	}

	public Point getWhereAmI()
	{
		return whereAmI;
	}

	public Point getXY()
	{
		return new Point(x, y);
	}

	public Boolean getFaceStatus()
	{
		return _faceup;
	}

	public void setXY(Point p)
	{
		x = p.x;
		y = p.y;

	}

	public void setSuit(Suit suit)
	{
		_suit = suit;
	}

	public void setValue(Value value)
	{
		_value = value;
	}

	public EOCard setFaceup()
	{
		_faceup = true;
		return this;
	}

	public EOCard setFacedown()
	{
		_faceup = false;
		return this;
	}

	@Override
	public boolean contains(Point p)
	{
		Rectangle rect = new Rectangle(whereAmI.x, whereAmI.y, EOCard.CARD_WIDTH, EOCard.CARD_HEIGHT);
		return (rect.contains(p));
	}

	private void drawSuit(Graphics2D g, String suit, Color color)
	{
		g.setColor(color);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.drawString(suit, _location.x + x_offset, _location.y + y_offset);
		g.drawString(suit, _location.x + new_x_offset, _location.y + y_offset + CARD_HEIGHT - 25);
	}

	private void drawValue(Graphics2D g, String value)
	{
		g.drawString(value, _location.x + new_x_offset, _location.y + y_offset);
		g.drawString(value, _location.x + x_offset, _location.y + CARD_HEIGHT - 5);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		RoundRectangle2D rect2 = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
				CORNER_ANGLE, CORNER_ANGLE);
		g2d.setColor(Color.WHITE);
		g2d.fill(rect2);
		g2d.setColor(Color.black);
		g2d.draw(rect2);
		// DRAW THE CARD SUIT AND VALUE IF FACEUP
		if (_faceup)
		{
			switch (_suit)
			{
				case HEARTS:
					drawSuit(g2d, "\u2665", Color.RED);
					break;
				case DIAMONDS:
					drawSuit(g2d, "\u2666", Color.RED);
					break;
				case SPADES:
					drawSuit(g2d, "\u2660", Color.BLACK);
					break;
				case CLUBS:
					drawSuit(g2d, "\u2663", Color.BLACK);
					break;
			}
			switch (_value)
			{
				case ACE:
					drawValue(g2d, "A");
					break;
				case TWO:
					drawValue(g2d, "2");
					break;
				case THREE:
					drawValue(g2d, "3");
					break;
				case FOUR:
					drawValue(g2d, "4");
					break;
				case FIVE:
					drawValue(g2d, "5");
					break;
				case SIX:
					drawValue(g2d, "6");
					break;
				case SEVEN:
					drawValue(g2d, "7");
					break;
				case EIGHT:
					drawValue(g2d, "8");
					break;
				case NINE:
					drawValue(g2d, "9");
					break;
				case TEN:
					drawValue(g2d, "10");
					break;
				case JACK:
					drawValue(g2d, "J");
					break;
				case QUEEN:
					drawValue(g2d, "Q");
					break;
				case KING:
					drawValue(g2d, "K");
					break;
			}
		} else
		{
			// DRAW THE BACK OF THE CARD IF FACEDOWN
			RoundRectangle2D rect = new RoundRectangle2D.Double(_location.x, _location.y, CARD_WIDTH, CARD_HEIGHT,
					CORNER_ANGLE, CORNER_ANGLE);

			int size = 20;
			Color rightTop = new Color(255, 254, 55);
			Color leftBottom = new Color(100, 0, 255);
			Color leftTop= new Color(237, 0, 3);
			Color rightBottom = new Color(237, 0, 90);
			GradientPaint twoColorGradient = new GradientPaint(
					size, 0f, rightTop, 0, 150, leftBottom);

			float radius = size-(size/4);
			float[] dist = {0f, 1.0f};
			Point2D center = new Point2D.Float(0f, 0f);
			Color noColor = new Color(0f, 0f, 0f, 0f);
			Color[] colors = {leftTop, noColor};
			RadialGradientPaint thirdColor = new RadialGradientPaint(center, 25, dist, colors);


			center = new Point2D.Float(size, size);
			Color[] colors2 = {rightBottom, noColor};
			RadialGradientPaint fourthColor = new RadialGradientPaint(center, 150, dist, colors2);

			g2d.setPaint(twoColorGradient);
			g2d.fill(rect);

			g2d.setPaint(thirdColor);
			g2d.fill(rect);

			g2d.setPaint(fourthColor);
			g2d.fill(rect);

			g2d.fill(rect);
			g2d.setColor(Color.black);
			g2d.draw(rect);
		}

	}

}// END Card