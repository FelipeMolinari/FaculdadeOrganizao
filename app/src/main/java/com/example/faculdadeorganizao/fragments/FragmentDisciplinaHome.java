package com.example.faculdadeorganizao.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faculdadeorganizao.R;

public class FragmentDisciplinaHome extends Fragment {
    public FragmentDisciplinaHome() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_disciplina_home,container, false);
    }
}

