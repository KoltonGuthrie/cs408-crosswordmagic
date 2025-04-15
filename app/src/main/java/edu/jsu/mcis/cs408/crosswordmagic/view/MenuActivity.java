package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.List;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.ActivityMenuBinding;
import edu.jsu.mcis.cs408.crosswordmagic.model.CrosswordMagicModel;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;

public class MenuActivity extends AppCompatActivity implements AbstractView{
    private ActivityMenuBinding binding;
    private CrosswordMagicController controller;
    private final PuzzleItemClickHandler itemClick = new PuzzleItemClickHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        controller = new CrosswordMagicController();
        CrosswordMagicModel crosswordMagicModel = new CrosswordMagicModel(this);

        /* Register View(s) and Model(s) with Controller */
        controller.addModel(crosswordMagicModel);
        controller.addView(this);

        updateRecyclerView();


        binding.buttonDownloadPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick.puzzleListItem != null) {
                    downloadPuzzle(itemClick.puzzleListItem);
                }
            }
        });
    }
    public PuzzleItemClickHandler getItemClick() { return itemClick; }

    public void downloadPuzzle(PuzzleListItem puzzleListItem) {
        int puzzleId = puzzleListItem.getId();

        Intent i = new Intent(this, MainActivity.class);
        //i.putExtra("puzzleid", puzzleId);
        i.putExtra("puzzleid", 1);
        startActivity(i);
    }


    private void updateRecyclerView() {
        controller.getPuzzleListFromAPI();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

        String name = evt.getPropertyName();
        Object value = evt.getNewValue();

        if (name.equals(CrosswordMagicController.PUZZLE_LIST_FROM_API_PROPERTY)) {

            if (value instanceof PuzzleListItem[]) {
                List<PuzzleListItem> puzzles = Arrays.asList((PuzzleListItem[])value);

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(MenuActivity.this, puzzles);
                binding.recyclerViewPuzzles.setHasFixedSize(true);
                binding.recyclerViewPuzzles.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
                binding.recyclerViewPuzzles.setAdapter(adapter);
            }

        }

    }
    private class PuzzleItemClickHandler implements View.OnClickListener {
        private PuzzleListItem puzzleListItem;
        @Override
        public void onClick(View v) {
            int position = binding.recyclerViewPuzzles.getChildLayoutPosition(v);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.recyclerViewPuzzles.getAdapter();
            if (adapter != null) {
                puzzleListItem = adapter.getItem(position);
                int id = puzzleListItem.getId();

                Toast.makeText(v.getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
