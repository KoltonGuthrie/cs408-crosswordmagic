package edu.jsu.mcis.cs408.crosswordmagic.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.crosswordmagic.controller.CrosswordMagicController;
import edu.jsu.mcis.cs408.crosswordmagic.databinding.TabContainerBinding;

public class TabLayoutContainer extends Fragment implements AbstractView {

    private CrosswordMagicController controller;
    private TabContainerBinding binding;
    private TabLayoutAdapter tabLayoutAdapter;
    private ViewPager2 viewPager;
    private ClueFragment clueFragment;
    private PuzzleFragment puzzleFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TabContainerBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get controller and register Fragment as a View
        this.controller = ((MainActivity) getActivity()).getController();
        controller.addView(this);

        // Initialize fragments
        clueFragment = new ClueFragment();
        puzzleFragment = new PuzzleFragment();

        // Set up ViewPager and TabLayout
        tabLayoutAdapter = new TabLayoutAdapter(this);
        viewPager = binding.pager;
        viewPager.setAdapter(tabLayoutAdapter);

        TabLayout tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Puzzle");
                    break;
                case 1:
                    tab.setText("Clues");
                    break;
            }
        }).attach();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        // Pass events onto fragments
        if (clueFragment != null) {
            clueFragment.modelPropertyChange(evt);
        }
        if (puzzleFragment != null) {
            puzzleFragment.modelPropertyChange(evt);
        }
    }
}