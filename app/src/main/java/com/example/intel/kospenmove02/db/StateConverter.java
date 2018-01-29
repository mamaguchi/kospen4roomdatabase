package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.TypeConverter;


public class StateConverter {

    public enum State {
        PAHANG(1),
        NONPAHANG(2);

        private final int stateCode;

        State(int stateCode) {
            this.stateCode = stateCode;
        }

        public int getStateCode() {
            return this.stateCode;
        }
    }

    @TypeConverter
    public static State toEnumState(int stateInt) {

        final State state;

        switch (stateInt) {
            case 1:
                state = State.PAHANG;
                break;
            case 2:
                state = State.NONPAHANG;
                break;
            default:
                state = State.PAHANG;
        }

        return state;
    }

    @TypeConverter
    public static int toIntState(State stateEnum) {

        final int state;

        switch (stateEnum) {
            case PAHANG:
                state = 1;
                break;
            case NONPAHANG:
                state = 2;
                break;
            default:
                state = 1;
        }

        return state;
    }

}
