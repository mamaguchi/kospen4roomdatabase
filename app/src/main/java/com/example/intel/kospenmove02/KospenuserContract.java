package com.example.intel.kospenmove02;

import android.net.Uri;
import android.provider.BaseColumns;

public class KospenuserContract {

    // ============================
    //         PROVIDER level
    // ============================

    // 'AUTHORITY' represents 'provider'
    public static final String AUTHORITY = "com.kospen.kospenusersprovider.provider";

    // CONTENT_URI for 'provider' level
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);


    // ============================
    //      Database TABLE level
    //
    // for every table in database
    // that you want to expose via
    // this provider, you have to
    // create a 'public static class'
    // that implements BaseColumns for
    // each table.
    // ============================

    public static class Kospenusers implements BaseColumns {

        // Name for 'kospenusers' table
        public static final String TABLE_NAME = "kospenusers";

        // CONTENT_URI for database Table level
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/kospenusers");

        // MIME type of CONTENT_URI for database Table->multiple rows
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.com.example.intel.kospenmove02.kospenusers";

        // MIME type of CONTENT_URI for database Table->row
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.com.example.intel.kospenmove02.kospenusers";

        // ============================
        //  Database Table COLUMN level
        //
        // we need to create a contract
        // class constant for every column
        // in this database table, in order
        // to provide a constant interface
        // to underlying database table Column
        // name that can change time to time,
        // for ContentProvider user to form a
        // well-formed Uri when calling methods
        // on ContentResolver.
        // ============================

        //  Constant to '_ID' column
        public static final String _ID = BaseColumns._ID;

        // Constant to 'name' column
        public static final String NAME = "name";

    }


}

































