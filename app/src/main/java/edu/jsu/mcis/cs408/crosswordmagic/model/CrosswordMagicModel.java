package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;

import java.util.HashMap;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.PuzzleDAO;

public class CrosswordMagicModel extends AbstractModel {

    private final int DEFAULT_PUZZLE_ID = 1;

    private Puzzle puzzle;

    public CrosswordMagicModel(Context context) {

        DAOFactory daoFactory = new DAOFactory(context);
        PuzzleDAO puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(DEFAULT_PUZZLE_ID);

    }

    public void getTestProperty() {

        String wordCount = (this.puzzle != null ? String.valueOf(puzzle.getSize()) : "none");
        firePropertyChange(CrosswordMagicController.TEST_PROPERTY, null, wordCount);

    }

    public void setGuessProperty(HashMap<String, String> hmap) {
        puzzle.setGuess(hmap);
    }

    public void getGridDimensionProperty() {
        if (puzzle != null) {
            Integer[] dimensions = new Integer[]{puzzle.getHeight(), puzzle.getWidth()};
            firePropertyChange(CrosswordMagicController.GRID_DIMENSION_PROPERTY, null, dimensions);
        }
    }

    public void getGridLettersProperty() {
        if (puzzle != null) {
            Character[][] letters = puzzle.getLetters();
            firePropertyChange(CrosswordMagicController.GRID_LETTERS_PROPERTY, null, letters);
        }
    }

    public void getGridNumbersProperty() {
        if (puzzle != null) {
            Integer[][] numbers = puzzle.getNumbers();
            firePropertyChange(CrosswordMagicController.GRID_NUMBERS_PROPERTY, null, numbers);
        }
    }

    public void getCluesProperty() {
        if (puzzle != null) {
            String acrossClues = puzzle.getCluesAcross();
            String downClues = puzzle.getCluesDown();

            firePropertyChange(CrosswordMagicController.CLUE_ACROSS_PROPERTY, null, acrossClues);
            firePropertyChange(CrosswordMagicController.CLUE_DOWN_PROPERTY, null, downClues);
        }
    }

}