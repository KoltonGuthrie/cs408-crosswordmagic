package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.PuzzleItemBinding;
import edu.jsu.mcis.cs408.crosswordmagic.model.PuzzleListItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private PuzzleItemBinding binding;
    private List<PuzzleListItem> data;
    private MenuActivity activity;

    public RecyclerViewAdapter(MenuActivity activity, List<PuzzleListItem> data) {
        this.data = data;
        this.activity = activity;
        Log.i("TEST", "GOT RECYCLER DATA: " + data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TEST", "AUFGDSHYUFHJUFHSUJFH VIEW HGOLDER");
        binding = PuzzleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(activity.getItemClick());
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
        //PuzzleItemBinding binding = PuzzleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        //
        //return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("TEST", "BINDING POSITION: " + position);
        holder.setPuzzleListItem(data.get(position));
        holder.bindData();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public PuzzleListItem getItem(int id) {
        return data.get(id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PuzzleListItem puzzleListItem;
        private TextView idLabel;
        private TextView textLabel;
        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("TEST", "AAAAAAAAA: " + itemView);
        }
        public PuzzleListItem getPuzzleListItem() {
            return puzzleListItem;
        }
        public void setPuzzleListItem(PuzzleListItem puzzleListItem) {
            Log.i("TEST", "SET PUZZLE: " + puzzleListItem.getId() + " : " + puzzleListItem.toString());
            this.puzzleListItem = puzzleListItem;
        }
        public void bindData() {
            if (idLabel == null) {
                idLabel = (TextView) itemView.findViewById(R.id.puzzleIdLabel);
            }
            if (textLabel == null) {
                textLabel = (TextView) itemView.findViewById(R.id.puzzleTextLabel);
            }
            Log.i("TEST", idLabel + " : " + textLabel);
            idLabel.setText(String.valueOf(puzzleListItem.getId()));
            textLabel.setText(puzzleListItem.toString());
        }
    }
}