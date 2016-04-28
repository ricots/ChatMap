package im.brianoneill.chatmap.controller.map_creation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.RealmMap;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by brianoneill on 16/03/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = getActivity().getLayoutInflater().inflate(R.layout.date_picker_calendar, null);
//        datePicker = (DatePicker)view.findViewById(R.id.date_picker);
//
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        DatePicker d = datePickerDialog.getDatePicker();
        d.setCalendarViewShown(true);
        //d.setBackgroundColor(getResources().getColor(R.color.chatMapDarkGrey));

        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog;

    }

    //implemented method
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        //format date
        Date date = new Date(year, monthOfYear, dayOfMonth);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yy");
        String mapDate = dateFormatter.format(date);

        //commit to realm
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        // Get a Realm instance for this thread
        Realm realm = Realm.getInstance(realmConfig);
        RealmMap realmMap = realm.where(RealmMap.class).findFirst();
//        test retrieval from database - working :)
//        Log.d("***********", "name: " + realmMap.getRealmMapName());
//        Log.d("***********", "lat: " + realmMap.getRealmLatitude());
//        Log.d("***********", "long: " + realmMap.getRealmLongitude());
        realm.beginTransaction();
        realmMap.setRealmDate(mapDate);
        realm.commitTransaction();


    }

}
