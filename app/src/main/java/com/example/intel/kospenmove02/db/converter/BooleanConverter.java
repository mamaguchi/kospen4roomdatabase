package com.example.intel.kospenmove02.db.converter;


import android.arch.persistence.room.TypeConverter;

public class BooleanConverter {

    @TypeConverter
    public static boolean toBoolean(int booleanInt) {
        return booleanInt != 0;
    }

    @TypeConverter
    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }

}
