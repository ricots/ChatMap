package im.brianoneill.chatmap.controller.map_creation;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import im.brianoneill.chatmap.model.RealmMap;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by brianoneill on 28/04/16.
 */
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String mapTime =  String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
        //commit to realm db
        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getActivity()).build();
        // Get a Realm instance for this thread
        Realm realm = Realm.getInstance(realmConfig);
        RealmMap realmMap = realm.where(RealmMap.class).findFirst();

        realm.beginTransaction();
        realmMap.setRealmTime(mapTime);
        realm.commitTransaction();

        DateAndTimeChosen dateTimeChosenActivity = (DateAndTimeChosen)getActivity();
        dateTimeChosenActivity.setDateTextViewColour();
        this.dismiss();

    }

    //set the colour of the setDateTimeTextView on completion of date and time choice - implemented in MapCreatorActivity
    public interface DateAndTimeChosen{
        void setDateTextViewColour();
    }
}
