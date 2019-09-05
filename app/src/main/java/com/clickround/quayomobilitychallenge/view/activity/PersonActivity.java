package com.clickround.quayomobilitychallenge.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.clickround.quayomobilitychallenge.R;
import com.clickround.quayomobilitychallenge.data.local.room.RoomHelper;
import com.clickround.quayomobilitychallenge.data.local.room.model.Person;
import com.clickround.quayomobilitychallenge.utils.DateUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonActivity extends AppCompatActivity {
    @BindView(R.id.dob)
    TextView dobTextView;
    @BindView(R.id.first_name)
    TextInputEditText firstNameInputEditText;
    @BindView(R.id.last_name)
    TextInputEditText lastNameInputEditText;
    @BindView(R.id.gender)
    RadioGroup genderRadioGroup;
    @BindView(R.id.submit)
    Button submitButton;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();
        dobTextView.setText(DateUtil.getInstance().getTodayDate());

        Person person = null;

        String extra = getIntent().getStringExtra("person");
        if (extra == null) submitButton.setText("Submit");
        else {
            submitButton.setText("Update");
            person = new Gson().fromJson(extra, Person.class);

            firstNameInputEditText.setText(person.getFirstName());
            lastNameInputEditText.setText(person.getLastName());
            dobTextView.setText(DateUtil.getInstance().getDateFormatted(person.getDob(), "yyyy-MM-dd"));

            calendar.setTime(person.getDob());
            genderRadioGroup.check(person.isGender() ? R.id.male : R.id.female);
        }

        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(PersonActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dobTextView.setText(DateUtil.getInstance().getDateFormatted(calendar, "yyyy-MM-dd"));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        Person finalPerson = person;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    if (extra == null) {
                        String firstName = Objects.requireNonNull(firstNameInputEditText.getText()).toString().trim().toLowerCase();
                        String lastName = Objects.requireNonNull(lastNameInputEditText.getText()).toString().trim().toLowerCase();
                        boolean gender = genderRadioGroup.getCheckedRadioButtonId() == R.id.male;

                        Person person = new Person(firstName, lastName, calendar.getTime(), gender);
                        RoomHelper.getInstance(getApplicationContext()).getDatabase().personDao().insert(person);
                    } else {
                        String firstName = Objects.requireNonNull(firstNameInputEditText.getText()).toString().trim().toLowerCase();
                        String lastName = Objects.requireNonNull(lastNameInputEditText.getText()).toString().trim().toLowerCase();
                        boolean gender = genderRadioGroup.getCheckedRadioButtonId() == R.id.male;

                        finalPerson.setFirstName(firstName);
                        finalPerson.setLastName(lastName);
                        finalPerson.setDob(calendar.getTime());
                        finalPerson.setGender(gender);
                        RoomHelper.getInstance(getApplicationContext()).getDatabase().personDao().update(finalPerson);
                    }

                    setResult(1);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validateInput() {
        firstNameInputEditText.setError(null);
        lastNameInputEditText.setError(null);

        View focusView = null;

        if (TextUtils.isEmpty(Objects.requireNonNull(lastNameInputEditText.getText()).toString().trim())) {
            lastNameInputEditText.setError("Field Required");
            focusView = lastNameInputEditText;
            focusView.requestFocus();
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(firstNameInputEditText.getText()).toString().trim())) {
            firstNameInputEditText.setError("Field Required");
            focusView = firstNameInputEditText;
            focusView.requestFocus();
        }

        return focusView == null;
    }
}
