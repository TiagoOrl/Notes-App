package com.example.a7_2notesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    static NotesData notesData;
    ListView lv_main;
    static ArrayAdapter<String> ad_main;
    static private final String FILE_NAME = "notes_data";
    static Context mContext;

    @Override
    protected void onStop() {
        super.onStop();
        writeData(notesData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main = findViewById(R.id.lv_main);
        mContext = this.getApplicationContext();

        if ((notesData = getData()) == null){
            notesData = new NotesData();
        }

        ad_main = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesData.mTitle);
        lv_main.setAdapter(ad_main);

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);

                if (position == 0){
                    intent.putExtra("title", "new");
                }

                else {
                    intent.putExtra("title", notesData.mTitle.get(position));
                    intent.putExtra("text", notesData.mMainText.get(position));
                    intent.putExtra("notePosition", position);
                }
                startActivity(intent);
            }
        });

        lv_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (position > 0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Remover")
                            .setMessage("Remover Nota da lista?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notesData.mTitle.remove(position);
                                    notesData.mMainText.remove(position);
                                    ad_main.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return true;
                }
                return false;
            }
        });
    }

    NotesData getData(){
        NotesData notesData = null;
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String text = "";

            while ((text = bufferedReader.readLine()) != null)
                stringBuilder.append(text).append("\n");

            notesData = new Gson().fromJson(stringBuilder.toString(), NotesData.class);

            fis.close();
            bufferedReader.close();

        }
        catch (IOException | JsonSyntaxException e){e.printStackTrace(); return null;}

        return  notesData;
    }

    static void writeData(NotesData notes){
        FileOutputStream fos = null;

        try{
            fos = mContext.getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(new Gson().toJson(notes).getBytes());
            fos.close();
        }
        catch (IOException e){e.printStackTrace();}
    }

}