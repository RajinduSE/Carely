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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.caring.dbHandlers.DbHandler;
import com.example.caring.models.achievement.AchievementModel;
import com.example.caring.models.education.School;

import es.dmoral.toasty.Toasty;

public class Achievement extends AppCompatActivity {
    private EditText event;
    private EditText description;
    private EditText award;
    private EditText year;
    private Button add;
    private Context context;
    private DbHandler achievementDbHandler;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        event = findViewById(R.id.inputEvent);
        description = findViewById(R.id.inputDescription);
        award = findViewById(R.id.inputAwards);
        year = findViewById(R.id.inputDate);
        add = findViewById(R.id.add_achievement);
        context = this;
        achievementDbHandler = new DbHandler(context);

        //Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add validations
        awesomeValidation.addValidation(this,R.id.inputEvent, RegexTemplate.NOT_EMPTY,R.string.invalid_input);
        awesomeValidation.addValidation(this,R.id.inputDescription, RegexTemplate.NOT_EMPTY,R.string.invalid_input);
        awesomeValidation.addValidation(this,R.id.inputAwards, RegexTemplate.NOT_EMPTY,R.string.invalid_input);
        awesomeValidation.addValidation(this,R.id.inputDate, RegexTemplate.NOT_EMPTY,R.string.invalid_input);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()) {
                    String userEvent = event.getText().toString();
                    String userDescription = description.getText().toString();
                    String userAward = award.getText().toString();
                    String userYear = year.getText().toString();

                    AchievementModel achievementModel = new AchievementModel(userEvent, userDescription, userAward, Integer.parseInt(userYear));
                    if (achievementDbHandler.addAchievement(achievementModel)) {
                        Toasty.success(getApplicationContext(), "Successfully Added", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context, ViewAchievement.class));
                    } else {
                        Toasty.error(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context, Achievement.class));
                    }
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