import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JComponent;

/* This is GUI component with a embedded
 * data structure. This structure is a mixture
 * of a queue and a stack
 */
class KCardStack extends JComponent
{
	private static final long serialVersionUID = 1L;
	protected final int NUM_CARDS = 52;
	protected Vector<KCard> v;
	protected boolean playStack = false;
	protected int SPREAD = 18;
	protected int _x = 0;
	protected int _y = 0;

	public KCardStack(boolean isDeck)
	{
		this.setLayout(null);
		v = new Vector<KCard>();
		if (isDeck)
		{
			// set deck position
			for (KCard.Suit suit : KCard.Suit.values())
			{
				for (KCard.Value value : KCard.Value.values())
				{
					v.add(new KCard(suit, value));
				}
			}
		} else
		{
			playStack = true;
		}
	}

	public boolean empty()
	{
		if (v.isEmpty())
			return true;
		else
			return false;
	}

	public void putFirst(KCard c)
	{
		v.add(0, c);
	}

	public KCard getFirst()
	{
		if (!this.empty())
		{
			return v.get(0);
		} else
			return null;
	}

	// analogous to peek()
	public KCard getLast()
	{
		if (!this.empty())
		{
			return v.lastElement();
		} else
			return null;
	}

	// queue-like functionality
	public KCard popFirst()
	{
		if (!this.empty())
		{
			KCard c = this.getFirst();
			v.remove(0);
			return c;
		} else
			return null;

	}

	public void push(KCard c)
	{
		v.add(c);
	}

	public KCard pop()
	{
		if (!this.empty())
		{
			KCard c = v.lastElement();
			v.remove(v.size() - 1);
			return c;
		} else
			return null;
	}

	// shuffle the cards
	public void shuffle()
	{
		Vector<KCard> v = new Vector<KCard>();
		while (!this.empty())
		{
			v.add(this.pop());
		}
		while (!v.isEmpty())
		{
			KCard c = v.elementAt((int) (Math.random() * v.size()));
			this.push(c);
			v.removeElement(c);
		}

	}

	public int showSize()
	{
		System.out.println("Stck Size: " + v.size());
		return v.size();
	}

	// reverse the order of the stack
	public KCardStack reverse()
	{
		Vector<KCard> v = new Vector<KCard>();
		while (!this.empty())
		{
			v.add(this.pop());
		}
		while (!v.isEmpty())
		{
			KCard c = v.firstElement();
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
		Rectangle rect = new Rectangle(_x, _y, KCard.CARD_WIDTH + 10, KCard.CARD_HEIGHT * 3);
		return (rect.contains(p));
	}

	public void setXY(int x, int y)
	{
		_x = x;
		_y = y;
		// System.out.println("CardStack SET _x: " + _x + " _y: " + _y);
		setBounds(_x, _y, KCard.CARD_WIDTH + 10, KCard.CARD_HEIGHT * 3);
	}

	public Point getXY()
	{
		// System.out.println("CardStack GET _x: " + _x + " _y: " + _y);
		return new Point(_x, _y);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (playStack)
		{
			removeAll();
			ListIterator<KCard> iter = v.listIterator();
			Point prev = new Point(); // positioning relative to the container
			Point prevWhereAmI = new Point();// abs positioning on the board
			if (iter.hasNext())
			{
				KCard c = iter.next();
				// this origin is point(0,0) inside the cardstack container
				prev = new Point();// c.getXY(); // starting deck pos
				add(KSolitaire.moveCard(c, prev.x, prev.y));
				// setting x & y position
				c.setWhereAmI(getXY());
				prevWhereAmI = getXY();
			} else
			{
				removeAll();
			}

			for (; iter.hasNext();)
			{
				KCard c = iter.next();
				c.setXY(new Point(prev.x, prev.y + SPREAD));
				add(KSolitaire.moveCard(c, prev.x, prev.y + SPREAD));
				prev = c.getXY();
				// setting x & y position
				c.setWhereAmI(new Point(prevWhereAmI.x, prevWhereAmI.y + SPREAD));
				prevWhereAmI = c.getWhereAmI();
			}

		}
	}
}// END CardStack