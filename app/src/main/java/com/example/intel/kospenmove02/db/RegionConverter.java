package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class RegionConverter {

    public enum Region {
        MARAN(1),
        JERANTUT(2);

        private final int regionCode;

        Region(int regionCode) {
            this.regionCode = regionCode;
        }

        public int getRegionCode() {
            return this.regionCode;
        }
    }

    @TypeConverter
    public static Region toEnumRegion(int regionInt) {

        final Region region;

        switch (regionInt) {
            case 1:
                region = Region.MARAN;
                break;
            case 2:
                region = Region.JERANTUT;
                break;
            default:
                region = Region.MARAN;
        }

        return region;
    }

    @TypeConverter
    public static int toIntRegion(Region regionEnum) {

        final int region;

        switch (regionEnum) {
            case MARAN:
                region = 1;
                break;
            case JERANTUT:
                region = 2;
                break;
            default:
                region = 1;
        }

        return region;
    }

}
