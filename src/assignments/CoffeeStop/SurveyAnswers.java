package assignments.CoffeeStop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SurveyAnswers{

	//Data to be inserted into the tables
	
	long id;
	String coffeeShopName;
	int firstAnswer;
	int secondAnswer;
	int thirdAnswer;
	int fourthAnswer;
	
	public SurveyAnswers() {}
	
	//Constructor receives all data and saves it into local variables
	
	public SurveyAnswers(final String coffeeShopName, final String frstA, final String scndA, final String thrdA, final String frthA) {
		this.coffeeShopName = coffeeShopName;
		this.firstAnswer = Integer.parseInt(frstA);
		this.secondAnswer = Integer.parseInt(scndA);
		this.thirdAnswer = Integer.parseInt(thrdA);
		this.fourthAnswer = Integer.parseInt(frthA);
	}
	
	//Saves the data into the database tables
	
	public void save(DatabaseHelper dbHelper) {
		try{
			final ContentValues values = new ContentValues();
			
			values.put(SHOPNAME, this.coffeeShopName);
			values.put(FIRSTQUESTION, this.firstAnswer);
			values.put(SECONDQUESTION, this.secondAnswer);
			values.put(THIRDQUESTION, this.thirdAnswer);
			values.put(FOURTHQUESTION, this.fourthAnswer);
			
			final SQLiteDatabase db = dbHelper.getReadableDatabase();
			this.id = db.insert(SURVEY_TABLE_NAME, null, values);
			
			db.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	//Counts the database rows
	
	public int countRows(DatabaseHelper dbHelper){
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
	
	//Standard getters and setter
	
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
	
	// Column names
	
	static final String ID = "ID";
	static final String SHOPNAME = "Name";
	static final String FIRSTQUESTION= "Service";
	static final String SECONDQUESTION = "Item";
	static final String THIRDQUESTION = "Music";
	static final String FOURTHQUESTION = "Environment";
	
	// Creating the table
	
	public static final String SURVEY_TABLE_NAME = "Answers";	
	public static final String SURVEY_CREATE_TABLE = "CREATE TABLE " + SurveyAnswers.SURVEY_TABLE_NAME + " ("
							+ SurveyAnswers.ID + " INTEGER PRIMARY KEY,"
							+ SurveyAnswers.SHOPNAME + " TEXT,"
							+ SurveyAnswers.FIRSTQUESTION + " INTEGER,"
							+ SurveyAnswers.SECONDQUESTION + " INTEGER,"
							+ SurveyAnswers.THIRDQUESTION + " INTEGER,"
							+ SurveyAnswers.FOURTHQUESTION + " INTEGER"
							+ ");";

}
