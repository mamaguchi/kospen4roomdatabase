package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class SubregionConverter {

    public enum Subregion {
        JENGKA2(1),
        MARAN(2);

        private final int subregionCode;

        Subregion(int subregionCode) {
            this.subregionCode = subregionCode;
        }

        public int getSubregionCode() {
            return this.subregionCode;
        }
    }

    @TypeConverter
    public static Subregion toEnumSubregion(int subregionInt) {

        final Subregion subregion;

        switch (subregionInt) {
            case 1:
                subregion = Subregion.JENGKA2;
                break;
            case 2:
                subregion = Subregion.MARAN;
                break;
            default:
                subregion = Subregion.JENGKA2;
        }

        return subregion;
    }

    @TypeConverter
    public static int toIntSubregion(Subregion subregionEnum) {

        final int subregion;

        switch (subregionEnum) {
            case JENGKA2:
                subregion = 1;
                break;
            case MARAN:
                subregion = 2;
                break;
            default:
                subregion = 1;
        }

        return subregion;
    }

}
