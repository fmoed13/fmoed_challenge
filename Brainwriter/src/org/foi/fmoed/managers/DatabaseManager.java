package org.foi.fmoed.managers;

import java.util.ArrayList;
import java.util.List;

import org.foi.fmoed.models.Group;
import org.foi.fmoed.models.Idea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 4;
 
    // Database Name
    private static final String DATABASE_NAME = "brainwriter";
 
    // table names
    public static final String TABLE_GROUP = "tbl_group";
    public static final String TABLE_IDEA = "tbl_idea";
 
    // field names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ROUND = "round";
    public static final String KEY_AUTHOR_NAME = "author_name";
    public static final String KEY_PATH = "path";
    public static final String FK_IDEA_KEY = "group_id";

    /**
     * Main constructor of DatabaseManager
     * @param context Context of application
     */
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		
        String CREATE_GROUP_TABLE = "CREATE TABLE " 
        		+ TABLE_GROUP + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " 
        		+ KEY_NAME + " TEXT, "
        		+ KEY_STATUS + " TEXT, " 
        		+ KEY_ROUND + " TEXT)";

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
	
	/**
	 * Method for adding new record to database
	 * @param values New values to add
	 * @param table Table name
	 * @return Return status
	 */
	public long addRecord(ContentValues values, String table) {
		long status;
		
	    SQLiteDatabase db = this.getWritableDatabase();
	    status = db.insert(table, null, values);
	    db.close();
	    
	    return status;
	}
	
	/**
	 * Method for updating existing row in database
	 * @param values New values
	 * @param table Table to update
	 * @param id Id of row which we want to update
	 * @return Number of affected rows
	 */
	public long updateRecord(ContentValues values, String table, int id) {
		long numRows;
		
	    SQLiteDatabase db = this.getWritableDatabase();
	    numRows = db.update(table, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
	    db.close();
	    
	    return numRows;
	}
	
	/**
	 * Method for deleting row from database
	 * @param table Table name
	 * @param id ID of row which we want to delete
	 * @return Number of affected rows to delete
	 */
	public long deleteRecord(String table, int id) {
		long numRows;
		
	    SQLiteDatabase db = this.getWritableDatabase();
	    numRows = db.delete(table, KEY_ID + " = ?", new String[] { String.valueOf(id) });
	    db.close();
	    
	    return numRows;
	}
	
	/**
	 * Method for getting number of rows in some table
	 * @param table Table
	 * @return Row count
	 */
    public int getRecordsCount(String table) {
        String countQuery = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
 
        return cursor.getCount();
    }
	
	/**
	 * Method for getting list of Group records from database
	 * @return List<Group> 
	 */
	public List<Group> getGroupRecords() {
	    Group group;
		List<Group> groupList = new ArrayList<Group>();
	    String selectQuery = "SELECT  * FROM " + TABLE_GROUP;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	group = new Group();
	        	group.setId(Integer.parseInt(cursor.getString(0)));
	        	group.setName(cursor.getString(1));
	        	
	        	groupList.add(group);
	        } while (cursor.moveToNext());
	    }
	    return groupList;
	}
	
	/**
	 * Method for getting list of Idea records from database
	 * @return List<Idea>
	 */
	public List<Idea> getIdeaRecords() {
		Idea idea;
	    List<Idea> ideaList = new ArrayList<Idea>();
	    String selectQuery = "SELECT  * FROM " + TABLE_IDEA;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    
	    if (cursor.moveToFirst()) {
	        do {
	        	idea = new Idea();
	        	idea.setId(Integer.parseInt(cursor.getString(0)));
	        	idea.setAuthorName(cursor.getString(1));
	        	idea.setPath(cursor.getString(2));
	        	idea.setGroupID(Integer.parseInt(cursor.getString(3)));
	        	
	        	ideaList.add(idea);
	        } while (cursor.moveToNext());
	    }
	    return ideaList;
	}
}