package DnDWorkshop.SuperInitiative;

import character.Unit;
import junit.framework.TestCase;

public class CharacterTest extends TestCase {
	
	@Test
	public void testStatic() {
		Unit firstUnit = new Unit("Nochmoor",2);
		Unit secondUnit = newUnit ("Lair",20);
		assertFalse(firstUnit.getStatic());
		assertFalse(secondUnit.getStatic());
		
		firstUnit.setStatic(true);
		secondUnit.setStatic(true);
		assertTrue(firstUnit.getStatic());
		assertTrue(secondUnit.getStatic());
		
		firstUnit.setStatic(true);
		assertFalse(firstUnit.getStatic());
	}

}
