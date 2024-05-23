package UniqueBoard;

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

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

//0 = NONE
//1 = PLAYER 1
//2 = PLAYER 2

public class Total_Unique_Validations_Final {

	static List<Map.Entry<int[], Integer>> sequences1 = new ArrayList<>(); // sequences with 1 token
	static List<Map.Entry<int[], Integer>> total1 = new ArrayList<>(); // total unique placements for 1 token with
																		// weight
	static List<Map.Entry<int[], Integer>> totalupdated1 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> sequences2 = new ArrayList<>(); // sequences with 2 tokens
	static List<Map.Entry<int[], Integer>> total2 = new ArrayList<>();
	static List<Map.Entry<int[], Integer>> totalupdated2 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> sequences3 = new ArrayList<>(); // sequences with 3 tokens
	static List<Map.Entry<int[], Integer>> total3 = new ArrayList<>();
	static List<Map.Entry<int[], Integer>> totalupdated3 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> sequences4 = new ArrayList<>(); // sequences with 4 tokens
	static List<Map.Entry<int[], Integer>> total4 = new ArrayList<>();
	static List<Map.Entry<int[], Integer>> totalupdated4 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> sequences5 = new ArrayList<>(); // sequences with 5 tokens
	static List<Map.Entry<int[], Integer>> total5 = new ArrayList<>();
	static List<Map.Entry<int[], Integer>> totalupdated5 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> total5nonwinning;
	static List<Map.Entry<int[], Integer>> total5winning = new ArrayList<>();
	static int step5_winning_weights;

	static List<Map.Entry<int[], Integer>> sequences6 = new ArrayList<>(); // sequences with 6 tokens
	static List<Map.Entry<int[], Integer>> total6 = new ArrayList<>();
	static List<Map.Entry<int[], Integer>> totalupdated6 = new ArrayList<>();

	static List<Map.Entry<int[], Integer>> total6nonwinning;
	static List<Map.Entry<int[], Integer>> total6winning = new ArrayList<>();

	static List<int[]> WINS = new ArrayList<>();

	// make an exact copy of the board given as parameter and return it
	public static int[] copyBoard(int[] G) {
		int[] copyG = new int[9];
		for (int i = 0; i < G.length; i++) {
			copyG[i] = G[i];
		}
		return copyG;
	}

	// make every possible board placement with one token (its first players turn so
	// value of token added is 1)
	public static void ProduceToken1(int[] G) {

		for (int i = 0; i < G.length; i++) {
			int[] newG = copyBoard(G);
			if (newG[i] == 0) {
				newG[i] = 1;
				sequences1.add(new SimpleEntry<>(newG, 1));
			}
		}
	}

	// make board placements with token 1 (player 1)
	public static void ProduceTokenPlayer1(List<Map.Entry<int[], Integer>> from, List<Map.Entry<int[], Integer>> to) {
		for (Map.Entry<int[], Integer> entry : from) {
			int[] sequence = entry.getKey();
			for (int i = 0; i < sequence.length; i++) {
				if (sequence[i] == 0) { // Place token only where spot is empty (=0)
					int[] newseq = sequence.clone();
					newseq[i] = 1; // Placing token with value 1
					to.add(new SimpleEntry<>(newseq, entry.getValue())); // SAVING THE NEW SEQUENCE WITH THE
																			// WEIGHT OF THE PARENT
				}
			}
		}

	}

	// make board placements with token 2 (player 2)
	public static void ProduceTokenPlayer2(List<Map.Entry<int[], Integer>> from, List<Map.Entry<int[], Integer>> to) {
		for (Map.Entry<int[], Integer> entry : from) {
			int[] sequence = entry.getKey();
			for (int i = 0; i < sequence.length; i++) {
				if (sequence[i] == 0) { // Place token only where spot is empty (=0)
					int[] newseq = sequence.clone();
					newseq[i] = 2; // Placing token with value 2
					to.add(new SimpleEntry<>(newseq, entry.getValue()));// SAVING THE NEW SEQUENCE WITH THE
																		// WEIGHT OF THE PARENT
				}
			}
		}

	}

	public static int[] R1(int[] G) {

		// ROTATE CORNERS
		int temp = G[4];
		G[4] = G[3];
		G[3] = G[2];
		G[2] = G[1];
		G[1] = temp;

		// ROTATE SIDES
		temp = G[8];
		G[8] = G[7];
		G[7] = G[6];
		G[6] = G[5];
		G[5] = temp;

		return G;
	}

