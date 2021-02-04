package com.example.roomtutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import Room.MyDatabase;
import Room.Student;

public class MainActivity extends AppCompatActivity {
private EditText firstName, secondName, studentClass,updateName,updateId,deleteId;
private Button btnInsert, btnRead,btnUpdate,btnDelete;
    MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.secondName);
        studentClass = findViewById(R.id.studentClass);
        btnInsert = findViewById(R.id.btnInsert);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        updateName = findViewById(R.id.updateName);
        updateId = findViewById(R.id.updateId);
        btnDelete = findViewById(R.id.btnDelete);
        deleteId = findViewById(R.id.deleteId);
        setDB();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(firstName.getText().toString(),secondName.getText().toString()
                ,studentClass.getText().toString());

                myDatabase.dao().studentInsertion(student);
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<Student> stuData =  myDatabase.dao().getStudent();
               for(int i=0;i<stuData.size();i++) {
                   Log.d("STUDENT DATA", String.valueOf(stuData.get(i).getStuId()+" , "+stuData.get(i).getStuFirstName()+" , "+stuData.get(i).getStuLastName()+" , "+stuData.get(i).getStuClass()));
               }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabase.dao().updateStu(updateName.getText().toString(), Integer.parseInt(updateId.getText().toString()));
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabase.dao().deleteStu(Integer.parseInt(deleteId.getText().toString()));
            }
        });
    }
    public void setDB()
    {
        myDatabase = Room.databaseBuilder(MainActivity.this, MyDatabase.class,"StudentDB")
                .allowMainThreadQueries().build();
    }
}