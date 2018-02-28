package com.example.intel.kospenmove02.db.converter;

import android.arch.persistence.room.TypeConverter;


public class OutRestReqConverter {

    public enum OutRestReq {
        UpdateServerFrmLocal(1),
        UpdateServerFrmGlobal(2),
        UpdateServerNewKospenuser(3);

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
                outRestReq = OutRestReq.UpdateServerFrmLocal;
                break;
            case 2:
                outRestReq = OutRestReq.UpdateServerFrmGlobal;
                break;
            case 3:
                outRestReq = OutRestReq.UpdateServerNewKospenuser;
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
            case UpdateServerFrmLocal:
                outRestReq = 1;
                break;
            case UpdateServerFrmGlobal:
                outRestReq = 2;
                break;
            case UpdateServerNewKospenuser:
                outRestReq = 3;
                break;
            default:
                outRestReq = 0;
        }

        return outRestReq;
    }

}
