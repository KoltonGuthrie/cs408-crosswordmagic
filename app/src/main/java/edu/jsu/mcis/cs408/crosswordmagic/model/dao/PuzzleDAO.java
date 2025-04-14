package edu.jsu.mcis.cs408.crosswordmagic.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import edu.jsu.mcis.cs408.crosswordmagic.model.Puzzle;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;
import edu.jsu.mcis.cs408.crosswordmagic.model.Word;
import edu.jsu.mcis.cs408.crosswordmagic.model.WordDirection;

public class PuzzleDAO {

    private final DAOFactory daoFactory;

    PuzzleDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /* add a new puzzle entry to the database */

    public int create(Puzzle newPuzzle) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        int result = create(db, newPuzzle);
        db.close();
        return result;

    }

    public int create(SQLiteDatabase db, Puzzle newPuzzle) {

        int key = 0;

        /* use this method if there IS already a SQLiteDatabase open */

        String name = daoFactory.getProperty("sql_field_name");
        String description = daoFactory.getProperty("sql_field_description");
        String height = daoFactory.getProperty("sql_field_height");
        String width = daoFactory.getProperty("sql_field_width");

        ContentValues values = new ContentValues();
        values.put(name, newPuzzle.getName());
        values.put(description, newPuzzle.getDescription());
        values.put(height, newPuzzle.getHeight());
        values.put(width, newPuzzle.getWidth());

        key = (int)db.insert(daoFactory.getProperty("sql_table_puzzles"), null, values);

        return key;

    }

    /* return an existing puzzle entry from the database */

    public Puzzle find(int puzzleid) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        Puzzle result = find(db, puzzleid);
        db.close();
        return result;

    }

    public Puzzle find(SQLiteDatabase db, int puzzleid) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        Puzzle puzzle = null;

        String query = daoFactory.getProperty("sql_get_puzzle");
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(puzzleid)});

        if (cursor.moveToFirst()) {

            cursor.moveToFirst();

            /* get data for puzzle */

            HashMap<String, String> params = new HashMap<>();

            /* get data for new puzzle */

            params.put(daoFactory.getProperty("sql_field_name"), cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_name"))));
            params.put(daoFactory.getProperty("sql_field_description"), cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_description"))));
            params.put(daoFactory.getProperty("sql_field_height"), cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_height"))));
            params.put(daoFactory.getProperty("sql_field_width"), cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_width"))));

            if ( !params.isEmpty() )
                puzzle = new Puzzle(params);

            /* get list of words (if any) to add to puzzle */

            WordDAO wordDao = daoFactory.getWordDAO();

            ArrayList<Word> words = wordDao.list(db, puzzleid);

            if ( !words.isEmpty() )
                puzzle.addWordsToPuzzle(words);

            cursor.close();

            /* get already-guessed words (if any) for puzzle */

            query = daoFactory.getProperty("sql_get_guesses");

            int boxColumnIndex = Integer.parseInt(daoFactory.getProperty("sql_guesses_box_column_index"));
            int directionColumnIndex = Integer.parseInt(daoFactory.getProperty("sql_guesses_direction_column_index"));

            cursor = db.rawQuery(query, new String[]{String.valueOf(puzzleid)});

            if (cursor.moveToFirst()) {

                cursor.moveToFirst();

                do {

                    Integer box = cursor.getInt(boxColumnIndex);
                    WordDirection direction = WordDirection.values()[cursor.getInt(directionColumnIndex)];

                    puzzle.addWordToGuessed(box + direction.toString());

                }
                while ( cursor.moveToNext() );

                cursor.close();

            }

        }

        return puzzle;

    }

    public PuzzleListItem[] list() {

        /* use this method if there is NOT already a SQLiteDatabase open */

        SQLiteDatabase db = daoFactory.getWritableDatabase();
        PuzzleListItem[] result = list(db);
        db.close();
        return result;

    }

    public PuzzleListItem[] list(SQLiteDatabase db) {

        /* use this method if there is NOT already a SQLiteDatabase open */

        ArrayList<PuzzleListItem> result = new ArrayList<>();

        String query = daoFactory.getProperty("sql_get_all_puzzles");
        Cursor cursor = db.rawQuery(query, new String[]{});

        if (cursor.moveToFirst()) {

            PuzzleListItem puzzleListItem = null;

            cursor.moveToFirst();

            /* get data for puzzle */

            /* get data for puzzle */

            //params.put(daoFactory.getProperty("sql_field_puzzleid"), cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_puzzleid"))));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_id")));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(daoFactory.getProperty("sql_field_name")));

            puzzleListItem = new PuzzleListItem(id, name);

            cursor.close();

            result.add(puzzleListItem);

        }

        return result.toArray(new PuzzleListItem[]{});

    }

}