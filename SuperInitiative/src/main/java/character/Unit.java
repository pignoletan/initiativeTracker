package character;

public class Unit {

	private String name;
	private int initiative;
	private int rolledInitiative;
	private boolean hasAdvantage;
	private boolean hasDisadvantage;
	private boolean hasHealth;
	private int maxHealth;
	private int curHealth;
	private int tempHitPoints;
	private boolean isStatic;
	
	public Unit(String name,int initiative) {
		this.name = name;
		this.initiative = initiative;
		hasHealth = false;
		hasAdvantage = false;
		hasDisadvantage = false;
		maxHealth = 1; //failsafe to prevent errors
		curHealth = 1;
		isStatic = false;
		rolledInitiative = rollInitiative();
		tempHitPoints = 0;
	}


	public Unit(String name,int initiative,int maxHealth) {
		this.name = name;
		this.initiative = initiative;
		this.maxHealth = maxHealth;
		this.hasHealth = true;
		hasAdvantage = false;
		hasDisadvantage = false;
		curHealth = maxHealth;
		isStatic = false;
		rolledInitiative = rollInitiative();
		tempHitPoints = 0;
	}
	
	public boolean isStatic() {
		return isStatic;
	}


	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public boolean getHasAdvantage() {
		return hasAdvantage;
	}

	public void setHasAdvantage(boolean hasAdvantage) {
		if (hasAdvantage) {hasDisadvantage = false;}
		this.hasAdvantage = hasAdvantage;
	}

	public boolean getHasDisadvantage() {
		return hasDisadvantage;
	}

	public void setHasDisadvantage(boolean hasDisadvantage) {
		if (hasDisadvantage) {hasAdvantage = false;}
		this.hasDisadvantage = hasDisadvantage;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		int diff = maxHealth - this.maxHealth;
		curHealth += diff;
		this.maxHealth = maxHealth;
		hasHealth = true;
	}
	
	public int getCurHealth() {
		return curHealth;
	}
	
	public void inflictDamage(int damage) {
		if (tempHitPoints > 0) {
			tempHitPoints -= damage;
			if (tempHitPoints < 0) {
				curHealth += tempHitPoints;
				tempHitPoints = 0;
			}
		} else {
			curHealth -= damage;
		}
		if (curHealth < 0) {curHealth = 0;}
	}
	
	public void heal(int healAmount) {
		curHealth += healAmount;
		if (curHealth >= maxHealth) {curHealth = maxHealth;}
	}
	
	public int rollInitiative() {
		if (isStatic) {
			rolledInitiative = initiative;
			return initiative;
		}
		int roll = dieRoll(20);
		if (hasAdvantage||hasDisadvantage) {
			int secondRoll = dieRoll(20);
			if ((secondRoll < roll&&hasDisadvantage)
					||(secondRoll > roll&&hasAdvantage)) {
				roll = secondRoll;
			}
		}
		rolledInitiative = roll + initiative;
		return rolledInitiative;
	}
	
	public static int dieRoll(int dieSize) {
		return (int)(Math.random() * 1000000000) % dieSize + 1;
	}
	
	public int getRolledInitiative() {
		return rolledInitiative;
	}


	public boolean getHasHealth() {
		return hasHealth;
	}
	
	public void setTempHitPoints(int tempHitPoints) {
		if (tempHitPoints > this.tempHitPoints) {
			this.tempHitPoints = tempHitPoints;
		}
	}
	
	public int getTempHitPoints() {
		return tempHitPoints;
	}
}
