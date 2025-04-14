package edu.jsu.mcis.cs408.crosswordmagic.model;

import android.content.Context;

import java.util.HashMap;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.DAOFactory;
import edu.jsu.mcis.cs408.crosswordmagic.model.dao.PuzzleDAO;

public class CrosswordMagicModel extends AbstractModel {

    private final int DEFAULT_PUZZLE_ID = 1;

    private Puzzle puzzle;
    private DAOFactory daoFactory;
    private PuzzleDAO puzzleDAO;

    public CrosswordMagicModel(Context context) {
        daoFactory = new DAOFactory(context);
        puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(DEFAULT_PUZZLE_ID);
    }

    public CrosswordMagicModel(Context context, int id) {
        daoFactory = new DAOFactory(context);
        puzzleDAO = daoFactory.getPuzzleDAO();

        this.puzzle = puzzleDAO.find(id);
    }

    public void getTestProperty() {

        String wordCount = (this.puzzle != null ? String.valueOf(puzzle.getSize()) : "none");
        firePropertyChange(CrosswordMagicController.TEST_PROPERTY, null, wordCount);

    }

    public void setGuess(HashMap<String, String> hmap) {
        puzzle.setGuess(hmap);
    }

    public void getGridDimension() {
        if (puzzle != null) {
            Integer[] dimensions = new Integer[]{puzzle.getHeight(), puzzle.getWidth()};
            firePropertyChange(CrosswordMagicController.GRID_DIMENSION_PROPERTY, null, dimensions);
        }
    }

    public void getGridLetters() {
        if (puzzle != null) {
            Character[][] letters = puzzle.getLetters();
            firePropertyChange(CrosswordMagicController.GRID_LETTERS_PROPERTY, null, letters);
        }
    }

    public void getGridNumbers() {
        if (puzzle != null) {
            Integer[][] numbers = puzzle.getNumbers();
            firePropertyChange(CrosswordMagicController.GRID_NUMBERS_PROPERTY, null, numbers);
        }
    }

    public void getClues() {
        if (puzzle != null) {
            String acrossClues = puzzle.getCluesAcross();
            String downClues = puzzle.getCluesDown();

            firePropertyChange(CrosswordMagicController.CLUE_ACROSS_PROPERTY, null, acrossClues);
            firePropertyChange(CrosswordMagicController.CLUE_DOWN_PROPERTY, null, downClues);
        }
    }

    public void getPuzzleList() {
        PuzzleListItem[] result = puzzleDAO.list();
        firePropertyChange(CrosswordMagicController.PUZZLE_LIST_PROPERTY, null, result);
    }

}