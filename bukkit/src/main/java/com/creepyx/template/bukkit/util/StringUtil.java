package com.creepyx.template.bukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.text.Normalizer;
import java.util.Set;

@UtilityClass
public class StringUtil {


	public boolean tooManyCaps(final String message) {
		return getCapsPercentage(message) > 0.7;
	}

	public boolean tooManyCaps(final String message, double percentage) {
		return getCapsPercentage(message) > percentage;
	}

	public boolean isSimilarWord(String word, Set<String> wordList) {
		for (String anotherWord : wordList) {
			word = removeSimilarity(word);
			anotherWord = removeSimilarity(anotherWord);

			int levenshteinDistance = calculateLevenshteinDistance(word, anotherWord);
			int maxLength = Math.max(word.length(), anotherWord.length());

			// Calculate similarity percentage
			double similarity = 1 - (double) levenshteinDistance / maxLength;

			// Check if similarity is greater than or equal to 80%
			if (similarity >= 0.7) {
				return true;
			}
		}
		return false;
	}

	public boolean isSimilarWord(String word, Set<String> wordList, double percentage) {

		for (String anotherWord : wordList) {
			word = removeSimilarity(word);
			anotherWord = removeSimilarity(anotherWord);

			int levenshteinDistance = calculateLevenshteinDistance(word, anotherWord);
			int maxLength = Math.max(word.length(), anotherWord.length());

			// Calculate similarity percentage
			double similarity = 1 - (double) levenshteinDistance / maxLength;

			// Check if similarity is greater than or equal to 80%
			if (similarity >= percentage) {
				return true;
			}
		}
		return false;
	}

	public boolean isSimilarWord(String word, String anotherWord) {
		return isSimilarWord(word, anotherWord, 0.7);
	}

	public boolean isSimilarWord(String word, String anotherWord, double percentage){

		word = removeSimilarity(word);
		anotherWord = removeSimilarity(anotherWord);

		int levenshteinDistance = calculateLevenshteinDistance(word, anotherWord);
		int maxLength = Math.max(word.length(), anotherWord.length());

		// Calculate similarity percentage
		double similarity = 1 - (double) levenshteinDistance / maxLength;

		// Check if similarity is greater than or equal to 80%
		return similarity >= percentage;
	}

	// Helper method to calculate Levenshtein Distance
	public int calculateLevenshteinDistance(String word1, String word2) {
		int[][] dp = new int[word1.length() + 1][word2.length() + 1];

		for (int i = 0; i <= word1.length(); i++) {
			for (int j = 0; j <= word2.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
							Math.min(dp[i - 1][j], dp[i][j - 1]));
				}
			}
		}
		return dp[word1.length()][word2.length()];
	}

	/*
	 * Remove any similarity traits of a message such as removing colors,
	 * lowercasing it, removing diacritic
	 */
	private String removeSimilarity(String message) {

		message = replaceDiacritic(message);
		message = ChatColor.stripColor(message);
		message = message.toLowerCase();

		return message;
	}

	/**
	 * Replace special accented letters with their non-accented alternatives
	 * such as á is replaced by a
	 *
	 * @param message the message which is involved
	 * @return Replaced message
	 */
	public static String replaceDiacritic(final String message) {
		return Normalizer.normalize(message, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	public double getCapsPercentage(final String message) {
		if (message.isEmpty())
			return 0;

		final String[] sentences = message.split(" ");
		StringBuilder messageToCheck = new StringBuilder();
		double upperCount = 0;

		for (final String sentence : sentences)
			messageToCheck.append(sentence).append(" ");

		for (final char ch : messageToCheck.toString().toCharArray())
			if (Character.isUpperCase(ch))
				upperCount++;

		return upperCount / messageToCheck.length();
	}
}
