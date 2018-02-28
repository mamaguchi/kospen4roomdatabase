package com.example.intel.kospenmove02.db.converter;


import android.arch.persistence.room.TypeConverter;


@Deprecated
public class GenderConverter {

    public enum Gender {
        MALE(1),
        FEMALE(2);

        private final int genderCode;

        Gender(int genderCode) {
            this.genderCode = genderCode;
        }

        public int getGenderCode() {
            return this.genderCode;
        }
    }

    public static Gender strToEnumGender(String genderStr) {

        final Gender gender;

        switch (genderStr) {
            case "MALE":
                gender = Gender.MALE;
                break;
            case "FEMALE":
                gender = Gender.FEMALE;
                break;
            default:
                gender = null;
        }
        return gender;
    }

    @TypeConverter
    public static Gender toEnumGender(int genderInt) {

        final Gender gender;

        switch (genderInt) {
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            default:
                gender = Gender.MALE;
        }
        return gender;
    }

    @TypeConverter
    public static int toIntGender(Gender genderEnum) {

        final int gender;

        switch (genderEnum) {
            case MALE:
                gender = 1;
                break;
            case FEMALE:
                gender = 2;
                break;
            default:
                gender = 1;
        }
        return gender;
    }

}
