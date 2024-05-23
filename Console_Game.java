package ConsoleGame;

/*
 * This file is part of the Bachelor Thesis project called "Analysis and Implementation of Cypriot game Andrin",
 * under the Computer Science department of the  University of Cyprus.
 * 
 * 
 * Author: Styliana Vaki
 * 
 * Created on: Spring Semester 2024
 * 
 * Credits: Please credit the original author if this code is used or edited.
 */

import java.util.Scanner;
//  a1---b1---c1
//  |	  |	   |
//  a2---b2---c2
//  |	  |	   | 
//  a3---b3---c3

//	0---1---2
//	|	|	|
//	3---4---5
//	|	|	| 
//	6---7---8
// adjacent are: 0-1, 1-2, 3-4, 4-5, 6-7, 7-8, 0-3, 3-6, 1-4, 4-7, 2-5, 5-8

//{a1, b1, c1, a2, b2, c2, a3, b3, c3}; (used in code)
//a1 = G[0]
//b1 = G[1]
//c1 = G[2]
//a2 = G[3]
//b2 = G[4]
//c2 = G[5]
//a3 = G[6]
//b3 = G[7]
//c3 = G[8]

public class Console_Game {

	public static int[] board = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	public static String[] encodedboard = { "a1", "b1", "c1", "a2", "b2", "c2", "a3", "b3", "c3" };

	// Print current board on console
	public static void printBoard(int[] G) {
		System.out.println("  " + G[0] + "---" + G[1] + "---" + G[2]);
		System.out.println("  " + "|   |   |");
		System.out.println("  " + G[3] + "---" + G[4] + "---" + G[5]);
		System.out.println("  " + "|   |   |");
		System.out.println("  " + G[6] + "---" + G[7] + "---" + G[8]);

	}

	// Print encoded board on console
	public static void printEncoding() {
		System.out.println("  " + encodedboard[0] + "---" + encodedboard[1] + "---" + encodedboard[2]);
		System.out.println("  " + "|     |    |");
		System.out.println("  " + encodedboard[3] + "---" + encodedboard[4] + "---" + encodedboard[5]);
		System.out.println("  " + "|     |    |");
		System.out.println("  " + encodedboard[6] + "---" + encodedboard[7] + "---" + encodedboard[8]);

	}

	// Given string input of choice return the corresponding index on the array
	public static int returnIndex(String temp) {
		int index = 0;
		if (temp.equals("a1")) {
			index = 0;
		} else if (temp.equals("b1")) {
			index = 1;
		} else if (temp.equals("c1")) {
			index = 2;
		} else if (temp.equals("a2")) {
			index = 3;
		} else if (temp.equals("b2")) {
			index = 4;
		} else if (temp.equals("c2")) {
			index = 5;
		} else if (temp.equals("a3")) {
			index = 6;
		} else if (temp.equals("b3")) {
			index = 7;
		} else if (temp.equals("c3")) {
			index = 8;
		}
		return index;
	}

	// Check if the string input is one of the nine acceptable choices
	public static boolean isValidInput(String temp) {
		return temp.equals("a1") || temp.equals("b1") || temp.equals("c1") || temp.equals("a2") || temp.equals("b2")
				|| temp.equals("c2") || temp.equals("a3") || temp.equals("b3") || temp.equals("c3");

	}

	// Check if chosen position is empty
	public static boolean isPositionEmpty(int temp) {
		if (board[temp] == 0) {
			return true;
		}
		return false;
	}

	// Check if the current board is a win given the player number
	public static boolean isWin(int player) {
		if (board[0] == player && board[1] == player && board[2] == player) {
			return true;
		} else if (board[3] == player && board[4] == player && board[5] == player) {
			return true;
		} else if (board[6] == player && board[7] == player && board[8] == player) {
			return true;
		} else if (board[0] == player && board[3] == player && board[6] == player) {
			return true;
		} else if (board[1] == player && board[4] == player && board[7] == player) {
			return true;
		} else if (board[2] == player && board[5] == player && board[8] == player) {
			return true;
		}

		return false;
	}

	// Check if the board is a win
	public static boolean isWin() {
		if (board[0] != 0 && board[0] == board[1] && board[1] == board[2] && board[0] == board[2]) {
			return true;
		} else if (board[3] != 0 && board[3] == board[4] && board[4] == board[5] && board[3] == board[5]) {
			return true;
		} else if (board[6] != 0 && board[6] == board[7] && board[7] == board[8] && board[6] == board[8]) {
			return true;
		} else if (board[0] != 0 && board[0] == board[3] && board[3] == board[6] && board[0] == board[6]) {
			return true;
		} else if (board[1] != 0 && board[1] == board[4] && board[4] == board[7] && board[1] == board[7]) {
			return true;
		} else if (board[2] != 0 && board[2] == board[5] && board[5] == board[8] && board[2] == board[8]) {
			return true;
		}

		return false;
	}

