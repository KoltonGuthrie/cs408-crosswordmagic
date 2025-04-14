package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.FragmentClueBinding;

public class ClueFragment extends Fragment implements AbstractView {

    private CrosswordMagicController controller;
    private FragmentClueBinding binding; // View Binding object

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentClueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get controller and register Fragment as a View
        this.controller = ((MainActivity) getActivity()).getController();
        controller.addView(this);

        // Request clues from the controller
        controller.getClues();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if (binding == null)
            return;

        // Handle property changes
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();

        if (propertyName.equals(CrosswordMagicController.CLUE_ACROSS_PROPERTY)) {
            // Update Across clues
            binding.aContainer.setText((String) newValue);
        } else if (propertyName.equals(CrosswordMagicController.CLUE_DOWN_PROPERTY)) {
            // Update Down clues
            binding.dContainer.setText((String) newValue);
        }
    }
}
