package top.andnux.chain.core.database;

import android.app.Application;

import androidx.room.Room;

public class DatabaseManager {

    private static final DatabaseManager ourInstance = new DatabaseManager();
    private AppDatabase mAppDatabase;

    public static DatabaseManager getInstance() {
        return ourInstance;
    }

    private DatabaseManager() {

    }

    public void init(Application app) {
        mAppDatabase = Room.databaseBuilder(app,
                AppDatabase.class, "android.db")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
