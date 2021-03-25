import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.Point;
import org.junit.jupiter.api.Test;


class EOWasteStackTest 
{

	@Test
	/*
	 * Test for constructor EOWasteStack()
	 * Create wasteStack object and pointer. See if object creation is successful and pointer points to object on heap
	 * Success means object creation working properly
	 */
	void testEOWasteStack() 
	{
		// create solitaire test object
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOWasteStack wastePointer = wasteStackTest;
		
		// If pointer is not null and points to an object then this assert will pass, since the constructor was successful in creation and pointer
		// points to the same object as whats on the heap
		assertSame(wastePointer, wasteStackTest);
	}
	
	@Test
	/*
	 * Test for empty()
	 * Create wasteStack object. Call empty() on wastestack with no cards added into pile
	 * Success means before cards are added return is True
	 * Success also means after adding cards return is False
	 */
	void testEmpty() 
	{
		// wasteStackTest creation
		EOWasteStack wasteStackTest = new EOWasteStack();
		
		// expected value should be true as when waste stack is created variable 'v' is empty
		assertTrue(wasteStackTest.empty());

		// add some cards into wasteStack
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		
		// push cards onto stack
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		
		// check if return is false
		assertFalse(wasteStackTest.empty());
	}
	
	@Test
	/*
	 * Test for PutFirst()
	 * Create wastestackObject. Add cards onto stack. Check that first card is our expected card
	 * Success means putFirst() added card into front of stack
	 */
	void testPutFirst() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		
		// add 2 cards into stack. Second card should be put ahead of first card
		wasteStackTest.putFirst(cardTest);
		wasteStackTest.putFirst(cardTest2);
		
