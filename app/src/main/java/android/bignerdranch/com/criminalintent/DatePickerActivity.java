package android.bignerdranch.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import java.util.Date;

/**
 * Created by Kazakova_net on 30.01.2019.
 */
public class DatePickerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DatePickerFragment();
    }

    public static Intent newIntent(Context packageContext, Date date) {
        Intent intent = new Intent(packageContext, DatePickerActivity.class);
        intent.putExtra(DatePickerFragment.EXTRA_DATE, date);

        return intent;
    }
}
