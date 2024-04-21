import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class CrosswordPuzzle {
    private final char[][] board;
    private final int size = 20; // Puzzle size
    private final Map<Character, List<Coordinate>> letterPlacements;
    private boolean isFirstWord = true;

    public CrosswordPuzzle() {
        this.board = new char[size][size];
        this.letterPlacements = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }

    public boolean addWords(List<String> words) {
        boolean isFirstWord = true;
        for (String word : words) {
            if (isFirstWord) {
                // Place the first word at the center
                if (!placeWord(word, size / 2, size / 2, true)) {
                    return false; // Failed to place the first word
                }
                isFirstWord = false;
            } else {
                if (!placeNewWord(word)) {
                    return false; // Failed to place subsequent word
                }
            }
        }
        return true; // Successfully placed all words
    }

    private boolean placeNewWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letterPlacements.containsKey(letter)) {
                for (Coordinate coord : letterPlacements.get(letter)) {
                    // Try to place the word vertically or horizontally
                    if (checkAndPlaceWord(word, coord, true) || checkAndPlaceWord(word, coord, false)) {
                        return true;
                    }
                }
            }
        }

        // If no matching letter or placement found, try placing the word anywhere on the board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '.' && (placeWord(word, i, j, true) || placeWord(word, i, j, false))) {
                    return true;
                }
            }
        }

        // If no place found
        return false;
    }

    private boolean checkAndPlaceWord(String word, Coordinate coord, boolean horizontal) {
        int x = coord.x;
        int y = coord.y;
        int wordIndex = word.indexOf(board[y][x]);

        // Calculate the starting point
        int startX = horizontal ? x - wordIndex : x;
        int startY = horizontal ? y : y - wordIndex;

        return placeWord(word, startX, startY, horizontal);
    }

    private boolean placeWord(String word, int startX, int startY, boolean horizontal) {
        int x = startX;
        int y = startY;

        if (horizontal) {
            if (x < 0 || x + word.length() > size) return false;
            for (int i = 0; i < word.length(); i++) {
                if (board[y][x + i] != '.' && board[y][x + i] != word.charAt(i)) {
                    return false; // Conflict
                }
            }
            for (int i = 0; i < word.length(); i++) {
                board[y][x + i] = word.charAt(i);
                letterPlacements.putIfAbsent(word.charAt(i), new ArrayList<>());
                letterPlacements.get(word.charAt(i)).add(new Coordinate(x + i, y));
            }
        } else {
            if (y < 0 || y + word.length() > size) return false;
            for (int i = 0; i < word.length(); i++) {
                if (board[y + i][x] != '.' && board[y + i][x] != word.charAt(i)) {
                    return false; // Conflict
                }
            }
            for (int i = 0; i < word.length(); i++) {
                board[y + i][x] = word.charAt(i);
                letterPlacements.putIfAbsent(word.charAt(i), new ArrayList<>());
                letterPlacements.get(word.charAt(i)).add(new Coordinate(x, y + i));
            }
        }

        return true;
    }

    public boolean addWord(String word) {
        if (isFirstWord) {
            // Place the first word at the center
            boolean placed = placeWord(word, size / 2 - word.length() / 2, size / 2, true);
            isFirstWord = false;
            return placed;
        } else {
            return placeNewWord(word);
        }
    }

    public void printPuzzle() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Coordinate {
        int x, y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}