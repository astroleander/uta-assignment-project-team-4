package mavs.uta.team4carental.ui.user.requestCar;

import androidx.appcompat.app.AppCompatActivity;
import mavs.uta.team4carental.R;
import mavs.uta.team4carental.adapter.CarListAdapter;
import mavs.uta.team4carental.pojo.Car;
import mavs.uta.team4carental.pojo.Rental;
import mavs.uta.team4carental.utils.DBHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpecificCarActivity extends AppCompatActivity {
    private Button reserve;

    private DBHelper dbHelper;

    private String totalprice = " ";
    private Rental rental;
    CheckBox checkBox01 ;
    CheckBox checkBox02 ;
    CheckBox checkBox03 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_car);
        Intent i = getIntent();
        Car car = (Car) i.getSerializableExtra(CarListAdapter.CAR_INTENT_TOKEN);
        String user = getIntent().getStringExtra("user");
        String start = getIntent().getStringExtra("start");
        String back = getIntent().getStringExtra("back");
        String occupants = getIntent().getStringExtra("occupants");
//        Rental[] reservations=dbHelper.queryAllReservations("0000-00-00-00:00","3000-00-00-00:00");

        checkBox01 = (CheckBox) findViewById(R.id.checkBox_gps);
        checkBox02 = (CheckBox) findViewById(R.id.checkBox_onstar);
        checkBox03 = (CheckBox) findViewById(R.id.checkBox_siriusXM);
//        String string1 = dbHelper.queryUserstatus("chen");
//        String string2 = dbHelper.queryUserstatus("li");






        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        Date startdate = null;
        Date backdate = null;
        try {
            startdate = format.parse(start);
            backdate = format.parse(back);
        }catch (ParseException e){
            e.printStackTrace();
        }

        float durations = (backdate.getTime() - startdate.getTime())/(1000*24*60*60);
        if(((backdate.getTime() - startdate.getTime())%(1000*24*60*60))==0){

        }else{
            durations+=1;
        }
        String dur = String.valueOf(durations);
        long flag = startdate.getTime();
        int day_of_weekend = 0;
        int day_of_weekday = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(startdate);
        int start_week = cal.get(Calendar.DAY_OF_WEEK)-1;
        cal.setTime(backdate);
        int back_week = cal.get(Calendar.DAY_OF_WEEK)-1;

        int flag_week = start_week;
        for(;(backdate.getTime()-flag)>(1000*24*60*60);){
            if(flag_week==0||flag_week==6){
                day_of_weekend+=1;
            }else{
                day_of_weekday+=1;
            }
            flag_week+=1;
            flag_week = flag_week%7;
            flag+=(1000*24*60*60);
        }
        if(back_week==0||back_week==6){
            day_of_weekend+=1;
        }else{
            day_of_weekday+=1;
        }


//
//
////        TextView test = findViewById(R.id.for_test);
////        if (car != null) {
////            test.setText(car.toString());
////        } else {
////            test.setText("error token");
////        }
////        Intent i = getIntent();
////        Car car = (Car) i.getSerializableExtra(CarListAdapter.CAR_INTENT_TOKEN);



        float price_weekday = Float.valueOf(car.getWeekday());
        float price_weekend = Float.valueOf(car.getWeekend());
        final float price = price_weekday * day_of_weekday + price_weekend * day_of_weekend;


        totalprice = String.valueOf(price);
//        System.out.println(totalprice);



        if (car != null) {
            ((TextView) findViewById(R.id.car_name)).setText(car.getCarname());
            ((TextView) findViewById(R.id.capacity)).setText(car.getCapicity());
            ((TextView) findViewById(R.id.Start)).setText(start);
            ((TextView) findViewById(R.id.Back)).setText(back+String.valueOf(back_week));
            ((TextView) findViewById(R.id.duration)).setText(day_of_weekday+" weekday(s),"+day_of_weekend+" weekend(s)");
            ((TextView) findViewById(R.id.number_of_occupants)).setText(occupants+" occupant(s)");

            ((TextView) findViewById(R.id.total_price)).setText("$"+totalprice);

        }

//        totalprice = String.valueOf(price[0]);

        reserve = findViewById(R.id.reserve);
//        final String finalExtra = extra;
        final float finalprice = price;
        reserve.setOnClickListener(v -> {
//            dbHelper.addReservation(rental);



//            Toast.makeText(this, "Reservation number 2020,price: $"+totalprice+ finalExtra,Toast.LENGTH_LONG).show();
            finish();

        });
    }
}