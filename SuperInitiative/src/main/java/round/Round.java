package round;

import java.util.ArrayList;
import java.util.List;

import character.Unit;

public class Round {

	private List<Unit> participants;

	public Round() {
		participants = new ArrayList<Unit>();
	}

	public void addCharacter(Unit unit) {
		participants.add(unit);
	}

	public List<Unit> getParticipants() {
		return participants;
	}

	public void removeCharacter(String name) {
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).getName().equals(name)) {
				participants.remove(i);
				return;
			}
		}
	}
	
	public void removeCharacter(int index) {
		if (index >= 0&&index < participants.size()) {
			participants.remove(index);
		}
	}

	//Given the name of a unit, returns it. If the unit is not found, throws a null pointer
	public Unit getUnitByName(String name) {
		for (int i = 0; i < participants.size(); i++) {
			Unit unit = participants.get(i);
			if (unit.getName().contentEquals(name)) {
				return unit;
			}
		}
		throw new NullPointerException();
	}

	public List<Unit> orderByInitiative() {
		List<Unit> order = new ArrayList<Unit>();
		for (int i = 0; i < participants.size(); i++) {
			Unit unit = participants.get(i);
			int initiative = unit.getRolledInitiative();
			for (int j = 0; j <= order.size(); j++) {
				if (j == order.size()) {
					order.add(unit);
					j = 2000;
				} else {
					if (initiative > order.get(j).getRolledInitiative()) {
						order.add(j, unit);
						j = 2000;
					}
				}
			}
		}
		return order;
	}
	
	public void printAllUnits(boolean showInitiative,boolean showHealth,
			boolean showStatic, boolean showAdvantage) {
		List<Unit> orderedUnits = orderByInitiative();
		for (int i = 0; i < orderedUnits.size(); i++) {
			Unit unit = orderedUnits.get(i);
			String output = (i + 1) + ". " + unit.getName();
			if (showInitiative) {output += " " + unit.getRolledInitiative();}
			if (unit.getHasHealth()&&showHealth) {
				output += " " + unit.getCurHealth();
				if (unit.getTempHitPoints() > 0) {
					output += "(+" + unit.getTempHitPoints() + ")";
				}
				output += "/" + unit.getMaxHealth();
			}
			if (unit.isStatic()&&showStatic) {output += " S";}
			if (showAdvantage) {
				if (unit.getHasAdvantage()) {output += " Adv";}
				if (unit.getHasDisadvantage()) {output += " Dis";} 
			}
			System.out.println(output);
		}
	}
	
	public void rollAllInitiative() {
		for (Unit unit : participants) {
			unit.rollInitiative();
		}
	}
}
