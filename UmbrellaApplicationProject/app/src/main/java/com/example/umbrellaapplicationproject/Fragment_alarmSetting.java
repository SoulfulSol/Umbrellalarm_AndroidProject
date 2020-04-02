/*

현재 버그 리스트(프래그먼트)
1. timePicker 안건들면 DB에 들어가는 값이 0시 0분임 -> 현재 API로는 setHour, setMinute 안먹힘

 */


package com.example.umbrellaapplicationproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Fragment_alarmSetting extends Fragment {
    private LinearLayout frag_mainLayout;
    private ScrollView scrollView;
    private ImageButton frag_backButton;

    /* 요일 버튼 및 boolean */
    private Button button_monday;
    private boolean bol_monday;
    private Button button_tuesday;
    private boolean bol_tuesday;
    private Button button_wednesday;
    private boolean bol_wednesday;
    private Button button_thursday;
    private boolean bol_thursday;
    private Button button_friday;
    private boolean bol_friday;
    private Button button_saturday;
    private boolean bol_saturday;
    private Button button_sunday;
    private boolean bol_sunday;

    /* 시간 버튼 및 boolean*/
    private Button time_sixAMToNine;
    private boolean bool_time_sixAMToNine;
    private Button time_nineAMToTwelve;
    private boolean bool_time_nineAMToTwelve;
    private Button time_twelveToThree;
    private boolean bool_time_twelveToThree;
    private Button time_threeToSix;
    private boolean bool_time_threeToSix;
    private Button time_sixToNine;
    private boolean bool_time_sixToNine;
    private Button time_ninePMToTwelve;
    private boolean bool_time_ninePMToTwelve;

    /* precipitation radio */
    private RadioGroup radioGroupOfPrecipitation;
    private RadioGroup radioGroupOfAlarmPoint;

    /* Layout and spinners for selecting location*/
    private Spinner location_province;
    private Spinner location_seoul;
    private Spinner location_kyeunggi;
    private FrameLayout locationBundle;
    private boolean checkLocationSelection;

    private Button alarmAddButton;

    /* TextView for validation */
    private TextView valid_day;
    private TextView valid_location;
    private TextView valid_time;
    private TextView valid_precipitation;
    private TextView valid_alarmTime;

    /* Data sets to transfer to MainActivity / to save in DB */
    private boolean[] dayList = new boolean[7];
    private String[] locationList = new String[2];
    private boolean[] timeList = new boolean[6];
    private int precipitation;
    private int alarmPoint; //0 : default, 1 : a day ahead , 2 : on the very day
    private int pickedHour;
    private int pickedMinute;

    /* timePicker vars */
    private TimePicker timePicker;

    /* DB */
    private String dbTableName = "alarmData";
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_alarm_setting, container, false);
        scrollView = rootView.findViewById(R.id.scrollView);

        /* 뒤 액티비티 버튼 클릭 방지*/
        frag_mainLayout = rootView.findViewById(R.id.frag_mainLayout);
        frag_mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        /* 좌측 상단 백버튼 */
        frag_backButton = rootView.findViewById(R.id.frag_backButton);
        frag_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setDialogBuilder(1);
                // if user clicks the back button on the left-top, parameter is 1,
            }
        });

        /* 알람 요일 설정 관련 */
        button_monday = rootView.findViewById(R.id.button_monday);
        button_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_monday, bol_monday);
                bol_monday = switchBolean(bol_monday);
            }
        });
