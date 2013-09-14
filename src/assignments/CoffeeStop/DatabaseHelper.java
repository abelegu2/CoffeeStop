package assignments.CoffeeStop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	public static final String DATABASE_NAME = "Answers.db";
	public static final int DATABASE_VERSION = 1;
	
	/**
	 * Sets up db helper.
	 * @param ctx activity context
	 */
	public DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SurveyAnswers.SURVEY_CREATE_TABLE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO How did our DB change? Have we added new column? Renamed the column? 
		// Now - handle the change.
	}
}
