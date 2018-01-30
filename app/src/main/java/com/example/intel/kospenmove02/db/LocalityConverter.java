package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class LocalityConverter {

    public enum Locality {
        ULUJEMPOL(1),
        JENGKA6(2);

        private final int localityCode;

        Locality(int localityCode) {
            this.localityCode = localityCode;
        }

        public int getLocalityCode() {
            return this.localityCode;
        }
    }

    public static Locality strToEnumLocality(String localityStr) {

        final Locality locality;

        switch (localityStr) {
            case "ULUJEMPOL":
                locality = Locality.ULUJEMPOL;
                break;
            case "JENGKA6":
                locality = Locality.JENGKA6;
                break;
            default:
                locality = null;
        }
        return locality;
    }

    @TypeConverter
    public static Locality toEnumLocality(int localityInt) {

        final Locality locality;

        switch (localityInt) {
            case 1:
                locality = Locality.ULUJEMPOL;
                break;
            case 2:
                locality = Locality.JENGKA6;
                break;
            default:
                locality = Locality.ULUJEMPOL;
        }

        return locality;
    }

    @TypeConverter
    public static int toIntLocality(Locality localityEnum) {

        final int locality;

        switch (localityEnum) {
            case ULUJEMPOL:
                locality = 1;
                break;
            case JENGKA6:
                locality = 2;
                break;
            default:
                locality = 1;
        }

        return locality;
    }

}
