package com.example.intel.kospenmove02;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

public class KospenusersProvider extends ContentProvider {

    // AUTHORITY for content provider
    public static final String AUTHORITY = "com.kospen.kospenusersprovider.provider";

    // ==========================================
    //  Database TABLE Content_uri Registration
    //
    // for every database table that will be exposed
    // via this content provider, we need to assign
    // a number code for multiple-rows(DIR) and
    // a single-row(ITEM) for each table, using UriMatcher addURI().
    // Purpose is so that based on the code matching, we
    // can further decorate the 'projection', 'selection',
    // and 'selectionArgs' argument for CRUD method call of
    // content provider.
    // ==========================================
    /** Match code for multiple items in 'kospenusers' table */
    private static final int CODE_KOSPENUSERS_DIR = 1;

    /** Match code for an item in 'kospenusers' table */
    private static final int CODE_KOSPENUSERS_ITEM = 2;

    /** The URI matcher */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(AUTHORITY, KospenuserContract.Kospenusers.TABLE_NAME, CODE_KOSPENUSERS_DIR);
        MATCHER.addURI(AUTHORITY, KospenuserContract.Kospenusers.TABLE_NAME + "/*", CODE_KOSPENUSERS_ITEM);
    }
    // ========================================== END - ContentUri registration



    // ==========================================
    //                  DBHandler
    // ==========================================
    protected static final class MyDBHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "kospenDB.db";
        private static final String TABLE_KOSPENUSERS = "kospenusers";
        private static final String COLUMN_ID = "_ID";
        private static final String COLUMN_NAME = "name";


        public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE " + TABLE_KOSPENUSERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT" +
                    ");";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_KOSPENUSERS);
            onCreate(sqLiteDatabase);
        }

    }
    /// ========================================== END - DBHandler

    private MyDBHandler mDatabaseHandler;
    private SQLiteDatabase mDb;

    public KospenusersProvider() {
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // handle query requests from clients.
        String TABLE_TO_QUERY;

        switch(MATCHER.match(uri)) {
            case 1: // Table: 'kospenusers', all-rows
                TABLE_TO_QUERY = "kospenusers";
                if(TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;

            case 2: // Table: 'kospenusers', single-row
                TABLE_TO_QUERY = "kospenusers";
                if(TextUtils.isEmpty(selection)) {
                    selection = selection + "_ID = " + uri.getLastPathSegment();
                }
//                else {
//                    selection = selection + "AND _ID = " + uri.getLastPathSegment();
//                }
                break;

            default:
                throw new IllegalArgumentException("No such database table exists!");
        }

        mDb = mDatabaseHandler.getReadableDatabase();
        final Cursor cursor = mDb.query(TABLE_TO_QUERY, projection, selection, selectionArgs, null, null, sortOrder);
//        mDb.close();

        return cursor;
    }



    @Override
    public boolean onCreate() {
        // initialize your content provider's database(repository) on startup.
        mDatabaseHandler = new MyDBHandler(
                getContext(),
                MyDBHandler.TABLE_KOSPENUSERS,
                null,
                MyDBHandler.DATABASE_VERSION
        );

        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // handle requests to insert a new row.
        String TABLE_TO_INSERT_TO;
        long newlyInsertedRowId;

        switch(MATCHER.match(uri)) {
            case 1: // Table: 'kospenusers', all-rows
                TABLE_TO_INSERT_TO = "kospenusers";
                break;

            case 2: // Table: 'kospenusers', single-row
                TABLE_TO_INSERT_TO = "kospenusers";
                break;

            default:
                throw new IllegalArgumentException("No such database table exists!");
        }

        mDb = mDatabaseHandler.getWritableDatabase();
        newlyInsertedRowId = mDb.insert(TABLE_TO_INSERT_TO, null, values);
//        mDb.close();

        return ContentUris.withAppendedId(KospenuserContract.Kospenusers.CONTENT_URI, newlyInsertedRowId);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // handle requests to update one or more rows.
        String TABLE_TO_UPDATE_TO;

        switch (MATCHER.match(uri)) {
            case 1: // Table: 'kospenusers', multiple-rows
                TABLE_TO_UPDATE_TO = "kospenusers";
                break;

            case 2: // Table: 'kospenusers', single-row
                TABLE_TO_UPDATE_TO = "kospenusers";
                if(TextUtils.isEmpty(selection)) {
                    selection = selection + "_ID = " + uri.getLastPathSegment();
                } else {
                    selection = selection + "AND _ID = " + uri.getLastPathSegment();
                }
                break;

            default:
                throw new IllegalArgumentException("No such database table exists!");
        }

        mDb = mDatabaseHandler.getWritableDatabase();

        final int numRowsUpdated = mDb.update(TABLE_TO_UPDATE_TO, values, selection, selectionArgs);
//        mDb.close();

        return numRowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // handle requests to delete one or more rows.
        String TABLE_TO_DELETE_TO;

        switch(MATCHER.match(uri)) {
            case 1: // Table: 'kospenusers', multiple-rows
                TABLE_TO_DELETE_TO = "kospenusers";
                break;

            case 2: // Table: 'kospenusers', single-row
                TABLE_TO_DELETE_TO = "kospenusers";
                if(TextUtils.isEmpty(selection)) {
                    selection = selection + "_ID = " + uri.getLastPathSegment();
                } else {
                    selection = selection + "AND _ID = " + uri.getLastPathSegment();
                }
                break;

            default:
                throw new IllegalArgumentException("No such database table exists!");
        }

        mDb = mDatabaseHandler.getWritableDatabase();

        final int numRowsDeleted = mDb.delete(TABLE_TO_DELETE_TO, selection, selectionArgs);
//        mDb.close();

        return numRowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // handle requests for the MIME type of the data
        // at the given URI.
        switch(MATCHER.match(uri)) {
            case CODE_KOSPENUSERS_DIR:
                return "vnd.android.cursor.dir/" + "vnd." + AUTHORITY + "." +
                        KospenuserContract.Kospenusers.TABLE_NAME;

            case CODE_KOSPENUSERS_ITEM:
                return "vnd.android.cursor.item/" + "vnd." + AUTHORITY + "." +
                        KospenuserContract.Kospenusers.TABLE_NAME;

            default:
                throw new IllegalArgumentException("No such database table exists!");
        }
    }











//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

//    @Override
//    public String getType(Uri uri) {
//        // TODO: Implement this to handle requests for the MIME type of the data
//        // at the given URI.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        // TODO: Implement this to handle requests to insert a new row.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

//    @Override
//    public boolean onCreate() {
//        // TODO: Implement this to initialize your content provider on startup.
//        return false;
//    }

//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        // TODO: Implement this to handle requests to update one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
