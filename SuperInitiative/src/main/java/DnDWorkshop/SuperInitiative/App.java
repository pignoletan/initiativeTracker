package DnDWorkshop.SuperInitiative;

import java.util.List;
import java.util.Scanner;

import character.Unit;
import round.Round;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Round round = new Round();
		round = inputUnits(round);
		boolean finished = false;
		Scanner scanner = new Scanner(System.in);
		while (!finished) {
			System.out.println("What do you want to do?");
			System.out.println("'I' = input more characters");
			System.out.println("'N' = start new round of combat");
			System.out.println("'S' = set/unset initiative as static");
			System.out.println("'A' = give/take unit advantage");
			System.out.println("'D' = give/take unit disadvantage");
			System.out.println("'R' = remove a unit");
			System.out.println("'E' = exit program");
			String input = scanner.nextLine();

			if (input.contentEquals("I")) {
				round = inputUnits(round);
			} else if (input.contentEquals("N")) {
				roundMenu(round);
			} else if (input.contentEquals("S")) {
				Unit selectedUnit = selectUnit(round, false, false, true, false);
				if (selectedUnit.isStatic()) {
					selectedUnit.setStatic(false);
				} else {
					selectedUnit.setStatic(true);
				}
			} else if (input.contentEquals("A")) {
				Unit selectedUnit = selectUnit(round, false, false, false, true);
				if (selectedUnit.getHasAdvantage()) {
					selectedUnit.setHasAdvantage(false);
				} else {
					selectedUnit.setHasAdvantage(true);
				}
			} else if (input.contentEquals("D")) {
				Unit selectedUnit = selectUnit(round, false, false, false, true);
				if (selectedUnit.getHasDisadvantage()) {
					selectedUnit.setHasDisadvantage(false);
				} else {
					selectedUnit.setHasDisadvantage(true);
				}
			} else if (input.contentEquals("R")) {
				Unit unit = selectUnit(round,false,false,false,false);
				round.removeCharacter(unit.getName());
			} else if (input.contentEquals("E")) {
				finished = true;
				System.out.println("I take it you got a total party kill?");
			}
		}

	}

	// loop that lets the player input character
	private static Round inputUnits(Round round) {
		boolean finished = false;
		Scanner scanner = new Scanner(System.in);

		while (!finished) {
			System.out.println("Enter a character name, or 'F' for finished");
			String name = scanner.nextLine();
			if (name.equals("F")) {
				finished = true;
				continue;
			}
			boolean validInput = false;
			int initiative = 0;
			while (!validInput) {
				try {
					System.out.println("Insert the character's initiative");
					String input = scanner.nextLine();
					initiative = Integer.parseInt(input);
					validInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Please insert an integer");
				}
			}
			validInput = false;
			int health = 0;
			while (!validInput) {
				try {
					System.out.println("Insert the character's health, or 0 for no health");
					String input = scanner.nextLine();
					health = Integer.parseInt(input);
					validInput = true;
				} catch (NumberFormatException e) {
					System.out.println("Please insert an integer");
				}
			}
			Unit unit;
			if (health > 0) {
				unit = new Unit(name, initiative, health);
			} else {
				unit = new Unit(name, initiative);
			}
			round.addCharacter(unit);
		}
		return round;
	}

	private static void roundMenu(Round round) {
		round.rollAllInitiative();
		boolean finished = false;
		while (!finished) {
			round.printAllUnits(true, true, false, false);
			System.out.println();
			System.out.println("What do you want to do?");
			System.out.println("T: new Turn");
			System.out.println("D: Deal damage");
			System.out.println("H: Heal damage");
			System.out.println("P: Give temporary hit Points");
			System.out.println("E: End round");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();

			if (input.contentEquals("T")) {
				round.rollAllInitiative();
				continue;
			}

			if (input.contentEquals("D")) {
				Unit selectedUnit = selectUnit(round,false,true,false,false);
				boolean _finished = false;
				while (!_finished) {
					try {
						System.out.println("How much damage?");
						String number = scanner.nextLine();
						int damage = Integer.parseInt(number);
						selectedUnit.inflictDamage(damage);
						if (selectedUnit.getCurHealth() == 0) {
							System.out.println("Remove this unit from the round? (Y/*)");
							String _input = scanner.nextLine();
							if (_input.contentEquals("Y")) {
								round.removeCharacter(selectedUnit.getName());
							}
						}
						_finished = true;
					} catch (NumberFormatException e) {
						System.out.println("Please input an integer");
					}
				}
			}

			if (input.contentEquals("H")) {
				Unit selectedUnit = selectUnit(round,false,true,false,false);
				boolean _finished = false;
				while (!_finished) {
					try {
						System.out.println("How much healing?");
						String number = scanner.nextLine();
						int healAmount = Integer.parseInt(number);
						selectedUnit.heal(healAmount);
						_finished = true;
					} catch (NumberFormatException e) {
						System.out.println("Please input an integer");
					}
				}
			}

			if (input.contentEquals("P")) {
				Unit selectedUnit = selectUnit(round,false,true,false,false);
				boolean _finished = false;
				while (!_finished) {
					try {
						System.out.println("How many temp hit points?");
						String number = scanner.nextLine();
						int tempHitPoints = Integer.parseInt(number);
						selectedUnit.setTempHitPoints(tempHitPoints);
						_finished = true;
					} catch (NumberFormatException e) {
						System.out.println("Please input an integer");
					}
				}
			}

			if (input.contentEquals("E")) {
				finished = true;
			}
		}
	}

	private static Unit selectUnit(Round round, boolean showInitiative, boolean showHealth, boolean showStatic,
			boolean showAdvantage) {
		Unit unit = null;
		boolean finished = false;
		Scanner scanner = new Scanner(System.in);
		while (!finished) {
			System.out.println("For which unit?");
			for (int i = 0; i < round.getParticipants().size(); i++) {
				System.out.println((i + 1) + ". " +
			round.getParticipants().get(i).getName());
			}
			try {
				String input = scanner.nextLine();
				int number = Integer.parseInt(input);
				List<Unit> participants = round.getParticipants();
				if (number < 1||number > participants.size()) {
					System.out.println("Pick a number from the list you fool");
					continue;
				}
				unit = participants.get(number - 1);
				finished = true;
			} catch (NumberFormatException e) {
				System.out.println("Please enter an integer");
			}
		}
		return unit;
	}

}
