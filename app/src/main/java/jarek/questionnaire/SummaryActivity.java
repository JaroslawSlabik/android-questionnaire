package jarek.questionnaire;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.io.FileOutputStream;

public class SummaryActivity extends AppCompatActivity
{
    private IntentDataClipboard clipboard;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);

        clipboard = new IntentDataClipboard();

        Intent intent_data = getIntent();
        if(true != intent_data.hasExtra("clipboard"))
        {
            return;
        }

        clipboard = (IntentDataClipboard)intent_data.getSerializableExtra("clipboard");

        ((TextView) findViewById(R.id.m_text_name_value)).setText(clipboard.getName());
        ((TextView) findViewById(R.id.m_text_surname_value)).setText(clipboard.getSurname());
        ((TextView) findViewById(R.id.m_text_city_value)).setText(clipboard.getCity());
        ((TextView) findViewById(R.id.m_text_street_value)).setText(clipboard.getStreet());
        ((TextView) findViewById(R.id.m_text_born_date_value)).setText(clipboard.getDateBornAsString());
        ((TextView) findViewById(R.id.m_text_favorite_color_value)).setText(clipboard.getFavouriteColor());
        ((TextView) findViewById(R.id.m_text_favorite_activities_value)).setText(clipboard.getFavouriteActivitiesAsString());
    }

    public void buttonSaveClicked(View parent)
    {
        boolean was_ok = writeToInternalStorage(clipboard);
        if(true == was_ok)
        {
            Intent intent_data = new Intent();
            clipboard.setMessage("Zapisano");
            intent_data.putExtra("clipboard", clipboard);
            setResult(MainActivity.RESULT_CODE_SAVE, intent_data);
            super.finish();
        }
        else
        {
            Intent intent_data = new Intent();
            clipboard.setMessage("Błąd podczas zapisywania");
            intent_data.putExtra("clipboard", clipboard);
            setResult(MainActivity.RESULT_CODE_SAVE, intent_data);
            super.finish();
        }
    }

    public void buttonEditClicked(View parent)
    {
        Intent intent_data = new Intent();
        clipboard.setMessage("Możesz edytować dane");
        intent_data.putExtra("clipboard", clipboard);
        setResult(MainActivity.RESULT_CODE_EDIT, intent_data);
        super.finish();
    }

    private boolean writeToInternalStorage(IntentDataClipboard intent_data_clipboard)
    {
        try
        {
            FileOutputStream file_out = openFileOutput("zapisani_ludzie.txt", Context.MODE_APPEND);
            String name = intent_data_clipboard.getName() + ";";
            file_out.write(name.getBytes());
            String surname = intent_data_clipboard.getSurname() + ";";
            file_out.write(surname.getBytes());
            String city = intent_data_clipboard.getCity() + ";";
            file_out.write(city.getBytes());
            String street = intent_data_clipboard.getStreet() + ";";
            file_out.write(street.getBytes());
            String date_born = intent_data_clipboard.getDateBorn().m_year + "-" + intent_data_clipboard.getDateBorn().m_month + "-" + intent_data_clipboard.getDateBorn().m_day + ";";
            file_out.write(date_born.getBytes());
            String favourite_color = intent_data_clipboard.getFavouriteColor() + ";";
            file_out.write(favourite_color.getBytes());
            String favourite_activities = "";
            for(String str : intent_data_clipboard.getFavouriteActivities() )
            {
                favourite_activities += str + ",";
            }
            file_out.write(favourite_activities.getBytes());
            String new_line = "\n";
            file_out.write(new_line.getBytes());

            file_out.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