	public static int[] R2(int[] G) {
		int[] temp = R1(G);
		return R1(temp);
	}

	public static int[] R3(int[] G) {
		int[] temp = R2(G);
		return R1(temp);
	}

	public static int[] R4(int[] G) {
		int[] temp = R3(G);
		return R1(temp);
	}

//	public static int[] UM(int[] G) {
//
//		// MIRROR CORNERS
//		int temp1 = G[1];
//		G[1] = G[3];
//		G[3] = temp1;
//
//		// MIRROR SIDES
//		int temp2 = G[5];
//		G[5] = G[6];
//		G[6] = temp2;
//		int temp3 = G[7];
//		G[7] = G[8];
//		G[8] = temp3;
//
//		return G;
//	}

	public static int[] DM(int[] G) {

		// MIRROR CORNERS
		int temp1 = G[2];
		G[2] = G[4];
		G[4] = temp1;

		// MIRROR SIDES
		int temp2 = G[5];
		G[5] = G[8];
		G[8] = temp2;
		int temp3 = G[6];
		G[6] = G[7];
		G[7] = temp3;

		return G;
	}

	// apply all the operators and there combinations
	public static void applyOperations(List<Map.Entry<int[], Integer>> sequences, List<Map.Entry<int[], Integer>> total,
			List<Map.Entry<int[], Integer>> totalupdated) {
		// for (int[] sequence : sequences) {
		for (Map.Entry<int[], Integer> entry : sequences) {
			int[] sequence = entry.getKey();
			int seqindex = sequences.indexOf(entry);
			if (!duplicates(sequence, total, sequences, totalupdated, seqindex)) { // Check if the initial sequence is
																					// in the total
				boolean found = false;

				for (int operator = 1; operator <= 7; operator++) {
					int[] temp = sequence.clone(); // Make a copy of the original sequence

					switch (operator) {
					case 1:
						temp = R1(temp);
						break;
					case 2:
						temp = R2(temp);
						break;
					case 3:
						temp = R3(temp);
						break;
					case 4:
						temp = DM(temp);
						break;
					case 5:
						temp = R1(DM(sequence.clone()));
						break;
					case 6:
						temp = R2(DM(sequence.clone()));
						break;
					case 7:
						temp = R3(DM(sequence.clone()));
						break;
					}

					if (duplicates(temp, total, sequences, totalupdated, seqindex)) { // If the result is true ->
																						// sequence exists
						// in total
						found = true;

						break; // Exit the loop as we found a match
					}
				}

				if (!found) { // If no matching configuration found after applying all operators to the
					// sequence add the original sequence to total with weight 1
					total.add(Map.entry(sequence, 1));
					int newweight = (entry.getValue());
					totalupdated.add(Map.entry(sequence, (newweight)));

				}
			}
		}
	}

	// check foo duplicate boards
	public static boolean duplicates(int[] sequence, List<Map.Entry<int[], Integer>> total,
			List<Map.Entry<int[], Integer>> sequences, List<Map.Entry<int[], Integer>> totalupdated, int seqindex) {
		for (Map.Entry<int[], Integer> entry : total) {
			int[] otherArray = entry.getKey();
			if (compare(sequence, otherArray)) {
				// Found a duplicate
				int index = total.indexOf(entry);
				total.set(index, Map.entry(otherArray, entry.getValue() + 1)); // Increment weight

				int newweight = sequences.get(seqindex).getValue(); // parent weight going to be added to the total
				// System.out.println("Parent Index: " + seqindex + " Parent Weight: " +
				// newweight);
				totalupdated.set(index, Map.entry(otherArray, totalupdated.get(index).getValue() + (newweight)));

				return true;
			}
		}
		return false;
	}

