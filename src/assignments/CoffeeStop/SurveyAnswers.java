package assignments.CoffeeStop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SurveyAnswers{

	//Consider making them String from the start, to not make meaningless (as of now) conversions.
	//Data to be inserted into the tables
	long id;
	String coffeeShopName;
	int firstAnswer;
	int secondAnswer;
	int thirdAnswer;
	int fourthAnswer;
	
	public SurveyAnswers() {}
	
	public SurveyAnswers(final String coffeeShopName, final String frstA, final String scndA, final String thrdA, final String frthA) {
		this.coffeeShopName = coffeeShopName;
		this.firstAnswer = Integer.parseInt(frstA);
		this.secondAnswer = Integer.parseInt(scndA);
		this.thirdAnswer = Integer.parseInt(thrdA);
		this.fourthAnswer = Integer.parseInt(frthA);
	}
	
	public void save(DatabaseHelper dbHelper) {
		try{
			final ContentValues values = new ContentValues();
			
			values.put(SHOPNAME, this.coffeeShopName);
			values.put(FIRSTQ, this.firstAnswer);
			values.put(SECONDQ, this.secondAnswer);
			values.put(THIRDQ, this.thirdAnswer);
			values.put(FOURTHQ, this.fourthAnswer);
			
			final SQLiteDatabase db = dbHelper.getReadableDatabase();
			this.id = db.insert(SURVEY_TABLE_NAME, null, values);
			
			db.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
		
	
	public int count(DatabaseHelper dbHelper){
		int i=0;
		
		String query = "SELECT * FROM "+ SURVEY_TABLE_NAME;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor cs = db.rawQuery(query, null);
		
		if(cs.moveToFirst()){
			do{
				i++;
			}while(cs.moveToNext());
			
			return i;
		}
		else
			return 0;
	}
	
	public int[] getAverages(DatabaseHelper dbHelper, String name){
		try{
			String subQuery = "SELECT * FROM "+SURVEY_TABLE_NAME+" WHERE Name LIKE '" + name +"'";
			String firstQAvgQuery = "SELECT AVG("+FIRSTQ+") as first, "+
									"AVG("+SECONDQ+") as second, " + 
									"AVG("+THIRDQ+") as third, " +
									"AVG("+FOURTHQ+") as fourth" +
									" FROM("+subQuery+")";
			
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			
			Cursor cursor1 = db.rawQuery(firstQAvgQuery, null);

			int[] i = new int[4];
			
			if(cursor1.moveToFirst()){
				i[0]=cursor1.getInt(cursor1.getColumnIndex("first"));
				i[1]=cursor1.getInt(cursor1.getColumnIndex("second"));
				i[2]=cursor1.getInt(cursor1.getColumnIndex("third"));
				i[3]=cursor1.getInt(cursor1.getColumnIndex("fourth"));
			}
			
			db.close();
			return i;
		}
		catch(Exception ei){
			ei.printStackTrace();
			return null;
		}
	}
	
	//Standard getters and setters

	public String getCoffeeShopName() {
		return coffeeShopName;
	}
	public void setCoffeeShopName(String cShopName) {
		this.coffeeShopName = cShopName;
	}
	
	public int getFirstAnswer() {
		return firstAnswer;
	}
	public void setFirstAnswer(int frstA) {
		this.firstAnswer = frstA;
	}

	public int getSecondAnswer() {
		return secondAnswer;
	}
	public void setSecondAnswer(int scndA) {
		this.secondAnswer = scndA;
	}
	
	public int getThirdAnswer() {
		return thirdAnswer;
	}
	public void setThirdAnswer(int thrdA) {
		this.thirdAnswer = thrdA;
	}
	
	public int getFourthAnswer() {
		return fourthAnswer;
	}
	public void setFourthAnswer(int frthA) {
		this.fourthAnswer = frthA;
	}
	
	// column names
	static final String ID = "ID";
	static final String SHOPNAME = "Name";
	static final String FIRSTQ = "Service";
	static final String SECONDQ = "Item";
	static final String THIRDQ = "Music";
	static final String FOURTHQ = "Environment";
	
	//creating the table
	public static final String SURVEY_TABLE_NAME = "Answers";	
	public static final String SURVEY_CREATE_TABLE = "CREATE TABLE " + SurveyAnswers.SURVEY_TABLE_NAME + " ("
							+ SurveyAnswers.ID + " INTEGER PRIMARY KEY,"
							+ SurveyAnswers.SHOPNAME + " TEXT,"
							+ SurveyAnswers.FIRSTQ + " INTEGER,"
							+ SurveyAnswers.SECONDQ + " INTEGER,"
							+ SurveyAnswers.THIRDQ + " INTEGER,"
							+ SurveyAnswers.FOURTHQ + " INTEGER"
							+ ");";

}
