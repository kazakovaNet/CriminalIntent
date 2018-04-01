package android.bignerdranch.com.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

import static android.widget.CompoundButton.*;

public class CrimeFragment extends Fragment {
    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;

    private static final String ARG_CRIME_ID = "crime_id";

    // Метод получает UUID, создает пакет аргументов,
    // создает экземпляр фрагмента, а затем присоединяет аргументы к фрагменту
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // В простом решении CrimeFragment использует метод getActivity()
        // для прямого обращения к интенту CrimeActivity
//        UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);

        // Получение идентификатора преступления из аргументов (гибкое решение)
        Bundle bundle = getArguments();
        UUID crimeId = null;
        if (bundle != null) {
            crimeId = (UUID) bundle.getSerializable(ARG_CRIME_ID);
        }
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Заполнение представления фрагмента:
        // 1-й параметр: идентификатор ресурса макета
        // 2-й параметр: родитель представления
        // 3-й параметр: нужно ли включать заполненное представление в родителя
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        titleField = v.findViewById(R.id.crime_title);
        titleField.setText(crime.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Здесь намеренно оставлено пустое место
            }
        });

        dateButton = v.findViewById(R.id.crime_date);
        dateButton.setText(crime.getDate().toString());
        // TODO Кнопка заблокирована
        dateButton.setEnabled(false);

        solvedCheckBox = v.findViewById(R.id.crime_solved);
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Сохранение в базу изменяемых объектов Crime
        CrimeLab.get(getActivity()).updateCrime(crime);
    }
}
