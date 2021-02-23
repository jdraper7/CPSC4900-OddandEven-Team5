import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JComponent;

/* This is GUI component with a embedded
 * data structure. This structure is a mixture
 * of a queue and a stack
 */
class WasteStack extends JComponent
{
	public Vector<EOCard> v;
	protected int SPREAD = 18;
	protected boolean playStack = false;
	protected int _x = 0;
	protected int _y = 0;

	public WasteStack()
	{
		this.setLayout(null);
		v = new Vector<EOCard>();
	}

	public boolean empty()
	{
		if (v.isEmpty())
			return true;
		else
			return false;
	}

	public void putFirst(EOCard c)
	{
		v.add(0, c);
	}

	public EOCard getFirst()
	{
		if (!this.empty())
		{
			return v.get(0);
		} else
			return null;
	}

	// analogous to peek()
	public EOCard getLast()
	{
		if (!this.empty())
		{
			return v.lastElement();
		} else
			return null;
	}

	// queue-like functionality
	public EOCard popFirst()
	{
		if (!this.empty())
		{
			EOCard c = this.getFirst();
			v.remove(0);
			return c;
		} else
			return null;

	}

	public void push(EOCard c)
	{
		v.add(c);
	}

	public EOCard pop()
	{
		if (!this.empty())
		{
			EOCard c = v.lastElement();
			v.remove(v.size() - 1);
			return c;
		} else
			return null;
	}

	public int showSize()
	{
		return v.size();
	}

	// reverse the order of the stack
	public WasteStack reverse()
	{
		Vector<EOCard> v = new Vector<EOCard>();
		while (!this.empty())
		{
			v.add(this.pop());
		}
		while (!v.isEmpty())
		{
			EOCard c = v.firstElement();
			this.push(c);
			v.removeElement(c);
		}
		return this;
	}

	public void makeEmpty()
	{
		while (!this.empty())
		{
			this.popFirst();
		}
	}

	@Override
	public boolean contains(Point p)
	{
		Rectangle rect = new Rectangle(_x, _y, EOCard.CARD_WIDTH + 10, EOCard.CARD_HEIGHT * 3);
		return (rect.contains(p));
	}

	public void setXY(int x, int y)
	{
		_x = x;
		_y = y;
		// System.out.println("CardStack SET _x: " + _x + " _y: " + _y);
		setBounds(_x, _y, EOCard.CARD_WIDTH + 10, EOCard.CARD_HEIGHT * 3);
	}

	public Point getXY()
	{
		// System.out.println("CardStack GET _x: " + _x + " _y: " + _y);
		return new Point(_x, _y);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		removeAll();
		if (!empty())
		{
			add(EOSolitaire.moveCard(this.getLast(), 1, 1));
		} 
		// else
		// {
		// 	// draw back of card if empty
		// 	Graphics2D g2d = (Graphics2D) g;
		// 	RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT,
		// 			Card.CORNER_ANGLE, Card.CORNER_ANGLE);
		// 	g2d.setColor(Color.LIGHT_GRAY);
		// 	g2d.fill(rect);
		// 	g2d.setColor(Color.black);
		// 	g2d.draw(rect);
		// }
	}
}// END CardStack