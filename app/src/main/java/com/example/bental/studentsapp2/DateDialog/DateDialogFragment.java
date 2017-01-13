package com.example.bental.studentsapp2.DateDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.bental.studentsapp2.model.CustomSimpleDate;


/**
 * Created by ben on 12/14/2016.
 */

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDateSetLisetener lisetener;
    private CustomSimpleDate defaultDate;

    public void setOnDateSetListener(OnDateSetLisetener listener, CustomSimpleDate defaultDate) {
        this.lisetener = listener;
        this.defaultDate = defaultDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new DatePickerDialog(getActivity(), this, defaultDate.getYear(), defaultDate.getMonth()-1, defaultDate.getDay());

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (this.lisetener != null) {
            this.lisetener.onDateSet(year, monthOfYear, dayOfMonth);
        }
    }

    public interface OnDateSetLisetener {
        void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }
}
