package edu.jsu.mcis.cs408.crosswordmagic.controller;

import java.util.HashMap;

public class CrosswordMagicController extends AbstractController {

    public static final String TEST_PROPERTY = "TestProperty";
    public static final String GRID_LETTERS_PROPERTY = "GridLettersProperty";
    public static final String GRID_NUMBERS_PROPERTY = "GridNumbersProperty";
    public static final String GRID_DIMENSION_PROPERTY = "GridDimensionProperty";
    public static final String CLUE_ACROSS_PROPERTY = "ClueAcrossProperty";
    public static final String CLUE_DOWN_PROPERTY = "CluesDownProperty";
    public static final String CLUES_PROPERTY = "CluesProperty";
    public static final String GUESS_PROPERTY = "GuessProperty";

    public void getTestProperty(String value) {
        getModelProperty(TEST_PROPERTY);
    }
    public void getGridLettersProperty(String value) {
        getModelProperty(GRID_LETTERS_PROPERTY);
    }
    public void getGridNumbersProperty(String value) {
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
    public void getClueAcrossProperty() {
        getModelProperty(CLUE_ACROSS_PROPERTY);
    }
    public void getClueDownProperty() {
        getModelProperty(CLUE_DOWN_PROPERTY);
    }

    public void getCluesProperty() {
        getModelProperty(CLUES_PROPERTY);
    }
    public void setGuessProperty(HashMap<String, String> hmap) { setModelProperty(GUESS_PROPERTY, hmap);}

}