package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.R;
import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;

public class PuzzleFragment extends Fragment implements AbstractView {
    private CrosswordMagicController controller;
    private CrosswordGridView crosswordGridView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puzzle, container, false);

        // Get the CrosswordGridView
        crosswordGridView = view.findViewById(R.id.grid);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* get controller, register Fragment as a View */
        this.controller = ((MainActivity)getContext()).getController();
        controller.addView(this);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if (crosswordGridView != null) {
            crosswordGridView.modelPropertyChange(evt);
        }
    }
}