		// cardTest2 should be the first card in stack
		assertSame(cardTest2, wasteStackTest.getFirst());
	}

	@Test
	/*
	 * Testing for GetFirst()
	 * Create stack, add cards onto stack. Check that cardtest2 is the first card in stack. Empty the stack. Ensure that getFirst() returns NULL
	 * Success means assertSame was correct and assertNull was also correct
	 */
	void testGetFirst() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		
		// add 2 cards into stack. Second card should be put ahead of first card
		wasteStackTest.putFirst(cardTest);
		wasteStackTest.putFirst(cardTest2);
		
		// cardTest2 should be the first card in stack
		assertSame(cardTest2, wasteStackTest.getFirst());
		
		// now to test the null path I'll empty the wasteStack then check for null
		wasteStackTest.makeEmpty();
		
		// check that the first card is null
		assertNull(wasteStackTest.getFirst());
	}

	@Test
	/*
	 * Testing for GetLast()
	 * Create stack, add cards onto stack. Check that cardTest is last card in stack. Empty the stack. Ensure that GetLast() returns NULL
	 * Success means assertSame was correct and assertNull was also correct
	 */
	void testGetLast() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		
		// add 2 cards into stack. Second card should be put ahead of first card
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		
		// cardTest2 should be the first card in stack
		assertSame(cardTest2, wasteStackTest.getLast());
		
		// now to test the null path I'll empty the wasteStack then check for null
		wasteStackTest.makeEmpty();
		
		// check that the first card is null
		assertNull(wasteStackTest.getLast());
	}

	@Test
	/*
	 * Testing for PopFirst()
	 * Create stack, add cards onto stack. Check that the card returned is the first card in stack. Empty the stack. Ensure that PopFirst() returns NULL
	 * Success means assertSame was correct and assertNull was also correct
	 */
	void testPopFirst() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		
		// add 2 cards into stack. Second card should be put ahead of first card
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		
		// cardTest should be the first card in stack that is popped
		assertSame(cardTest, wasteStackTest.popFirst());
		
		// now to test the null path I'll empty the wasteStack then check for null
		wasteStackTest.makeEmpty();
		
		// check that the first card is null
		assertNull(wasteStackTest.popFirst());
	}

	@Test
	/*
	 * Testing for Push()
	 * Create stack, add cards onto stack. Push cards into order then check that stack empty() returns false
	 * Success means push added cards successfuly, success also means emptying the stack worked successfuly
	 */
	void testPush() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		EOCard cardTest3 = new EOCard();
		
		// add cards using push
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		wasteStackTest.push(cardTest3);
		
		// check that there are cards in the stack
		assertFalse(wasteStackTest.empty());
		
		// empty the stack
		wasteStackTest.makeEmpty();
		
		// empty the stack and check that empty is now true
		assertTrue(wasteStackTest.empty());
	}

	@Test
	/*
	 * Testing for Pop()
	 * Create stack, add cards onto stack. Push cards onto stack. Then pop last card and see if its the last card we added manually
	 * Success means pop() properly removed the last card in stack, success also means pop() returns null if no cards in stack
	 */
	void testPop() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		EOCard cardTest3 = new EOCard();
		
		// add cards using push
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		wasteStackTest.push(cardTest3);
		
		// pop cardTest3 off of deck
		assertSame(cardTest3, wasteStackTest.pop());
		
		// now to test the null path I'll empty the wasteStack then check for null
		wasteStackTest.makeEmpty();
		
		// check that the first card is null
		assertNull(wasteStackTest.pop());
	}

	@Test
	/*
	 * Testing for ShowSize()
	 * Create stack, add 3 cards onto stack. Call showsize() and ensure its equal to 3
	 * Success means showsize correctly counted size of stack
	 */
	void testShowSize() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		EOCard cardTest3 = new EOCard();
		
		// add cards using push
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		wasteStackTest.push(cardTest3);
		
		// expected size is 3
		assertEquals(3, wasteStackTest.showSize());
	}

	@Test
	/*
	 * Testing for Reverse()
	 * Create stack, add cards into stack. Call Reverse() then test if last card added is now first card
	 * Success means Reverse() flipped order correctly
	 */
	void testReverse() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		EOCard cardTest3 = new EOCard();
		
		// add cards using push
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		wasteStackTest.push(cardTest3);
		
		// reverse the order
		wasteStackTest.reverse();
		
		// cardTest3 should be the first card now
		assertSame(cardTest3, wasteStackTest.getFirst());
	}

	@Test
	/*
	 * Testing for MakeEmpty()
	 * Create stack, add cards into stack. Call MakeEmpty() then test to see if return of empty() is null
	 * Success means MakeEmpty() correctly cleared out stack
	 */
	void testMakeEmpty() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		EOCard cardTest = new EOCard();
		EOCard cardTest2 = new EOCard();
		EOCard cardTest3 = new EOCard();
		
		// add cards using push
		wasteStackTest.push(cardTest);
		wasteStackTest.push(cardTest2);
		wasteStackTest.push(cardTest3);
		
		// empty them out
		wasteStackTest.makeEmpty();
		
		// the size should now be 0
		assertTrue(wasteStackTest.empty());
	}

	@Test
	/*
	 * Testing for Contains()
	 * Create stack, create point. Pass point object as argument into contains() method. Test to see if true value is returned
	 * Success means contains() properly identified if stack contains the point passed as argument
	 */
	void testContainsPoint() 
	{
		// Create testing objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		Point pointTest = new Point();
		
		// check that the output is true from the return of contains()
		assertTrue(wasteStackTest.contains(pointTest));
	}

	@Test
	/*
	 * Testing for SetXY()
	 * Create stack, set the x and y of the stack to 5 and 7 respectively. Test that the points equal the supplied argument
	 * Success means SetXY() correctly added the values of 5 and 7 to the x and y variables.
	 */
	void testSetXY() 
	{
		// waste stack testing object
		EOWasteStack wasteStackTest = new EOWasteStack();
		
		// set the x and y
		wasteStackTest.setXY(5, 7);
		
		// check that the x and y location is now 5 and 7
		assertEquals(5, wasteStackTest._x);
		assertEquals(7, wasteStackTest._y);
	}

	@Test
	/*
	 * Testing for GetXY()
	 * Create stack, create a new point. Set the x and y of the stack and the point to 5 and y. test that the x and y of each object are equal
	 * Success means GetXY() correctly returned a new point object with the same x and y values as what was manually assigned.
	 */
	void testGetXY() 
	{
		// waste stack testing object
		EOWasteStack wasteStackTest = new EOWasteStack();
		
		// set the x and y
		wasteStackTest.setXY(5, 7);
		
		// now create a point with the given x and y. then compare the two objects
		Point pointTest = new Point(5, 7);
		
		// check to see that the points match up
		assertEquals(pointTest.x, wasteStackTest.getXY().x);
		assertEquals(pointTest.y, wasteStackTest.getXY().y);
	}

	@Test
	/*
	 * Testing for PaintComponent()
	 * Create stack, create grahics object which points to stacks graphics component. Pass graphics component into paintComponent() method.
	 * Success means argument passing was successful
	 */
	void testPaintComponentGraphics() 
	{
		// create objects
		EOWasteStack wasteStackTest = new EOWasteStack();
		Graphics g = wasteStackTest.getGraphics();
		
		// pass the graphics component into the wasteStackTests paintComponent(g) method
		wasteStackTest.paintComponent(g);
		
		// I do a truth assert in that if the wasteStackTest was successfuly created, the graphics object points to the wassteStackTest's graphics component
		// and finally the paintComponent(g) accepts the graphics object g into its method then the entire method is successfly run. So running in sequential
		// order the program couldn't get to the assert method below unless everything above worked properly.
		assertEquals(1, 1);
	}

}
