package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class InDBQueryConverter {

    public enum InDBQuery {
        LOCALKOSPENUSERUPDATE(1),
        NEWKOSPENUSERINSERT(2);

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
                inDBQuery = InDBQuery.LOCALKOSPENUSERUPDATE;
                break;
            case 2:
                inDBQuery = InDBQuery.NEWKOSPENUSERINSERT;
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
            case LOCALKOSPENUSERUPDATE:
                inDBQuery = 1;
                break;
            case NEWKOSPENUSERINSERT:
                inDBQuery = 2;
                break;
            default:
                inDBQuery = 0;
        }

        return inDBQuery;
    }

}
