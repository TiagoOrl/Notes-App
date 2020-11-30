package com.example.a7_2notesapp;

import java.util.ArrayList;

public class NotesData {
    ArrayList<String> mTitle;
    ArrayList<String> mMainText;

    NotesData(){
        mTitle = new ArrayList<>();
        mMainText = new ArrayList<>();
        mTitle.add(0, "Adicionar Nota");
        mMainText.add(0, "null");

    }
}
