package android.bignerdranch.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
    private ViewPager viewPager;
    private List<Crime> crimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        viewPager = findViewById(R.id.crime_view_pager);

        crimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Адаптером назначается безымянный экземпляр FragmentStatePagerAdapter.
        // Для создания FragmentStatePagerAdapter необходим объект FragmentManager.
        // FragmentStatePagerAdapter — агент, управляющий взаимодействием с ViewPager.
        // Чтобы агент мог выполнить свою работу с фрагментами, возвращаемыми в getItem(int),
        // он должен быть способен добавить их в активность.
        // Вот почему ему необходим экземпляр FragmentManager.
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            /**
             * Получает экземпляр Crime для заданной позиции в наборе данных,
             * после чего использует его идентификатор для создания и возвращения
             * правильно настроенного экземпляра CrimeFragment.
             * @param position текущая позиция в наборе данных
             * @return экземпляр {@link CrimeFragment}
             */
            @Override
            public Fragment getItem(int position) {
                Crime crime = crimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            /**
             * @return Возвращает текущее количество элементов в списке
             */
            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        // По умолчанию ViewPager отображает в своем экземпляре PagerAdapter первый элемент.
        // Чтобы вместо него отображался элемент, выбранный пользователем,
        // текущим элементом ViewPager назначается элемент с указанным индексом.
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(crimeId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
