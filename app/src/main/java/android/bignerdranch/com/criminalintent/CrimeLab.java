package android.bignerdranch.com.criminalintent;

import android.bignerdranch.com.criminalintent.database.CrimeBaseHelper;
import android.bignerdranch.com.criminalintent.database.CrimeCursorWrapper;
import android.bignerdranch.com.criminalintent.database.CrimeDbSchema;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.bignerdranch.com.criminalintent.database.CrimeDbSchema.*;

class CrimeLab {
    private static CrimeLab crimeLab;
    private Context context;
    private SQLiteDatabase database;

    private CrimeLab(Context context) {
        this.context = context.getApplicationContext();
        // Создание базы данных
        this.database = new CrimeBaseHelper(this.context).getWritableDatabase();
    }

    static CrimeLab get(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);

        database.insert(CrimeTable.NAME, null, values);
    }

    List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }


        return crimes;
    }

    Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();

            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();

        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        database.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = database.query(
                CrimeTable.NAME,
                null, // выбираются все столбцы
                whereClause,
                whereArgs,
                null,
                null,
                null

        );

        return new CrimeCursorWrapper(cursor);
    }
}
