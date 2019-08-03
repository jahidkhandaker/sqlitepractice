package nsu.sqlitepractice;

import android.annotation.SuppressLint;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mName;
    private EditText mid;
    private EditText mDay;
    private EditText mSlot;
    private Button mSubmit;
    private Button mDisplayButton;
    private Button mDelButton;
    private Button mUpdate;


    DatabaseHelper mDatabaseHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        mName = (EditText) findViewById(R.id.name);
        mid = (EditText) findViewById(R.id.id);
        mDay = (EditText) findViewById(R.id.day);
        mSlot = (EditText) findViewById(R.id.slot);
        mSubmit = (Button) findViewById(R.id.submit);
        mDisplayButton = (Button) findViewById(R.id.Displaybutton);
        mDelButton = (Button) findViewById(R.id.Delbutton);
        mUpdate = (Button) findViewById(R.id.Updatebutton);


        mSubmit.setOnClickListener(this);
        mDisplayButton.setOnClickListener(this);
        mDelButton.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
    }

    public void onClick(View view){

        String name = mName.getText().toString();
        String id = mid.getText().toString();
        String day = mDay.getText().toString();
        String slot = mSlot.getText().toString();

        if (view.getId()==R.id.submit)
        {
            Cursor results = mDatabaseHelper.searchData(name,day,slot);

            if (results.getCount()==0)
            {
                long rowId = mDatabaseHelper.insertData(name,id,day,slot);
                if (rowId==-1)
                {

                    Toast.makeText(getApplicationContext(),"Unsuccessfull ", Toast.LENGTH_LONG).show();

                } else {
                    mName.setText(null);
                    mid.setText(null);
                    mDay.setText(null);
                    mSlot.setText(null);
                    Toast.makeText(getApplicationContext(),"Row "+rowId+"Success fully Allocated ", Toast.LENGTH_LONG).show();

                }

            }

           else {
                Toast.makeText(getApplicationContext(),"Teacher isn't free ", Toast.LENGTH_LONG).show();
            }

        }

        if (view.getId()==R.id.Displaybutton)
        {
            Cursor results = mDatabaseHelper.displayAllData();

               if (results.getCount()==0)
               {
                   showData("Error","No Data Found");

                    return;
               }
               StringBuffer stringBuffer = new StringBuffer();
               while(results.moveToNext())
               {
                   stringBuffer.append("ID : "+ results.getString(0)+"\n");
                   stringBuffer.append("Name : "+ results.getString(1)+"\n");
                   stringBuffer.append("Day : "+ results.getString(2)+"\n");
                   stringBuffer.append("Slot : "+ results.getString(2)+"\n\n");
               }
                showData("ResultSet", stringBuffer.toString());
        }


        if (view.getId()==R.id.Delbutton)
        {
            int value= mDatabaseHelper.deleteData(id);

            if (value==1)
            {
               clearText();
                Toast.makeText(getApplicationContext(),"Deleted ", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(),"Unsuccessfull ", Toast.LENGTH_LONG).show();

            }
        }

        if (view.getId()==R.id.Updatebutton)
        {
            Boolean isUpdated = (Boolean) mDatabaseHelper.updateData(id,name,day,slot);

            if (isUpdated==true)
            {
                Toast.makeText(getApplicationContext(),"Updated ", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(),"Unsuccessfull ", Toast.LENGTH_LONG).show();

            }
            clearText();
        }


    } //OnClick Close

        private void showData(String title, String Message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(Message);
            builder.setCancelable(true);
            builder.show();

        } //showData

    private void clearText(){
        mName.setText(null);
        mid.setText(null);
        mDay.setText(null);
        mSlot.setText(null);
    }

} //Class
