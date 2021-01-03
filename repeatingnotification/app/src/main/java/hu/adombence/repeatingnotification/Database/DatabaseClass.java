package hu.adombence.repeatingnotification.Database;

import android.content.Context;

import androidx.room.RoomDatabase;

public abstract class DatabaseClass extends RoomDatabase {
    private static DatabaseClass INSTANCE;

    public abstract EventDao EventDao();

    public static DatabaseClass getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseClass.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseClass.class,
                                    "product_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}