package assignments.CoffeeStop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CsSurveyActivity extends Activity{

	public static String shopName;
	boolean contentChanged= false;
	SurveyAnswers surveyAnswers = new SurveyAnswers();
	public DatabaseHelper dbHelper = new DatabaseHelper(CsSurveyActivity.this);
	
	//Saves shared preferences soon as it starts
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_cs_survey);
	    shopName=isRadioButtonClicked(); //= The text in the selected radio button in activity_cs_survey
	    
	    try{
	    	saveLastProgress();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    int count = surveyAnswers.countRows(dbHelper);
	    TextView txtTotal = (TextView)findViewById(R.id.txtTotal);
	    
	    //Displays the text that shows the number of DB rows.
	    txtTotal.setText("So far there have been "+count+" surveys submitted. Thank you all!");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try{
			saveLastProgress();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try{
			saveLastProgress();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		try{
			getLastProgress();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try{
			getLastProgress();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	
	public void onStartBtnClick(View v){
		//Condition that checks whether or not a radio button has been clicked
		//If not, it prompts a message and doesn't let you move forward.
		if(isRadioButtonClicked().equals("")){
			Toast.makeText(this, "Please select a shop before you continue!", Toast.LENGTH_SHORT).show();
		}
		else{
			setContentView(R.layout.activity_cs_surveyq);
			contentChanged = true;
		}
	}
	
	//Retrieves the information the user gives and saves them into local variables.
	//Sends those variables as parameter to the SurveyAnswers constructor which later are used to insert into the DB.
	public void onFinishBtnClick(View v){
		try{ 
			int serviceA = ((RatingBar)findViewById(R.id.ratingBar1)).getProgress();
			int itemA = ((RatingBar)findViewById(R.id.ratingBar2)).getProgress();
			int musicA = ((RatingBar)findViewById(R.id.ratingBar3)).getProgress();
			int envirA = ((RatingBar)findViewById(R.id.ratingBar4)).getProgress();
			
			SurveyAnswers surveyAnswers = new SurveyAnswers(shopName, Integer.toString(serviceA), Integer.toString(itemA), 
															Integer.toString(musicA), Integer.toString(envirA));
			surveyAnswers.save(dbHelper);
			
			showAlertMessage("Thank you for submitting your review!", v);			
			shopName="";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}	
	
	//Saves latest SharedPreferences.
	public void saveLastProgress(){
		if(contentChanged){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			int serviceQ = ((RatingBar)findViewById(R.id.ratingBar1)).getProgress();
			int itemQ = ((RatingBar)findViewById(R.id.ratingBar2)).getProgress();
			int musicQ = ((RatingBar)findViewById(R.id.ratingBar3)).getProgress();
			int envirQ = ((RatingBar)findViewById(R.id.ratingBar4)).getProgress();
			
			SharedPreferences.Editor editor = prefs.edit();
		    editor.putInt("serviceQ", serviceQ);
		    editor.putInt("itemQ", itemQ);
		    editor.putInt("musicQ", musicQ);
		    editor.putInt("envirQ", envirQ);
		    editor.putString("name", shopName);
		    
		    String check = isRadioButtonClicked();
		    RadioButton r1 = (RadioButton)findViewById(R.id.btn1);
			RadioButton r2 = (RadioButton)findViewById(R.id.btn2);
			RadioButton r3 = (RadioButton)findViewById(R.id.btn3);
			
			if(r1.getText().equals(check)){
				editor.putBoolean("radioButton", r1.isChecked());
			}
			else if(r2.getText().equals(check)){
				editor.putBoolean("radioButton", r2.isChecked());
			}
			else{
				editor.putBoolean("radioButton", r3.isChecked());
			}
		    editor.commit();
		}		
	}
	
	//Gets latest SharedPreferences and replaces values accordingly. 
	public void getLastProgress(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int serviceQ = prefs.getInt("serviceQ", 0);
		int itemQ = prefs.getInt("itemQ", 0);
		int musicQ = prefs.getInt("musicQ", 0);
		int envirQ = prefs.getInt("envirQ", 0);
		
		((RatingBar)findViewById(R.id.ratingBar1)).setProgress(serviceQ);
		((RatingBar)findViewById(R.id.ratingBar2)).setProgress(itemQ);
		((RatingBar)findViewById(R.id.ratingBar3)).setProgress(musicQ);
		((RatingBar)findViewById(R.id.ratingBar4)).setProgress(envirQ);
		shopName = prefs.getString("name", "");
		
		RadioButton r1 = (RadioButton)findViewById(R.id.btn1);
		RadioButton r2 = (RadioButton)findViewById(R.id.btn2);
		RadioButton r3 = (RadioButton)findViewById(R.id.btn3);
		
		if(r1.getText().equals(shopName)){
			r1.setChecked(true);
		}
		else if(r2.getText().equals(shopName)){
			r2.setChecked(true);
		}
		else{
			r3.setChecked(true);
		}
		
	}
	
	//Shows a generic MessageBox.
	public void showAlertMessage(String msg) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage(msg);
		dlgAlert.setPositiveButton("OK", null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	//Shows a MessageBox which also receives a View parameter. 
	//Serves to return back to the Main activity after PositiveButton has been clicked.
	
	public void showAlertMessage(String msg, final View v) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage(msg);
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
		    	backToMain(v);
		    }
		});
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}
	
	//Returns user back to Main activity.
	
	public void backToMain(View v)
	{
		Intent I =new Intent(this, CsMainActivity.class);
		this.startActivity(I);
	}
	
	//Retreives the text of the RadioButtons depending on which one has been clicked. 
	public String isRadioButtonClicked()
	{
		RadioButton r1 = (RadioButton)findViewById(R.id.btn1);
		RadioButton r2 = (RadioButton)findViewById(R.id.btn2);
		RadioButton r3 = (RadioButton)findViewById(R.id.btn3);
		
		String check="";
		
		if(r1.isChecked())
		{
			check= r1.getText().toString();	
		}
		else if(r2.isChecked())
		{
			check=r2.getText().toString();	
		}
		else if(r3.isChecked())
		{
			check=r2.getText().toString();
		}
		return check;
	}
	
	//HelpImage that explains to the user how the rating works if needed.
	public void onImageBtnClick(View v){
		showAlertMessage("0 - Undecided \n1 - Horrible! \n2 - Have seen better \n3 - It's okay \n4 - Great! \n5 - Amazing!!");
	}
}  
