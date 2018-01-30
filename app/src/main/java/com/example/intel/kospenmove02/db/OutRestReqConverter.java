package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class OutRestReqConverter {

    public enum OutRestReq {
        UPDATEFROMLOCAL(1),
        UPDATEFROMEXTERNAL(2),
        NEWKOSPENUSER(3);

        private final int outRestReqCode;

        OutRestReq(int outRestReqCode) {
            this.outRestReqCode = outRestReqCode;
        }

        public int getOutRestReqCode() {
            return this.outRestReqCode;
        }
    }

    @TypeConverter
    public static OutRestReq toEnumOutRestReq(int outRestReqInt) {

        final OutRestReq outRestReq;

        switch (outRestReqInt) {
            case 1:
                outRestReq = OutRestReq.UPDATEFROMLOCAL;
                break;
            case 2:
                outRestReq = OutRestReq.UPDATEFROMEXTERNAL;
                break;
            case 3:
                outRestReq = OutRestReq.NEWKOSPENUSER;
                break;
            default:
                outRestReq = null;
        }

        return outRestReq;
    }

    @TypeConverter
    public static int toIntOutRestReq(OutRestReq outRestReqEnum) {

        final int outRestReq;

        switch (outRestReqEnum) {
            case UPDATEFROMLOCAL:
                outRestReq = 1;
                break;
            case UPDATEFROMEXTERNAL:
                outRestReq = 2;
                break;
            case NEWKOSPENUSER:
                outRestReq = 3;
                break;
            default:
                outRestReq = 0;
        }

        return outRestReq;
    }

}
