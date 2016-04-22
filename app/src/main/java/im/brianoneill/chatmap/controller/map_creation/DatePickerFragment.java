package im.brianoneill.chatmap.controller.map_creation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import im.brianoneill.chatmap.R;

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
        //TODO send date chosen by user to Firebase
        //Launch Time Picker
    }
}
