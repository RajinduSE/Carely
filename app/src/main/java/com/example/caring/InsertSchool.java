package com.example.caring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caring.dbHandlers.DbHandler;
import com.example.caring.models.education.School;

import es.dmoral.toasty.Toasty;

public class InsertSchool extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText from;
    private Button btn;
    private Context context;
    private DbHandler schoolDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_school);

        name = findViewById(R.id.editSchool);
        phone = findViewById(R.id.editPhone);
        from = findViewById(R.id.editFrom);
        btn = findViewById(R.id.editBtn);
        context = this;

        schoolDbHandler = new DbHandler(context);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName = name.getText().toString();
                int inputPhone = Integer.parseInt(phone.getText().toString());
                int inputFrom = Integer.parseInt(from.getText().toString());

                School school = new School(inputName, inputPhone, inputFrom, 0);
                if(schoolDbHandler.addSchool(school)){
                    Toasty.success(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, Schools.class));
                }else{
                    Toasty.error(getApplicationContext(), "Inserting Error", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context, InsertSchool.class));
                }
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                startActivity(new Intent(this, MainDashboard.class));
                return true;
            case R.id.education:
                startActivity(new Intent(this, EducationDashboard.class));
                return true;
            case R.id.health:
                startActivity(new Intent(this, Dashboard.class));
                return true;
            case R.id.achievement:
                startActivity(new Intent(this, ViewAchievement.class));
                return true;
            case R.id.timeTable:
                startActivity(new Intent(this, ViewTimetable.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}