package android.bignerdranch.com.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> crimes;

    private CrimeLab(Context context) {
        crimes = new ArrayList<>();

        // Генерирование тестовых объектов
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();

            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Для каждого второго

            crimes.add(crime);
        }
    }

    static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    List<Crime> getCrimes() {
        return crimes;
    }

    Crime getCrime(UUID id) {
        for (Crime crime : crimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
