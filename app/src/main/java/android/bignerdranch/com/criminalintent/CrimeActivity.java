package android.bignerdranch.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    @Override
    protected Fragment createFragment() {
        // Класс CrimeActivity должен вызывать CrimeFragment.newInstance(UUID) каждый раз,
        // когда ему потребуется создать CrimeFragment.
        // При вызове передается значение UUID, полученное из дополнения
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        // Чтобы сообщить CrimeFragment, какой объект Crime следует отображать,
        // передается идентификатор в дополнении (extra) объекта Intent при запуске CrimeActivity
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }
}
