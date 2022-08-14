package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calculation extends AppCompatActivity implements View.OnClickListener
{
    private TextView editdate;
    String date,fday,fmonth,fyear;
    String date_format;

    private Button calculate;
    private EditText editmicro;
    private EditText editcoversmall;
    private EditText editcovermed;
    private EditText editcoverlarge;

    private EditText editbotsmall;
    private EditText editbotmed;
    private EditText editbotlarge;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;


    double totalco2=0.0;
    double mtotalco2=0.0,ytotalco2=0.0;

    public static double sday_total=0.0;
    public static double totalco2()
    {
        return sday_total;
    }

    List<Double> newallco2=new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__calculation);

        editdate=findViewById(R.id.caldate);

        Calendar calendar= Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        calculate=(Button) findViewById(R.id.calbtn);
        calculate.setOnClickListener(this);

        editmicro=(EditText)findViewById(R.id.calmicro);
        editcoversmall=(EditText) findViewById(R.id.calsmall);
        editcovermed=(EditText) findViewById(R.id.calmed);
        editcoverlarge=(EditText)findViewById(R.id.callarge);
        editbotsmall=(EditText)findViewById(R.id.calsmallbot);
        editbotmed=(EditText) findViewById(R.id.calmedbot);
        editbotlarge=(EditText)findViewById(R.id.callargebot);


        editdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog dialog = new DatePickerDialog(Calculation.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day)
                    {
                        month=month+1;
                        fday=""+day;
                        fmonth=""+month;
                        fyear=""+year;
                        date=""+day+month+year;
                        date_format=day+"/0"+month+"/"+year;
                        editdate.setText(date_format);

                    }
                },year,month,day);
                dialog.show();
            }
        });

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        userid=user.getUid();
    }

    @Override
    public void onClick(View view)
    {
        String micro= editmicro.getText().toString().trim();
        String scover=editcoversmall.getText().toString().trim();
        String mcover=editcovermed.getText().toString().trim();
        String lcover=editcoverlarge.getText().toString().trim();
        String sbottle=editbotsmall.getText().toString().trim();
        String mbottle=editbotmed.getText().toString().trim();
        String lbottle=editbotlarge.getText().toString().trim();
        if(date.isEmpty())
        {
            editdate.setError("Date is required");
            editdate.requestFocus();
            return;
        }
        if(micro.isEmpty())
        {
            editmicro.setError("Enter quantity/Enter 0 if none");
            editmicro.requestFocus();
            return;
        }
        if(scover.isEmpty())
        {
            editcoversmall.setError("Enter quantity/Enter 0 if none");
            editcoversmall.requestFocus();
            return;
        }
        if(mcover.isEmpty())
        {
            editcovermed.setError("Enter quantity/Enter 0 if none");
            editcovermed.requestFocus();
            return;
        }
        if(lcover.isEmpty())
        {
            editcoverlarge.setError("Enter quantity/Enter 0 if none");
            editcoverlarge.requestFocus();
            return;
        }
        if(sbottle.isEmpty())
        {
            editbotsmall.setError("Enter quantity/Enter 0 if none");
            editbotsmall.requestFocus();
            return;
        }
        if(mbottle.isEmpty())
        {
            editbotmed.setError("Enter quantity/Enter 0 if none");
            editbotmed.requestFocus();
            return;
        }
        if(lbottle.isEmpty())
        {
            editbotlarge.setError("Enter quantity/Enter 0 if none");
            editbotlarge.requestFocus();
            return;
        }
        if(!micro.matches("[0-9]+"))
        {
            editmicro.setError("Enter numerical value");
            editmicro.requestFocus();
            return;
        }
        if(!scover.matches("[0-9]+"))
        {
            editcoversmall.setError("Enter numerical value");
            editcoversmall.requestFocus();
            return;
        }
        if(!mcover.matches("[0-9]+"))
        {
            editcovermed.setError("Enter numerical value");
            editcovermed.requestFocus();
            return;
        }
        if(!lcover.matches("[0-9]+"))
        {
            editcoverlarge.setError("Enter numerical value");
            editcoverlarge.requestFocus();
            return;
        }
        if(!sbottle.matches("[0-9]+"))
        {
            editbotsmall.setError("Enter numerical value");
            editbotsmall.requestFocus();
            return;
        }
        if(!mbottle.matches("[0-9]+"))
        {
            editbotmed.setError("Enter numerical value");
            editcoversmall.requestFocus();
            return;
        }
        if(!lbottle.matches("[0-9]+"))
        {
            editbotlarge.setError("Enter numerical value");
            editbotlarge.requestFocus();
            return;
        }

        double imicro=Double.parseDouble(micro);
        double iscover=Double.parseDouble(scover);
        double imcover=Double.parseDouble(mcover);
        double ilcover=Double.parseDouble(lcover);
        double isbottle=Double.parseDouble(sbottle);
        double imbottle=Double.parseDouble(mbottle);
        double ilbottle=Double.parseDouble(lbottle);

        double day_total= (0.000324*imicro)+(0.00324*iscover)+(0.0046656*imcover)+
                               (0.00756*ilcover)+(0.0108*isbottle)+(0.015228*imbottle)+(0.02170*ilbottle);
        sday_total = day_total;


     /*   reference.child(userid).child(date).child("totalco2").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    totalco2=(double) snapshot.getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }

        });

        reference.child(userid).child(date).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    double day_data= (double) snapshot.getValue();
                    totalco2=totalco2-day_data;
                    reference.child(userid).child(date).setValue(day_total).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                totalco2=totalco2+day_total;
                                reference.child(userid).child("totalco2").setValue(totalco2);
                                Toast.makeText(Calculation.this,"Date Updated Successfully!!!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Calculation.this,"Failed to Calculate",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    reference.child(userid).child(date).setValue(day_total).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                totalco2=totalco2+day_total;
                                reference.child(userid).child("totalco2").setValue(totalco2);
                                Toast.makeText(Calculation.this,"Date Created Sucessfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Calculation.this,"Failed to Calculate",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });*/


        reference.child(userid).child(fyear).child(fmonth).child(fday).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    System.out.println("ATA True");
                    double old_day_data=(double) snapshot.getValue();
                    DatabaseReference yreference=reference.child(userid).child(fyear).child("yeartotalco2");
                    yreference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.exists())
                            {
                                ytotalco2=(double) snapshot.getValue();
                                ytotalco2=ytotalco2-old_day_data+day_total;
                                yreference.setValue(ytotalco2);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {


                        }
                    });
                    DatabaseReference mreference=reference.child(userid).child(fyear).child(fmonth).child("monthtotalco2");
                    mreference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.exists())
                            {
                                mtotalco2=(double) snapshot.getValue();
                                mtotalco2=mtotalco2-old_day_data+day_total;
                                mreference.setValue(mtotalco2);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                    DatabaseReference treference=reference.child(userid).child("totalco2");
                    treference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.exists())
                            {
                                totalco2=(double) snapshot.getValue();
                                totalco2=totalco2-old_day_data+day_total;
                                treference.setValue(totalco2);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                    reference.child(userid).child(fyear).child(fmonth).child(fday).setValue(day_total);
                    totalco2=0;mtotalco2=0;ytotalco2=0;
                    Toast.makeText(Calculation.this, "Data Updated Successfully!!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    reference.child(userid).child(fyear).child(fmonth).child(fday).setValue(day_total);
                    DatabaseReference yreference=reference.child(userid).child(fyear);
                    yreference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.hasChild("yeartotalco2"))
                            {
                                ytotalco2=(double)snapshot.child("yeartotalco2").getValue();
                                ytotalco2=ytotalco2+day_total;
                                yreference.child("yeartotalco2").setValue(ytotalco2);
                            }
                            else
                            {
                                yreference.child("yeartotalco2").setValue(day_total);
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    DatabaseReference mreference=reference.child(userid).child(fyear).child(fmonth);
                    mreference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {

                            if(snapshot.hasChild("monthtotalco2"))
                            {
                                mtotalco2=(double)snapshot.child("monthtotalco2").getValue();
                              //  mtotalco2=dvalue(value);
                                mtotalco2=mtotalco2+day_total;
                                mreference.child("monthtotalco2").setValue(mtotalco2);
                            }
                            else
                            {
                                mreference.child("monthtotalco2").setValue(day_total);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });

                    DatabaseReference treference=reference.child(userid);
                    treference.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.hasChild("totalco2"))
                            {
                                totalco2=(double) snapshot.child("totalco2").getValue();
                                totalco2=totalco2+day_total;
                                treference.child("totalco2").setValue(totalco2);
                            }
                            else
                            {
                                treference.child("totalco2").setValue(day_total);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    mtotalco2=0;ytotalco2=0;totalco2=0;
                    Toast.makeText(Calculation.this,"Data created Successfully!!!",Toast.LENGTH_LONG).show();

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        Intent intent = new Intent(Calculation.this, Output.class);
        startActivity(intent);
    }

    private double dvalue(Object obj)
    {
        String str = obj.toString();
        double d = Double.valueOf(str).doubleValue();
        return d;
    }

}