//        button_monday.setOnTouchListener();
        button_tuesday = rootView.findViewById(R.id.button_tuesday);
        button_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_tuesday, bol_tuesday);
                bol_tuesday = switchBolean(bol_tuesday);
            }
        });
        button_wednesday = rootView.findViewById(R.id.button_wednesday);
        button_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_wednesday, bol_wednesday);
                bol_wednesday = switchBolean(bol_wednesday);
            }
        });
        button_thursday = rootView.findViewById(R.id.button_thursday);
        button_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_thursday, bol_thursday);
                bol_thursday = switchBolean(bol_thursday);
            }
        });
        button_friday = rootView.findViewById(R.id.button_friday);
        button_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_friday, bol_friday);
                bol_friday = switchBolean(bol_friday);
            }
        });
        button_saturday = rootView.findViewById(R.id.button_saturday);
        button_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_saturday, bol_saturday);
                bol_saturday = switchBolean(bol_saturday);
            }
        });
        button_sunday = rootView.findViewById(R.id.button_sunday);
        button_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(button_sunday, bol_sunday);
                bol_sunday = switchBolean(bol_sunday);
            }
        });

        /* 알람 시간 설정 클릭 관련 */
        time_sixAMToNine = rootView.findViewById(R.id.time_sixAMToNine);
        time_sixAMToNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_sixAMToNine, bool_time_sixAMToNine);
                bool_time_sixAMToNine = switchBolean(bool_time_sixAMToNine);
            }
        });

        time_nineAMToTwelve = rootView.findViewById(R.id.time_nineAMToTwelve);
        time_nineAMToTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_nineAMToTwelve, bool_time_nineAMToTwelve);
                bool_time_nineAMToTwelve = switchBolean(bool_time_nineAMToTwelve);
            }
        });

        time_twelveToThree = rootView.findViewById(R.id.time_twelveToThree);
        time_twelveToThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_twelveToThree, bool_time_twelveToThree);
                bool_time_twelveToThree = switchBolean(bool_time_twelveToThree);
            }
        });

        time_threeToSix = rootView.findViewById(R.id.time_threeToSix);
        time_threeToSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_threeToSix, bool_time_threeToSix);
                bool_time_threeToSix = switchBolean(bool_time_threeToSix);
            }
        });

        time_sixToNine = rootView.findViewById(R.id.time_sixToNine);
        time_sixToNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_sixToNine, bool_time_sixToNine);
                bool_time_sixToNine = switchBolean(bool_time_sixToNine);
            }
        });

        time_ninePMToTwelve = rootView.findViewById(R.id.time_ninePMToTwelve);
        time_ninePMToTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChangeOnClick(time_ninePMToTwelve, bool_time_ninePMToTwelve);
                bool_time_ninePMToTwelve = switchBolean(bool_time_ninePMToTwelve);
            }
        });

        locationBundle = rootView.findViewById(R.id.locationBundle);
        location_kyeunggi = rootView.findViewById(R.id.location_kyeunggi);
        location_seoul = rootView.findViewById(R.id.location_seoul);

        location_province = rootView.findViewById(R.id.location_province);
        location_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* 지역선택 : 0 , 서울코드 : 1, 경기 : 2*/
                if (position == 1) { //서울
                    locationBundle.setVisibility(View.VISIBLE);
                    location_seoul.setVisibility(View.VISIBLE);
                    location_kyeunggi.setVisibility(View.INVISIBLE);
                    location_kyeunggi.setSelection(0);
                } else if (position == 2) { //경기
                    locationBundle.setVisibility(View.VISIBLE);
                    location_seoul.setVisibility(View.INVISIBLE);
                    location_kyeunggi.setVisibility(View.VISIBLE);
                    location_seoul.setSelection(0);
                } else { // 아무선택안함
                    locationBundle.setVisibility(View.INVISIBLE);
                    location_seoul.setSelection(0);
                    location_kyeunggi.setSelection(0);
                    checkLocationSelection = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location_seoul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    checkLocationSelection = false;
                } else {
                    checkLocationSelection = true;
                    locationList[0] = "서울";
                    /* put the position number together as an index */
                    locationList[1] = position + location_seoul.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /* When clicking '경기' */
        location_kyeunggi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    checkLocationSelection = false;
                } else {
                    checkLocationSelection = true;
                    locationList[0] = "경기";
                    /* put the position number together as an index */
                    locationList[1] = position + location_kyeunggi.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* Set precipitation */
        radioGroupOfPrecipitation = rootView.findViewById(R.id.radioGroupOfPrecipitation);
        precipitation = 0;
        radioGroupOfPrecipitation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.precipitationRadioButtonAbove30) {
                    precipitation = 30;
                } else if (checkedId == R.id.precipitationRadioButtonAbove50) {
                    precipitation = 50;
                } else {
                    precipitation = 70;
                }
            }
        });

        /* Set AlarmPoint */
        radioGroupOfAlarmPoint = rootView.findViewById(R.id.radioGroupOfAlarmPoint);
        alarmPoint = 0; // Default value when radiobuttons are left blank
        radioGroupOfAlarmPoint.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 4: 알람 전날, 5: 알람 당일
                if (checkedId == R.id.alarmPointRadioButtonBeforehand) {
                    alarmPoint = 1;
                } else {
                    alarmPoint = 2;
                }
            }
        });

        valid_day = rootView.findViewById(R.id.valid_day);
        valid_location = rootView.findViewById(R.id.valid_location);
        valid_time = rootView.findViewById(R.id.valid_time);
        valid_precipitation = rootView.findViewById(R.id.valid_precipitation);
        valid_alarmTime = rootView.findViewById(R.id.valid_alarmTime);

        /* 설정 버튼 클릭*/
        alarmAddButton = rootView.findViewById(R.id.alarmAddButton);
        alarmAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* validation */
                if (!validation()) {
                    return;
                }
                dayList[0] = bol_monday;
                dayList[1] = bol_tuesday;
                dayList[2] = bol_wednesday;
                dayList[3] = bol_thursday;
                dayList[4] = bol_friday;
                dayList[5] = bol_saturday;
                dayList[6] = bol_sunday;

                timeList[0] = bool_time_sixAMToNine;
                timeList[1] = bool_time_nineAMToTwelve;
                timeList[2] = bool_time_twelveToThree;
                timeList[3] = bool_time_threeToSix;
                timeList[4] = bool_time_sixToNine;
                timeList[5] = bool_time_ninePMToTwelve;

                ((MainActivity) getActivity()).setDialogForSetting();
                dataInsertToDB();
            }
        });
        /* if onTimeChanged is not called, default hour and minute are selected */
        timePicker = rootView.findViewById(R.id.timePicker);
        if (Build.VERSION.SDK_INT >= 23) {
            pickedHour = timePicker.getHour();
            pickedMinute = timePicker.getMinute();
        } else {
            pickedHour = timePicker.getCurrentHour();
            pickedMinute = timePicker.getCurrentMinute();
        }
        /* otherwise */
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                pickedHour = hourOfDay;
                pickedMinute = minute;
            }
        });
        /***********************************수정중*************************/
        /* When attached, verify if there is DB already inserted */
        sqLiteDatabase = ((MainActivity) getActivity()).sqLiteDatabaseGetter();
        Cursor cursor = sqLiteDatabase.rawQuery("select id from " + dbTableName, null);
        int recordCount = cursor.getCount();
        if (recordCount > 0) {
            // if DB exists
            setFragmentDatawhenDBexists(sqLiteDatabase);

            colorChangeOnClick(button_wednesday, false);
            radioGroupOfPrecipitation.check(R.id.precipitationRadioButtonAbove30);
            timePicker.setCurrentHour(5);
            timePicker.setCurrentMinute(55);
        }

        return rootView;
    }

    public void setFragmentDatawhenDBexists(SQLiteDatabase sqLiteDatabase) {
        String querie = "SELECT * FROM " + dbTableName;
        Cursor cursor = sqLiteDatabase.rawQuery(querie, null);
        cursor.moveToNext();
        /*
         * set days and switch the color of the buttons
         * The number of day columns is seven starting from the index of 1
         */
        for (int i = 1; i < 8; i++) {
            if (cursor.getInt(i) == 1) {
                dayList[i - 1] = true;
                if (i == 1) {
                    colorChangeOnClick(button_monday, false);
                    bol_monday = switchBolean(bol_monday);
                } else if (i == 2) {
                    colorChangeOnClick(button_tuesday, false);
                    bol_tuesday = switchBolean(bol_tuesday);
                } else if (i == 3) {
                    colorChangeOnClick(button_wednesday, false);
                    bol_wednesday = switchBolean(bol_wednesday);
                } else if (i == 4) {
                    colorChangeOnClick(button_thursday, false);
                    bol_thursday = switchBolean(bol_thursday);
                } else if (i == 5) {
                    colorChangeOnClick(button_friday, false);
                    bol_friday = switchBolean(bol_friday);
                } else if (i == 6) {
                    colorChangeOnClick(button_saturday, false);
                    bol_saturday = switchBolean(bol_saturday);
                } else {
                    colorChangeOnClick(button_sunday, false);
                    bol_sunday = switchBolean(bol_sunday);
                }
            }
            /* set location and display of the spinner */
            String getProvince = cursor.getString(8);
            String getSubProv = cursor.getString(9);
            locationList[0] = getProvince;
            locationList[1] = getSubProv;
            if (getProvince.charAt(0) == '서') {
                location_province.setSelection(1);
                int subProvIndex = getSubProv.charAt(0) - '0'; // Numbers in Char start from ASC|| code 48
                location_seoul.setSelection(subProvIndex);
            } else {
                location_province.setSelection(2);
                int subProvIndex = getSubProv.charAt(0) - '0'; // Numbers in Char start from ASC|| code 48
                location_kyeunggi.setSelection(subProvIndex);
            }
        }
        //=========요일/province 설정 끝. 나머지 Data들도 설정하면 됨 ===//
    }


    /* 요일, 날짜 버튼 클릭 시 색 변경 메소드*/
    public void colorChangeOnClick(Button intputButton, Boolean dayBoolean) {
        if (dayBoolean == false) {
            intputButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_customized_day_selected));
        } else {
            intputButton.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_customized_day_unselected));
        }
    }

    /* 요일, 날짜 boolean 변경 메소드*/
    public boolean switchBolean(boolean target) {
        if (target) {
            return false;
        } else {
            return true;
        }
    }

    /* Method for scrolling up the the top of the fragment (called by the MainActivity) */
    public void scrollUptotheTop() {
        scrollView.scrollTo(0, 0);
    }

    public boolean validation() {
        /* Checking set day*/
        if (bol_monday == false && bol_tuesday == false && bol_wednesday == false &&
                bol_thursday == false && bol_friday == false && bol_saturday == false && bol_sunday == false) {
            scrollView.smoothScrollTo(0, 0);
            valid_day.setVisibility(View.VISIBLE);
        } else {
            valid_day.setVisibility(View.GONE);
        }

        /* checking set location */
        if (checkLocationSelection == false) {
            valid_location.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
        } else {
            valid_location.setVisibility(View.GONE);
        }

        /* checking set time */
        if (bool_time_sixAMToNine == false && bool_time_nineAMToTwelve == false && bool_time_twelveToThree == false &&
                bool_time_threeToSix == false && bool_time_sixToNine == false && bool_time_ninePMToTwelve == false) {
            scrollView.smoothScrollTo(0, 0);
            valid_time.setVisibility(View.VISIBLE);
        } else {
            valid_time.setVisibility(View.GONE);
        }

        /* checking set precipitation */
        if (precipitation == 0) {
            scrollView.smoothScrollTo(0, 600);
            valid_precipitation.setVisibility(View.VISIBLE);
        } else {
            valid_precipitation.setVisibility(View.GONE);
        }
        /* checking set alarmPoint */
        if (alarmPoint == 0) {
            scrollView.smoothScrollTo(0, 1500);
            valid_alarmTime.setVisibility(View.VISIBLE);
        } else {
            valid_alarmTime.setVisibility(View.GONE);
        }

        if (valid_day.getVisibility() == View.VISIBLE || valid_location.getVisibility() == View.VISIBLE
                || valid_time.getVisibility() == View.VISIBLE || valid_precipitation.getVisibility() == View.VISIBLE
                || valid_alarmTime.getVisibility() == View.VISIBLE) {
            return false;
        }
        return true;
    }

    public void cancelAlarmSetting() {
        bol_monday = false;
        bol_tuesday = false;
        bol_wednesday = false;
        bol_thursday = false;
        bol_friday = false;
        bol_saturday = false;
        bol_sunday = false;

        bool_time_sixAMToNine = false;
        bool_time_nineAMToTwelve = false;
        bool_time_twelveToThree = false;
        bool_time_threeToSix = false;
        bool_time_sixToNine = false;
        bool_time_ninePMToTwelve = false;

        checkLocationSelection = false;
        location_province.setSelection(0);
    }

    public void dataInsertToDB() {
        /* dayList insert */
        String dayListResult = "";
        for (int i = 0; i < dayList.length; i++) {
            if (dayList[i] == true) {
                dayListResult += "1, ";
            } else {
                dayListResult += "0, ";
            }
        }
        /* location insert */
        String prov = locationList[0];
        String subProv = locationList[1];

        /* timeList insert */
        String timeListResult = "";
        for (int i = 0; i < timeList.length; i++) {
            if (timeList[i] == true) {
                timeListResult += "1, ";
            } else {
                timeListResult += "0, ";
            }
        }
        String values = dayListResult + "'" + prov + "', '" + subProv + "', " +
                timeListResult + precipitation + ", " + alarmPoint + ", " + pickedHour + ", " + pickedMinute;
        String querie = "insert into " + dbTableName + "(mon, tue, wed, thu, fri, sat, sun, prov, subProv, " +
                "time1, time2, time3, time4, time5, time6, precipitation, alarmPoint, setHour, setMinute) " +
                " values ( " + values + " )";
        sqLiteDatabase = ((MainActivity) getActivity()).sqLiteDatabaseGetter();
        sqLiteDatabase.execSQL(querie);
    }
}
