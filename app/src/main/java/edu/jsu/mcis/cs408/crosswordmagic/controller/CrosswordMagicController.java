package edu.jsu.mcis.cs408.crosswordmagic.controller;

import java.util.HashMap;

public class CrosswordMagicController extends AbstractController {

    public static final String TEST_PROPERTY = "TestProperty";
    public static final String GRID_LETTERS_PROPERTY = "GridLetters";
    public static final String GRID_NUMBERS_PROPERTY = "GridNumbers";
    public static final String GRID_DIMENSION_PROPERTY = "GridDimension";
    public static final String CLUE_ACROSS_PROPERTY = "ClueAcross";
    public static final String CLUE_DOWN_PROPERTY = "CluesDown";
    public static final String CLUES_PROPERTY = "Clues";
    public static final String GUESS_PROPERTY = "Guess";
    public static final String PUZZLE_LIST_PROPERTY = "PuzzleList";
    public static final String PUZZLE_LIST_FROM_API_PROPERTY = "PuzzleListFromAPI";
    public static final String DOWNLOAD_PUZZLE_PROPERTY = "DownloadPuzzle";

    public void getTestProperty(String value) {
        getModelProperty(TEST_PROPERTY);
    }
    public void getGridLetters(String value) {
        getModelProperty(GRID_LETTERS_PROPERTY);
    }
    public void getGridNumbers(String value) {
        getModelProperty(GRID_NUMBERS_PROPERTY);
    }
    public void getGridDimensionProperty(String value) {
        getModelProperty(GRID_DIMENSION_PROPERTY);
    }

    public void getGridLetters() {
        getModelProperty(GRID_LETTERS_PROPERTY);
    }

    public void getGridNumbers() {
        getModelProperty(GRID_NUMBERS_PROPERTY);
    }

    public void getGridDimensions() {
        getModelProperty(GRID_DIMENSION_PROPERTY);
    }
    public void getClueAcross() {
        getModelProperty(CLUE_ACROSS_PROPERTY);
    }
    public void getClueDown() {
        getModelProperty(CLUE_DOWN_PROPERTY);
    }

    public void getClues() {
        getModelProperty(CLUES_PROPERTY);
    }
    public void getPuzzleList() { getModelProperty(PUZZLE_LIST_PROPERTY); }
    public void getPuzzleListFromAPI() { getModelProperty(PUZZLE_LIST_FROM_API_PROPERTY); }
    public void setGuess(HashMap<String, String> hmap) { setModelProperty(GUESS_PROPERTY, hmap); }

    public void getDownloadPuzzle(int puzzleId) {
        getModelProperty(DOWNLOAD_PUZZLE_PROPERTY, puzzleId);
    }
}