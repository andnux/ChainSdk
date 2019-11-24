package top.andnux.chain.core.database;

import androidx.room.TypeConverter;

import java.util.Date;

import top.andnux.chain.core.Env;
import top.andnux.chain.core.State;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static State dbToState(String value) {
        return value == null ? null : State.valueOf(value);
    }

    @TypeConverter
    public static String stateToDb(State state) {
        return state == null ? null : state.name();
    }

    @TypeConverter
    public static Env dbToEnv(String value) {
        return value == null ? null : Env.valueOf(value);
    }

    @TypeConverter
    public static String envToDb(Env env) {
        return env == null ? null : env.name();
    }
}
