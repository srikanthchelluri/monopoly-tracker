// sc
// june 2016

import java.util.*;

public class money {
	public static HashMap<String, Integer> players = new HashMap<String, Integer>();
	public static Scanner in = new Scanner(System.in);
	public static int startValue;
	public static boolean showStatus;

	public static void main(String args[]) {
		System.out.println("This is a simple program to track cash flow in Monopoly.");
		setupSettings();
		setupPlayers();
		while (true)
			getAttributes();
	}

	public static void setupSettings() {
		System.out.print("Everyone starts with how much? ");
		try {
			startValue = Integer.parseInt(in.nextLine());
		} catch (Exception e) {
			System.out.println("Not a valid amount.");
			setupSettings();
			return;
		}
		System.out.print("Show holdings on every transaction (yes, no)? ");
		showStatus = in.nextLine().equals("yes") ? true : false;
		if (showStatus) System.out.println("  Will show status of all players on each transaction.");
		else System.out.println("  Use 'status' instead to check holdings.");
	}

	public static void setupPlayers() {
		System.out.println("Enter each player's name, separated by a new line.");
		while (true) {
			String input = in.nextLine();
			if (input.equals(""))
				break;
			String name = input;
			name = pad(name);
			players.put(name, startValue);
		}
		int count = players.keySet().size();
		System.out.println("You have " + count + " players. Bank automatically added (use 'bank').");
		getStatus();
		System.out.println("Type 'quit' to quit; 'status' to see a player's holdings. Let's start!");
		System.out.println();
	}

	public static void getAttributes() {
		System.out.print("Money from? ");
		String from = in.nextLine();
		if (from.equals("quit")) {
			getStatus();
			System.exit(0);
		} else if (from.equals("status")) {
			getPlayerStatus();
			return;
		}
		System.out.print("Money to? ");
		String to = in.nextLine();
		if (to.equals("quit")) {
			getStatus();
			System.exit(0);
		} else if (to.equals("status")) {
			getPlayerStatus();
			return;
		}
		System.out.print("How much? ");
		try {
			int amount = Integer.parseInt(in.nextLine());
			transfer(from, to, amount);
		} catch (Exception e) {
			System.out.println("Not a valid amount.");
			System.out.println();
			getAttributes();
			return;
		}
	}

	public static void transfer(String from, String to, int amount) {
		if (!players.keySet().contains(pad(to)) && !to.equals("bank")) {
			System.out.println("Couldn't find \"to\" player.");
			System.out.println();
			getAttributes();
			return;
		}
		if (!players.keySet().contains(pad(from)) && !from.equals("bank")) {
			System.out.println("Couldn't find \"from\" player.");
			System.out.println();
			getAttributes();
			return;
		}

		if (from.equals("bank") || from.equals("Bank")) {
			int current = players.get(pad(to));
			current += amount;
			players.put(pad(to), current);
			if (showStatus) getStatus();
			System.out.println("Bank transferred $" + amount + " to " + to + ".");
		} else if (to.equals("bank") || to.equals("Bank")) {
			int current = players.get(pad(from));
			current -= amount;
			if (current <= 0) {
				System.out.println(from + " is BANKRUPT.");
			}
			players.put(pad(from), current);
			if (showStatus) getStatus();
			System.out.println(from + " transferred $" + amount + " to bank.");
		} else {
			int fromPlayer = players.get(pad(from));
			fromPlayer -= amount;
			if (fromPlayer <= 0) {
				System.out.println(from + " is BANKRUPT.");
			}
			players.put(pad(from), fromPlayer);
			int toPlayer = players.get(pad(to));
			toPlayer += amount;
			players.put(pad(to), toPlayer);
			if (showStatus) getStatus();
			System.out.println(from + " transferred $" + amount + " to " + to + ".");
		}

		System.out.println();
	}

	public static void getStatus() {
		System.out.println("Current holdings:");
		for (String player : players.keySet())
			System.out.println("  " + player + " \t$" + players.get(player));
	}

	public static void getPlayerStatus() {
		System.out.print("Player? ");
		String p = pad(in.nextLine());
		if (players.keySet().contains(p))
			System.out.println(p.trim() + " has $" + players.get(p) + ".");
		else
			System.out.println("Couldn't find player.");
		System.out.println();
	}

	public static String pad(String input) {
		if (input.length() >= 15)
			return input.substring(0, 15);
		while (input.length() != 15)
			input += " ";
		return input;
	}
}