package com.example.intel.kospenmove02.db.converter;

import android.arch.persistence.room.TypeConverter;


public class InDBQueryConverter {

    public enum InDBQuery {
        LocalKospenuserUpdateFrmInsideLocality(1),
        LocalKospenuserUpdateFrmOutsideLocality(2),
        KospenuserInsideLocalityNotInAndroidDb(3);
        private final int inDBQueryCode;

        InDBQuery(int inDBQueryCode) {
            this.inDBQueryCode = inDBQueryCode;
        }

        public int getInDBQueryCode() {
            return this.inDBQueryCode;
        }
    }

    @TypeConverter
    public static InDBQuery toEnumInDBQuery(int inDBQueryInt) {

        final InDBQuery inDBQuery;

        switch (inDBQueryInt) {
            case 1:
                inDBQuery = InDBQuery.LocalKospenuserUpdateFrmInsideLocality;
                break;
            case 2:
                inDBQuery = InDBQuery.LocalKospenuserUpdateFrmOutsideLocality;
                break;
            case 3:
                inDBQuery = InDBQuery.KospenuserInsideLocalityNotInAndroidDb;
                break;
            default:
                inDBQuery = null;
        }

        return inDBQuery;
    }

    @TypeConverter
    public static int toIntInDBQuery(InDBQuery inDBQueryEnum) {

        final int inDBQuery;

        switch (inDBQueryEnum) {
            case LocalKospenuserUpdateFrmInsideLocality:
                inDBQuery = 1;
                break;
            case LocalKospenuserUpdateFrmOutsideLocality:
                inDBQuery = 2;
                break;
            case KospenuserInsideLocalityNotInAndroidDb:
                inDBQuery = 3;
                break;
            default:
                inDBQuery = 0;
        }

        return inDBQuery;
    }

}