	public static boolean compare(int[] array1, int[] otherArray) {

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != otherArray[i])
				return false;
		}

		return true;
	}

	// print a 1D table given as parameter
	public static void printarray(int[] array) {
		// System.out.println("Resulting board: ");
		for (int i = 0; i < array.length; i++) {
			// System.out.print(array[i] + " ");
			System.out.print(array[i]);
		}
		System.out.println();
//			System.out.println();
	}

	// extract the winning boards
	public static void extractWinning(List<Map.Entry<int[], Integer>> total) {
		total5nonwinning = new ArrayList<>(total);

		Iterator<Map.Entry<int[], Integer>> iterator = total5nonwinning.iterator();
		while (iterator.hasNext()) {
			Map.Entry<int[], Integer> entry = iterator.next();
			int[] board = entry.getKey();
			if (board[1] == 1 && board[2] == 1 && board[5] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			} else if (board[0] == 1 && board[6] == 1 && board[8] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			} else if (board[3] == 1 && board[4] == 1 && board[7] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			} else if (board[1] == 1 && board[4] == 1 && board[8] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			} else if (board[0] == 1 && board[5] == 1 && board[7] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			} else if (board[2] == 1 && board[3] == 1 && board[6] == 1) {
				step5_winning_weights += entry.getValue();
				total5winning.add(entry);
				iterator.remove();
			}
		}

	}

	// extract winning boards, step 6
	public static void extractWinningStep6(List<Map.Entry<int[], Integer>> total) {

		total6nonwinning = new ArrayList<>(total);

		Iterator<Map.Entry<int[], Integer>> iterator = total6nonwinning.iterator();
		while (iterator.hasNext()) {

			Map.Entry<int[], Integer> entry = iterator.next();
			int[] board = entry.getKey();
			if (board[1] == 1 && board[2] == 1 && board[5] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[0] == 1 && board[6] == 1 && board[8] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[3] == 1 && board[4] == 1 && board[7] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[1] == 1 && board[4] == 1 && board[8] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[0] == 1 && board[5] == 1 && board[7] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[2] == 1 && board[3] == 1 && board[6] == 1 && (!total5winning.contains(board))) {
				total6winning.add(entry);
				iterator.remove();
			}

			else if (board[1] == 2 && board[2] == 2 && board[5] == 2) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[0] == 2 && board[6] == 2 && board[8] == 2) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[3] == 2 && board[4] == 2 && board[7] == 2) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[1] == 2 && board[4] == 2 && board[8] == 2) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[0] == 2 && board[5] == 2 && board[7] == 2) {
				total6winning.add(entry);
				iterator.remove();
			} else if (board[2] == 2 && board[3] == 2 && board[6] == 2) {
				total6winning.add(entry);
				iterator.remove();
			}
		}
	}

	// print a whole List of sequences given as parameters
	public static void printList(List<int[]> sequences) {
		for (int i = 0; i < sequences.size(); i++) {
			int[] temp = sequences.get(i); // i -th sequence of the list
			for (int j = 0; j < temp.length; j++) {
				int value = temp[j];
				System.out.print(value);
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();

	}

	// print the total number of unique sequences as well as the total number of
	// weights
	public static int printTotal(List<Map.Entry<int[], Integer>> total) {
		int sum = 0;
		for (Map.Entry<int[], Integer> entry : total) {
			int[] temp = entry.getKey();
			int weight = entry.getValue();

			sum += weight;
		}
		System.out.println("Total: " + sum);
		System.out.println("Unique: " + total.size());
		System.out.println();

		return sum;
	}

	public static void printTotalStep6(List<Map.Entry<int[], Integer>> total) {
		int sum = 0;
		for (Map.Entry<int[], Integer> entry : total) {
			int[] temp = entry.getKey();
			int weight = entry.getValue();

			sum += weight;
		}
		System.out.println("Total: " + (sum + step5_winning_weights));
		System.out.println("Unique: " + total.size() + total5winning);
		System.out.println();
	}

	// print the total number of unique sequences as well as the total number of
	// weights but for the testing version
	public static int printTotalTesting(List<Map.Entry<int[], Integer>> total) {
		int sum = 0;
		for (Map.Entry<int[], Integer> entry : total) {
			int[] temp = entry.getKey();
			int weight = entry.getValue();

			System.out.print("Sequence: ");
			for (int i = 0; i < temp.length; i++) {
				System.out.print(temp[i]);
			}
			System.out.println(" - Weight: " + weight);
			sum += weight;
		}

		System.out.println("Unique: " + total.size());
		// System.out.println("Total Weights: " + totalweight);
		System.out.println("Total Weights: " + sum);
		System.out.println();

		return sum;
	}

	public static void finalUnique() {

		int tot = total1.size() + total2.size() + total3.size() + total4.size() + total5.size() + total6.size()
				+ WINS.size();
		// System.out.println("Number of total unique boards: " + tot);

		int totalwinplayer1 = 0;
		int totalwinplayer2 = 0;

		for (Map.Entry<int[], Integer> entry : total5) {
			int[] board = entry.getKey();
			if (board[1] == 1 && board[2] == 1 && board[5] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}
			if (board[0] == 1 && board[6] == 1 && board[8] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}
			if (board[3] == 1 && board[4] == 1 && board[7] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}
			if (board[1] == 1 && board[4] == 1 && board[8] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}
			if (board[0] == 1 && board[5] == 1 && board[7] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}
			if (board[2] == 1 && board[3] == 1 && board[6] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}

		}

		for (Map.Entry<int[], Integer> entry : total6) {
			int[] board = entry.getKey();
			if (board[1] == 1 && board[2] == 1 && board[5] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			} else if (board[0] == 1 && board[6] == 1 && board[8] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			} else if (board[3] == 1 && board[4] == 1 && board[7] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			} else if (board[1] == 1 && board[4] == 1 && board[8] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			} else if (board[0] == 1 && board[5] == 1 && board[7] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			} else if (board[2] == 1 && board[3] == 1 && board[6] == 1 && (!WINS.contains(board))) {
				totalwinplayer1++;
				WINS.add(board);
			}

			else if (board[1] == 2 && board[2] == 2 && board[5] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			} else if (board[0] == 2 && board[6] == 2 && board[8] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			} else if (board[3] == 2 && board[4] == 2 && board[7] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			} else if (board[1] == 2 && board[4] == 2 && board[8] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			} else if (board[0] == 2 && board[5] == 2 && board[7] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			} else if (board[2] == 2 && board[3] == 2 && board[6] == 2 && (!WINS.contains(board))) {
				totalwinplayer2++;
				WINS.add(board);
			}
		}

		System.out.println("Total winning boards in unique PLAYER1: " + totalwinplayer1);
		System.out.println("Total winning boards in unique PLAYER2: " + totalwinplayer2);

		System.out.println("Total winning boards in unique : " + (totalwinplayer1 + totalwinplayer2));
	}

	// methot to verify the weights for validation
	public static int weightVerification(List<Map.Entry<int[], Integer>> totalFIRST,
			List<Map.Entry<int[], Integer>> totalSECOND) {
		int totalWeight = 0;

		for (Map.Entry<int[], Integer> entrySECOND : totalSECOND) {
			int[] sequenceSECOND = entrySECOND.getKey();
			int weightSECOND = entrySECOND.getValue();
			int index = totalSECOND.indexOf(entrySECOND);
//			System.out.print("Sequence in total2: ");
//			printarray(sequenceSECOND);

			for (Map.Entry<int[], Integer> entryFIRST : totalFIRST) {
				int[] sequenceFIRST = entryFIRST.getKey();
				int weightFIRST = entryFIRST.getValue();
//				System.out.print("Sequence in total1: ");
//				printarray(sequenceFIRST);
//				System.out.println();

				if (totalFIRST == total1 && totalSECOND == total2) {
					if (countMatchingValues(sequenceSECOND, sequenceFIRST) == 1) {
//					System.out.println("HIT");
						int newWeight = weightFIRST * weightSECOND;
						totalSECOND.set(index, Map.entry(sequenceSECOND, newWeight));
						totalWeight += newWeight;

						break;
					}
				} else if (totalFIRST == total2 && totalSECOND == total3) {
					if (countMatchingValues(sequenceSECOND, sequenceFIRST) == 2) {
//						System.out.println("HIT");
						int newWeight = weightFIRST * weightSECOND;
						totalSECOND.set(index, Map.entry(sequenceSECOND, newWeight));
						totalWeight += newWeight;

						break;
					}
				} else if (totalFIRST == total3 && totalSECOND == total4) {
					if (countMatchingValues(sequenceSECOND, sequenceFIRST) == 3) {
//						System.out.println("HIT");
						int newWeight = weightFIRST * weightSECOND;
						totalSECOND.set(index, Map.entry(sequenceSECOND, newWeight));
						totalWeight += newWeight;

						break;
					}
				} else if (totalFIRST == total4 && totalSECOND == total5) {
					if (countMatchingValues(sequenceSECOND, sequenceFIRST) == 4) {
//						System.out.println("HIT");
						int newWeight = weightFIRST * weightSECOND;
						totalSECOND.set(index, Map.entry(sequenceSECOND, newWeight));
						totalWeight += newWeight;

						break;
					}
				} else if (totalFIRST == total5nonwinning && totalSECOND == total6) {
					if (countMatchingValues(sequenceSECOND, sequenceFIRST) == 5) {
//						System.out.println("HIT");
						int newWeight = weightFIRST * weightSECOND;
						totalSECOND.set(index, Map.entry(sequenceSECOND, newWeight));
						totalWeight += newWeight;

						break;
					}
				}
			}
		}

		return totalWeight;
	}

	// Method to count matching values in two sequences
	public static int countMatchingValues(int[] seq1, int[] seq2) {
		int count = 0;
//		printarray(seq1);
//		printarray(seq2);
		for (int i = 0; i < seq1.length; i++) {
			if (seq1[i] == seq2[i] && seq1[i] != 0) {
				count++;
			}

		}
		return count;
	}

	public static void updateTotal5NonWinning() {
		for (Map.Entry<int[], Integer> entry : total5nonwinning) {
			int[] sequenceNonWinning = entry.getKey();
			int index = total5nonwinning.indexOf(entry);
			for (Map.Entry<int[], Integer> entry5 : total5) {
				int[] sequence5 = entry5.getKey();
				if (compare(sequenceNonWinning, sequence5)) {
					// Found matching sequence in total5, update weight in total5nonwinning
					int newweight = entry5.getValue();

					total5nonwinning.set(index, Map.entry(sequenceNonWinning, newweight));
					break; // No need to continue searching
				}
			}
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the first number (0-outputs 1-testing): ");
		int input = scanner.nextInt();

		// run basic program, only necessary outputs
		if (input == 0) {
			long start = System.currentTimeMillis();

			int[] board = new int[9];

			ProduceToken1(board);
			applyOperations(sequences1, total1, totalupdated1);
			// printTotal(total1);

			ProduceTokenPlayer2(total1, sequences2);
			applyOperations(sequences2, total2, totalupdated2);
			// printTotal(total2);

			ProduceTokenPlayer1(totalupdated2, sequences3);
			applyOperations(sequences3, total3, totalupdated3);
			// printTotal(total3);

			ProduceTokenPlayer2(totalupdated3, sequences4);
			applyOperations(sequences4, total4, totalupdated4);
			// printTotal(total4);

			ProduceTokenPlayer1(totalupdated4, sequences5);
			applyOperations(sequences5, total5, totalupdated5);
			// printTotal(total5);

			extractWinning(totalupdated5);

			// ProduceTokenPlayer2(total5nonwinning, sequences6);
			ProduceTokenPlayer2(totalupdated5, sequences6);
			applyOperations(sequences6, total6, totalupdated6);
			// printTotal(total6);
			extractWinningStep6(totalupdated6);

			finalUnique();

//			// WEIGHT VERIFICATION
			System.out.println();
			System.out.println("Step1 weight verification: ");
			printTotal(totalupdated1);
			// printTotalTesting(totalupdated1);

			System.out.println("Step2 weight verification: ");
			printTotal(totalupdated2);
			// printTotalTesting(totalupdated2);

			System.out.println("Step3 weight verification: ");
			printTotal(totalupdated3);

			System.out.println("Step4 weight verification: ");
			printTotal(totalupdated4);

			System.out.println("Step5 weight verification: ");
			System.out.println("	Winning: ");
			int temp1 = printTotal(total5winning);
			System.out.println("	Non Winning: ");
			int temp2 = printTotal(total5nonwinning);
			System.out.println("	Step5 total: ");
			System.out.println("Total: " + (temp1 + temp2));
			System.out.println("Unique: " + (total5winning.size() + total5nonwinning.size()));
			System.out.println();

			System.out.println("Step6 weight verification: ");
			System.out.println("	Winning: ");
			int temp3 = printTotal(total6winning);
			System.out.println("	Non Winning: ");
			int temp4 = printTotal(total6nonwinning);
			System.out.println("	Step6 total: ");
			System.out.println("Total: " + (temp3 + temp4));
			System.out.println("Unique: " + (total6winning.size() + total6nonwinning.size() + total5winning.size()));

//			// WEIGHT VERIFICATION
//			System.out.println();
//			System.out.println("Step1 weight verification: ");
//			printTotal(total1);
//
//			System.out.println("Step2 weight verification: ");
//			weightVerification(total1, total2);
//			printTotal(total2);
//
//			System.out.println("Step3 weight verification: ");
//			weightVerification(total2, total3);
//			printTotal(total3);
//
//			System.out.println("Step4 weight verification: ");
//			weightVerification(total3, total4);
//			printTotal(total4);
//
//			System.out.println("Step5 weight verification: ");
//			weightVerification(total4, total5);
//			printTotal(total5);
//
//			updateTotal5NonWinning();
//			System.out.println("Step6 weight verification: ");
//			weightVerification(total5nonwinning, total6);
//			printTotal(total6);

			System.out.println();
			long end = System.currentTimeMillis();
			float sec = (end - start) / 1000F;
			System.out.println(sec + " seconds");

		}

		// run program for debugging, print the sequences and all the other information
		// needed to debug the process
		else if (input == 1) {
			long start = System.currentTimeMillis();

			int[] board = new int[9];

			ProduceToken1(board);
			applyOperations(sequences1, total1, totalupdated1);
			// printTotalTesting(total1);

			ProduceTokenPlayer2(total1, sequences2);
			applyOperations(sequences2, total2, totalupdated2);
			// printTotalTesting(total2);

			ProduceTokenPlayer1(totalupdated2, sequences3);
			applyOperations(sequences3, total3, totalupdated3);
			// printTotalTesting(total3);

			ProduceTokenPlayer2(totalupdated3, sequences4);
			applyOperations(sequences4, total4, totalupdated4);
			// printTotalTesting(total4);

			ProduceTokenPlayer1(totalupdated4, sequences5);
			applyOperations(sequences5, total5, totalupdated5);
			// printTotalTesting(total5);

			extractWinning(totalupdated5);

			//ProduceTokenPlayer2(total5nonwinning, sequences6);
			ProduceTokenPlayer2(totalupdated5, sequences6);
			applyOperations(sequences6, total6, totalupdated6);
			// printTotalTesting(total6);

			extractWinningStep6(totalupdated6);

			finalUnique();
			System.out.println();
			System.out.println("Winning boards:");
			printList(WINS);

			// WEIGHT VERIFICATION
			System.out.println();
			System.out.println("Step1 weight verification: ");
			// printTotal(totalupdated1);
			printTotalTesting(totalupdated1);

			System.out.println("Step2 weight verification: ");
			// printTotal(totalupdated2);
			printTotalTesting(totalupdated2);

			System.out.println("Step3 weight verification: ");
			// printTotal(totalupdated3);
			printTotalTesting(totalupdated3);

			System.out.println("Step4 weight verification: ");
			// printTotal(totalupdated4);
			printTotalTesting(totalupdated4);

			System.out.println("Step5 weight verification: ");
			System.out.println("	Winning: ");
			int temp1 = printTotalTesting(total5winning);
			System.out.println("	Non Winning: ");
			int temp2 = printTotalTesting(total5nonwinning);
			System.out.println("	Step5 total: ");
			System.out.println("Total: " + (temp1 + temp2));
			System.out.println("Unique: " + (total5winning.size() + total5nonwinning.size()));
			System.out.println();

			System.out.println("Step6 weight verification: ");
			System.out.println("	Winning: ");
			int temp3 = printTotalTesting(total6winning);
			System.out.println("	Non Winning: ");
			int temp4 = printTotalTesting(total6nonwinning);
			System.out.println("	Step6 total: ");
			System.out.println("Total: " + (temp3 + temp4));
			System.out.println("Unique: " + (total6winning.size() + total6nonwinning.size() + total5winning.size()));

//			// WEIGHT VERIFICATION
//			System.out.println("Step1 weight verification: ");
//			printTotalTesting(total1);
//
//			System.out.println("Step2 weight verification: ");
//			weightVerification(total1, total2);
//			printTotalTesting(total2);
//
//			System.out.println("Step3 weight verification: ");
//			weightVerification(total2, total3);
//			printTotalTesting(total3);
//
//			System.out.println("Step4 weight verification: ");
//			weightVerification(total3, total4);
//			printTotalTesting(total4);
//
//			System.out.println("Step5 weight verification: ");
//			weightVerification(total4, total5);
//			printTotalTesting(total5);
//
//			updateTotal5NonWinning();
//			System.out.println("Step6 weight verification: ");
//			weightVerification(total5nonwinning, total6);
//			printTotalTesting(total6);

			System.out.println();
			long end = System.currentTimeMillis();
			float sec = (end - start) / 1000F;
			System.out.println(sec + " seconds");

		} else {
			System.out.println("Incorrect Input");
		}

	}

}
