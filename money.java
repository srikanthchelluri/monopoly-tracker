// sc
// june 2016

import java.util.*;

public class money {
	public static HashMap<String, Integer> players = new HashMap<String, Integer>();
	public static Scanner in = new Scanner(System.in);

	public static void main(String args[]) {
		setupPlayers();
		while (true) {
			getAttributes();
		}
	}

	public static void setupPlayers() {
		System.out.println("This is a simple program to track cash flow in Monopoly.");
		System.out.println("Enter each player's name, separated by a new line.");
		while (true) {
			String input = in.nextLine();
			if (input.equals(""))
				break;
			String name = input;
			name = pad(name);
			players.put(name, 1500);
		}
		int count = players.keySet().size();
		System.out.println("You have " + count + " players.");
		getStatus();
		System.out.println("Let's start!");
		System.out.println();
	}

	public static void getAttributes() {
		System.out.print("Money from? ");
		String from = in.next();
		if (from.equals("quit")) {
			getStatus();
			System.exit(0);
		}
		System.out.print("Money to? ");
		String to = in.next();
		if (to.equals("quit")) {
			getStatus();
			System.exit(0);
		}
		System.out.print("How much? ");
		try {
			int amount = Integer.parseInt(in.next());
			transfer(from, to, amount);
		} catch (Exception e) {
			System.out.println("Not a valid number.");
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

		if (from.equals("bank")) {
			int current = players.get(pad(to));
			current += amount;
			players.put(pad(to), current);
			getStatus();
			System.out.println("Bank tranferred $" + amount + " to " + to + ".");
		} else if (to.equals("bank")) {
			int current = players.get(pad(from));
			current -= amount;
			if (current < 0) {
				System.out.println(from + " is BANKRUPT.");
				System.exit(0);
			}
			players.put(pad(from), current);
			getStatus();
			System.out.println(from + " tranferred $" + amount + " to bank.");
		} else {
			int fromPlayer = players.get(pad(from));
			fromPlayer -= amount;
			if (fromPlayer < 0) {
				System.out.println(from + " is BANKRUPT.");
				System.exit(0);
			}
			players.put(pad(from), fromPlayer);
			int toPlayer = players.get(pad(to));
			toPlayer += amount;
			players.put(pad(to), toPlayer);
			getStatus();
			System.out.println(from + " tranferred $" + amount + " to " + to + ".");
		}

		System.out.println();
	}

	public static void getStatus() {
		System.out.println("Status:");
		for (String player : players.keySet())
			System.out.println("  " + player + " \t$" + players.get(player));
	}

	public static String pad(String input) {
		if (input.length() >= 15)
			return input.substring(0, 15);
		while (input.length() != 15)
			input += " ";
		return input;
	}
}