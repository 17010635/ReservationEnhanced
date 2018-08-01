package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhoneNumber, etGrpSize,etDate,etTime;
    CheckBox cbSmokingArea;
    Button btnSubmit, btnReset;
    String date,time, output;
    int mMonth,mYear,mDay,hour,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etPhoneNumber = findViewById(R.id.editTextMobile);
        etGrpSize = findViewById(R.id.editTextGrpSize);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);
        btnSubmit = findViewById(R.id.buttonSubmit);
        btnReset = findViewById(R.id.buttonReset);
        cbSmokingArea = findViewById(R.id.checkBoxSmoking);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = (dayOfMonth + "/"+ (monthOfYear+1) + "/" + year);
                        etDate.setText(date);
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                };


                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener,mYear ,mMonth,mDay);
                myDateDialog.show();

                final Calendar now = Calendar.getInstance();
                if(now.after(myDateDialog)){
                    Toast.makeText(MainActivity.this, "Date selected cannot be the past", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = (hourOfDay + ":" + minute);
                        etTime.setText(time);
                        hour = hourOfDay;
                        min = minute;
                    }
                };


                // Create the Time Picker Dialog
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener, hour,min,true);
                myTimeDialog.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String phoneNum = etPhoneNumber.getText().toString().trim();
                String grpSize = etGrpSize.getText().toString().trim();
                boolean cbChecked = cbSmokingArea.isChecked();
                if(name.length()!=0 || phoneNum.length()!=0 || grpSize.length()!=0 || date.length() !=0 || time.length() !=0) {

                    String status = "";
                    if (cbChecked){
                        status = "Smoking Area Table.";
                    } else {
                        status = "Non-Smoking Area Table.";
                    }
                    output = String.format("Register: %s\nMobile Number: %s\nPax: %s\nSmoking: %s\nDate: %s\nTime: %s\n", name, phoneNum, grpSize, status, date, time);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                    myBuilder.setTitle("Confirm Your Order");
                    myBuilder.setMessage(output);
                    myBuilder.setCancelable(false);
                    myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                        }
                    });
                    myBuilder.setNeutralButton("Cancel", null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                }else {
                    Toast.makeText( MainActivity.this,"Please ensure all info are fill up!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText("");
                etGrpSize.setText("");
                etPhoneNumber.setText("");
                etDate.setText("");
                etTime.setText("");
                cbSmokingArea.setChecked(false);
            }
        });
    }
}
