package android.bignerdranch.com.criminalintent.database;

public class CrimeDbSchema {
    /**
     * Класс для определения строковых констант,
     * необходимых для описания основных частей определения таблицы.
     */
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
