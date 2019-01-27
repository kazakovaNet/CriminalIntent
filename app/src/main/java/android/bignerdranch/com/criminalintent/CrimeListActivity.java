package android.bignerdranch.com.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Kazakova_net on 27.01.2019.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
