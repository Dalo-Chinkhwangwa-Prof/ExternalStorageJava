package com.bb.externalapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String notesFile = "Notes.html";
    private String fileDirectory = "ExtFiles";

    @BindView(R.id.editText)
    EditText noteEditText;

    @BindView(R.id.note_recyclerview)
    RecyclerView recyclerView;

    private File externalFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        externalFile = new File(getExternalFilesDir(fileDirectory), notesFile);

        ButterKnife.bind(this);

        try {
            readFromExternalStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToExternalStorage() throws IOException {


        FileOutputStream outputStream = new FileOutputStream(externalFile, true);
//        FileOutputStream outputStream = openFileOutput(externalFile.getCanonicalPath(), Context.MODE_APPEND);

        String newNote = noteEditText.getText().toString().trim();
        noteEditText.setText("");

        outputStream.write(newNote.getBytes());
        outputStream.close();
        Toast.makeText(this, "Write Completed", Toast.LENGTH_SHORT).show();

        readFromExternalStorage();
    }

    private void readFromExternalStorage() throws IOException {
        FileReader fileReader = new FileReader(externalFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String newLine = null;
        while ((newLine = bufferedReader.readLine()) != null) {
            Log.d("TAG_X", "Note : " + newLine);
        }
        bufferedReader.close();
    }

    @OnClick(R.id.button)
    public void onSave(View view) {
        try {
            writeToExternalStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
