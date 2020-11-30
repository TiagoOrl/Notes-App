package com.example.a7_2notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    Intent intent;
    EditText et_main, et_title;
    int i_notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        et_main = findViewById(R.id.et_main);
        et_title = findViewById(R.id.et_title);
        intent = getIntent();

         if (intent.getStringExtra("title").equals("new")){


         } else {
             et_title.setText(intent.getStringExtra("title"));
             et_main.setText(intent.getStringExtra("text"));
             i_notePosition = intent.getIntExtra("notePosition", -99);

             if (i_notePosition == -99)
                 Toast.makeText(this, "Erro ao obter indice", Toast.LENGTH_SHORT).show();
         }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.m_salvar:
                save();
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();
    }

    private void save(){

        if (intent.getStringExtra("title").equals("new") ){
            if (!et_main.getText().toString().equals("")){
                MainActivity.notesData.mTitle.add(et_title.getText().toString());
                MainActivity.notesData.mMainText.add(et_main.getText().toString());
            }


        } else {
            MainActivity.notesData.mTitle.set(i_notePosition, et_title.getText().toString());
            MainActivity.notesData.mMainText.set(i_notePosition, et_main.getText().toString());
        }

        MainActivity.writeData(MainActivity.notesData);
        MainActivity.ad_main.notifyDataSetChanged();
    }
}