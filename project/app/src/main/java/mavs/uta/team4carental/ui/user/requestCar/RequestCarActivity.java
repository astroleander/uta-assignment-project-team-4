package mavs.uta.team4carental.ui.user.requestCar;


import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View.OnClickListener;

import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import mavs.uta.team4carental.R;
import mavs.uta.team4carental.adapter.CarListAdapter;
import mavs.uta.team4carental.pojo.Car;
import mavs.uta.team4carental.pojo.Rental;
import mavs.uta.team4carental.utils.DBHelper;

public class RequestCarActivity extends AppCompatActivity implements OnClickListener{
    private TextView startDate;
    private TextView endDate;
    private TextView startTime;
    private TextView endTime;
    private Button btn_request;
    private Calendar cal;
    private int year,month,day,hour,minute;
    private EditText capacity;

    private ArrayList<Car> carListItems;
    private ListView carListView;
    private CarListAdapter adapter;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_car);
        this.initFilter();
        this.initCarList();
        this.initSearchButton();
    }

    private void initCarList() {
        carListView = findViewById(R.id.car_list);
    }

    private void initSearchButton() {
        btn_request = findViewById(R.id.btn_requestCar);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestCarActivity.this.queryCars();
                adapter = new CarListAdapter(RequestCarActivity.this, RequestCarActivity.this.carListItems);
                carListView.setAdapter(adapter);
            }
        });
    }

    private void queryCars() {
        String userName = getIntent().getStringExtra("userName");
        dbHelper = new DBHelper(this);
//        //给queryReservations提供参数使其能够进行查找操作
//        Rental[] reservation_list;
//        reservation_list = dbHelper.queryReservations(userName, startDate.toString(), endDate.toString());
//
//        int k=0;
//        String[] car_names = new String[100];
//        for(Rental a:reservation_list){
//            car_names[k]=a.getCarName();
//        }
        Car[] car_list = dbHelper.queryCar();
        ArrayList<Car> result = new ArrayList<>(Arrays.asList(car_list));
        this.carListItems = result;
    }

    private void initFilter() {
        //获取当前日期
        getDate();

        startDate=(TextView) findViewById(R.id.startDate);
        startDate.setOnClickListener(this);

        startTime=(TextView)findViewById(R.id.startTime);
        startTime.setOnClickListener(this);

        endDate=(TextView) findViewById(R.id.endDate);
        endDate.setOnClickListener(this);

        endTime =(TextView)findViewById(R.id.endTime);
        endTime.setOnClickListener(this);

        capacity = (EditText)findViewById(R.id.capacity);
    }

    //获取当前日期
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);    //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);  //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDate:
                OnDateSetListener listener=new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        startDate.setText(year+"-"+(++month)+"-"+day);   //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(RequestCarActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;

            case R.id.startTime:
                OnTimeSetListener listener1=new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker tp, int hour,int minute) {
                        startTime.setText(hour+":00");
                    }
                };
                TimePickerDialog dialog1 = new TimePickerDialog(RequestCarActivity.this,listener1,hour,minute,true);
                dialog1.show();

                break;

            case R.id.endDate:
                listener=new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        endDate.setText(year+"-"+(++month)+"-"+day);   //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                dialog=new DatePickerDialog(RequestCarActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;


            case R.id.endTime:
                 listener1=new OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker tp, int hour,int minute) {
                        endTime.setText(hour+":00");
                    }
                };
                dialog1 = new TimePickerDialog(RequestCarActivity.this,listener1,hour,minute,true);
                dialog1.show();

                break;
            default:
                break;
        }
    }

}