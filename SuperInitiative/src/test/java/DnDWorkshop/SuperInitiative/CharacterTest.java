package DnDWorkshop.SuperInitiative;

import org.junit.Test;

import character.Unit;
import junit.framework.TestCase;

public class CharacterTest extends TestCase {

	@Test
	public void testStatic() {
		Unit firstUnit = new Unit("Nochmoor",2);
		Unit secondUnit = new Unit ("Lair",20);
		assertFalse(firstUnit.isStatic());
		assertFalse(secondUnit.isStatic());
		
		firstUnit.setStatic(true);
		secondUnit.setStatic(true);
		assertTrue(firstUnit.isStatic());
		assertTrue(secondUnit.isStatic());
		
		firstUnit.setStatic(false);
		assertFalse(firstUnit.isStatic());
		
		for (int i = 0; i < 1000; i++) {
			assertEquals("Static initiative is still rolling", secondUnit.rollInitiative(), 20);
		}
	}
	
	@Test
	public void testAdvantage() {
		Unit firstUnit = new Unit("Owain",0);
		Unit secondUnit = new Unit("Castor",0);
		
		firstUnit.setHasAdvantage(true);
		secondUnit.setHasDisadvantage(true);
		
		assertTrue(firstUnit.getHasAdvantage());
		assertFalse(firstUnit.getHasDisadvantage());
		assertFalse(secondUnit.getHasAdvantage());
		assertTrue(secondUnit.getHasDisadvantage());
		
		assertTrue(testRandom(firstUnit,14));
		assertTrue(testRandom(secondUnit,6));
	}
	
	@Test
	public void testInitiative() {
		Unit firstUnit = new Unit("Me",0);
		Unit secondUnit = new Unit ("You",-10);
		Unit thirdUnit = new Unit ("Us",5);
		
		assertTrue(testRandom(firstUnit,11));
		assertTrue(testRandom(secondUnit,1));
		assertTrue(testRandom(thirdUnit,16));
	}
	
	private boolean testRandom(Unit unit, int expectedResult) {
		boolean resultIsCorrect = true;
		int aggregate = 0;
		for (int i = 0; i < 1000; i++) {
			aggregate += unit.rollInitiative();
		}
		aggregate /= 1000;
		if (aggregate > expectedResult + 1||aggregate < expectedResult - 1) {
			resultIsCorrect = false;
		}
		return resultIsCorrect;
	}
	
}