	// Check for if: position is empty, position is adjacent, player owns the chosen
	// token
	public static boolean isValidMove(int curr, int next, int player) {

		if ((curr == 0 && (next == 1 || next == 3 || next == 4)) || (curr == 1 && (next == 0 || next == 2 || next == 4))
				|| (curr == 2 && (next == 1 || next == 4 || next == 5))
				|| (curr == 3 && (next == 0 || next == 4 || next == 6))
				|| (curr == 4 && (next == 1 || next == 3 || next == 5 || next == 7))
				|| (curr == 5 && (next == 2 || next == 4 || next == 8))
				|| (curr == 6 && (next == 3 || next == 7 || next == 4))
				|| (curr == 7 && (next == 4 || next == 6 || next == 8))
				|| (curr == 8 && (next == 5 || next == 7 || next == 4))) {

			if (board[curr] == player && board[next] == 0) {

				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String userInput;
		int position;
		int newposition;
		int currentposition;
		boolean win = false;

		System.out.println("   Adrin Game");
		System.out.println();
		System.out.println(" Board Encoding ");
		printEncoding();
		System.out.println();

		System.out.println(" Current board");
		printBoard(board);

		// PHASE A
		System.out.println("PHASE 1");
		for (int step = 1; step <= 6; step++) {
			int player;

			// Go to the next turn of next player
			// player 1 plays in steps:1,3,5
			// player 2 plays in steps:2,4,6
			if (step % 2 == 1) {
				player = 1;
			} else {
				player = 2;
			}

			System.out.println("Player " + player + " - Where to put your token? ");
			userInput = scanner.nextLine().trim();

			// Check input validity
			while (!isValidInput(userInput)) {
				System.out.println("Wrong input - Insert choice again: ");
				userInput = scanner.nextLine().trim();

			}

			// Check if board at that index is empty
			position = returnIndex(userInput);
			while (!isPositionEmpty(position)) {
				System.out.println("Position already taken - Insert choice again: ");
				userInput = scanner.nextLine().trim();
				while (!isValidInput(userInput)) {
					System.out.println("Wrong input - Insert choice again: ");
					userInput = scanner.nextLine().trim();

				}
				position = returnIndex(userInput);
			}
			board[position] = player;
			System.out.println(" Current board");
			printBoard(board);

			if (isWin(player)) {
				System.out.println("Game Over - Player " + player + " wins");
				win = true;
				break;
			}
		}

		// Phase 2
		if (!win) {

			System.out.println("PHASE 2");

			int currentPlayer = 1;
			// while(!isWin()) {
			while (!isWin(currentPlayer)) {

				System.out.println("Player " + currentPlayer + " - Insert position of token to move? ");
				userInput = scanner.nextLine().trim();

				// Check input validity
				while (!isValidInput(userInput)) {
					System.out.println("Wrong input - Insert choice again: ");
					userInput = scanner.nextLine().trim();

				}

				currentposition = returnIndex(userInput);

				System.out.println("Player " + currentPlayer + " - Insert where to move? ");
				userInput = scanner.nextLine().trim();

				while (!isValidInput(userInput)) {
					System.out.println("Wrong input - Insert choice again: ");
					userInput = scanner.nextLine().trim();

				}

				newposition = returnIndex(userInput);

				while (!isValidMove(currentposition, newposition, currentPlayer)) {
					System.out.println("Not valid move - not your token or not empty spot or not adjacent");
					System.out.println("Player " + currentPlayer + " - Insert position of token to move? ");
					userInput = scanner.nextLine().trim();

					while (!isValidInput(userInput)) {
						System.out.println("Wrong input - Insert choice again: ");
						userInput = scanner.nextLine().trim();

					}

					currentposition = returnIndex(userInput);

					System.out.println("Player " + currentPlayer + " - Insert where to move? ");
					userInput = scanner.nextLine().trim();

					while (!isValidInput(userInput)) {
						System.out.println("Wrong input - Insert choice again: ");
						userInput = scanner.nextLine().trim();

					}

					newposition = returnIndex(userInput);
				}

				board[newposition] = currentPlayer;
				board[currentposition] = 0;

				System.out.println("Current board");
				printBoard(board);

				if (isWin(currentPlayer)) {
					System.out.println("Game Over - Player " + currentPlayer + " wins");
					win = true;
					break;
				}

				// Go to the next turn of next player
				if (currentPlayer == 1) {
					currentPlayer = 2;
				} else {
					currentPlayer = 1;
				}
			}
		}

		scanner.close();
	}

}
