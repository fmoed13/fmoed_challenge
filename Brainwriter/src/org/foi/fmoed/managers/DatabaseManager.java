package org.foi.fmoed.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "brainwriter";
 
    // table names
    public static final String TABLE_GROUP = "tbl_group";
    public static final String TABLE_IDEA = "tbl_idea";
 
    // field names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AUTHOR_NAME = "author_name";
    public static final String KEY_PATH = "path";
    public static final String FK_IDEA_KEY = "group_id";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		//db.execSQL("DROP TABLE IF EXISTS group");
		//db.execSQL("DROP TABLE IF EXISTS idea");
		
        String CREATE_GROUP_TABLE = "CREATE TABLE " 
        		+ TABLE_GROUP + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " 
        		+ KEY_NAME + " TEXT)";

        String CREATE_IDEA_TABLE = "CREATE TABLE " 
        		+ TABLE_IDEA + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " 
        		+ KEY_AUTHOR_NAME + " TEXT, " 
        		+ KEY_PATH + " TEXT, "
        		+ FK_IDEA_KEY + " INTEGER, FOREIGN KEY(" + FK_IDEA_KEY + ") REFERENCES tbl_group(id))";
        
        db.execSQL(CREATE_GROUP_TABLE);
        db.execSQL(CREATE_IDEA_TABLE);
        Log.i("database", "created...");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
 
        // Create tables again
        onCreate(db);
	}
	
	public void addRecord(ContentValues values, String table) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.insert(table, null, values);
	    db.close();
	}
}