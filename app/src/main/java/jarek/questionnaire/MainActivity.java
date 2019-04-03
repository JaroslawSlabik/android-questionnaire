package jarek.questionnaire;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    public static final int INTENT_SUMMARY_ACTIVITY_CODE = 1;
    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_SAVE = 1;
    public static final int RESULT_CODE_EDIT = 2;
    public static final String FILE_DATA_NAME = "zapisani_ludzie.txt";

    private IntentDataClipboard clipboard;

    private ListView m_list_view_favourite_activity;
    private String[] m_list_of_titles_favourite_activity;
    private String[] m_list_of_descriptions_favourite_activity;
    private int[] m_list_of_images_favourite_activity = {
            R.drawable.questionnaire_logo,
            R.drawable.questionnaire_logo,
            R.drawable.questionnaire_logo,
            R.drawable.questionnaire_logo,
            R.drawable.questionnaire_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        clipboard = new IntentDataClipboard();

        Resources res = getResources();
        m_list_of_titles_favourite_activity = res.getStringArray(R.array.name_favourite_activity);
        m_list_of_descriptions_favourite_activity = res.getStringArray(R.array.description_favourite_activity);
        m_list_view_favourite_activity = (ListView) findViewById(R.id.m_list_view_for_presentation);

        LayoutRowFavouriteActivityAdapter adapter = new LayoutRowFavouriteActivityAdapter(this, m_list_of_titles_favourite_activity, m_list_of_images_favourite_activity, m_list_of_descriptions_favourite_activity);
        m_list_view_favourite_activity.setAdapter(adapter);
        m_list_view_favourite_activity.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    public void favouriteColorRadioGroupClicked(View view)
    {
        String description = ((RadioButton)view).getText().toString();
        clipboard.setFavouriteColor(description);
    }

    public void favouriteActivitiesCheckGroupClicked(View view)
    {
        String description = ((CheckBox)view).getText().toString();
        clipboard.setFavouriteActivities(description);
    }

    private boolean checkCollectedData()
    {
        if(clipboard.getFavouriteColor().isEmpty())
        {
            Toast.makeText(this, "Uzupełnij ulubiony kolor bo jest wymagane", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(clipboard.getName().isEmpty())
        {
            Toast.makeText(this, "Uzupełnij pole imię bo jest wymagane", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(clipboard.getSurname().isEmpty())
        {
            Toast.makeText(this, "Uzupełnij pole nazwisko bo jest wymagane", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(clipboard.getStreet().isEmpty())
        {
            Toast.makeText(this, "Uzupełnij pole ulica bo jest wymagane", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(clipboard.getCity().isEmpty())
        {
            Toast.makeText(this, "Uzupełnij pole miasto bo jest wymagane", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean collectingData()
    {
        clipboard.setName(((TextView)findViewById(R.id.m_name)).getText().toString());
        clipboard.setSurname(((TextView)findViewById(R.id.m_surname)).getText().toString());
        clipboard.setCity(((TextView)findViewById(R.id.m_city)).getText().toString());
        clipboard.setStreet(((TextView)findViewById(R.id.m_street)).getText().toString());

        DatePicker datePicker = (DatePicker)findViewById(R.id.m_date_born);
        clipboard.setDateBorn(datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());

        return checkCollectedData();
    }

    public void buttonSummaryClicked(View view)
    {
        if( false == collectingData())
        {
            return;
        }

        Intent intent_summary_activity = new Intent(this, SummaryActivity.class);
        intent_summary_activity.putExtra("clipboard", clipboard);
        startActivityForResult(intent_summary_activity, INTENT_SUMMARY_ACTIVITY_CODE);
    }

    private void clearAll()
    {
        ((TextView)findViewById(R.id.m_name)).setText("");
        ((TextView)findViewById(R.id.m_surname)).setText("");
        ((TextView)findViewById(R.id.m_city)).setText("");
        ((TextView)findViewById(R.id.m_street)).setText("");

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        ((DatePicker)findViewById(R.id.m_date_born)).init(year, month, day, null);

        ((RadioGroup)findViewById(R.id.m_favourite_color_radio_group)).clearCheck();

        ((CheckBox)findViewById(R.id.m_check_box_fly)).setChecked(false);
        ((CheckBox)findViewById(R.id.m_check_box_programing)).setChecked(false);
        ((CheckBox)findViewById(R.id.m_check_box_reading)).setChecked(false);
        ((CheckBox)findViewById(R.id.m_check_box_singing)).setChecked(false);
        ((CheckBox)findViewById(R.id.m_check_box_watching)).setChecked(false);

        clipboard.Clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == INTENT_SUMMARY_ACTIVITY_CODE)
        {
            if(resultCode < RESULT_CODE_OK || true != data.hasExtra("clipboard"))
            {
                Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                return;
            }

            clipboard = (IntentDataClipboard)data.getSerializableExtra("clipboard");

            Toast.makeText(this, clipboard.getMessage(), Toast.LENGTH_SHORT).show();

            if(resultCode == RESULT_CODE_SAVE)
            {
                clearAll();
            }
        }
    }

    private void PodsumowanieShowToast()
    {
        String message = "Podsumowanie:";
        message += "\tImię: " + clipboard.getName() + "\n";
        message += "\tNazwisko: " + clipboard.getSurname() + "\n";
        message += "\tUlica: " + clipboard.getStreet() + "\n";
        message += "\tMiasto: " + clipboard.getCity() + "\n";
        message += "\tData urodzenia: " + clipboard.getDateBornAsString() + "\n";
        message += "\tUlubiony kolor: " + clipboard.getFavouriteColor() + "\n";
        message += "\tUlubione zajęcia: \n" + clipboard.getFavouriteActivitiesAsString();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void buttonRefreshClicked(View view)
    {
        clearAll();
    }

    public void buttonLoadLastClicked(View view)
    {
        String last_line = loadLastLineFromFile();
        if("null" == last_line)
        {
            return;
        }

        /*extract arguments from line*/
        int id_name = 0;
        int id_surname = 1;
        int id_city = 2;
        int id_street = 3;
        int id_date_born = 4;
        int id_favourite_color = 5;
        int id_favourite_activities = 6;
        String[] arg_list = new String[7];
        for(int i = 0; i < 7; i++)
        {
            arg_list[i] = "";
        }
        int current_arg = 0;
        for(char c : last_line.toCharArray())
        {
            if(';' == c)
            {
                arg_list[current_arg] = clearFromNullChar(arg_list[current_arg]);
                current_arg++;
                continue;
            }

            arg_list[current_arg] += c;
        }

        clipboard.setName(arg_list[id_name]);
        ((TextView)findViewById(R.id.m_name)).setText(arg_list[id_name]);

        clipboard.setSurname(arg_list[id_surname]);
        ((TextView)findViewById(R.id.m_surname)).setText(arg_list[id_surname]);

        clipboard.setCity(arg_list[id_city]);
        ((TextView)findViewById(R.id.m_city)).setText(arg_list[id_city]);

        clipboard.setStreet(arg_list[id_street]);
        ((TextView)findViewById(R.id.m_street)).setText(arg_list[id_street]);

        /*extract date member from date arg*/
        int id_date_year = 0;
        int id_date_month = 1;
        int id_date_day = 2;
        String date_year = "";
        String date_month = "";
        String date_day = "";
        String[] date_arg_list = new String[3];
        for(int i = 0; i < 3; i++)
        {
            date_arg_list[i] = "";
        }
        int current_date_arg = 0;
        for(char c : arg_list[id_date_born].toCharArray())
        {
            if('-' == c)
            {
                current_date_arg++;
                continue;
            }

            date_arg_list[current_date_arg] += c;
        }
        date_year = date_arg_list[id_date_year];
        date_month = date_arg_list[id_date_month];
        date_day = date_arg_list[id_date_day];
        clipboard.setDateBorn(Integer.parseInt(date_day), Integer.parseInt(date_month), Integer.parseInt(date_year));
        ((DatePicker)findViewById(R.id.m_date_born)).init(Integer.parseInt(date_year), Integer.parseInt(date_month), Integer.parseInt(date_day), null);

        clipboard.setFavouriteColor(arg_list[id_favourite_color]);
        RadioButton radio_button_red_color = (RadioButton)findViewById(R.id.m_red_color);
        RadioButton radio_button_green_color = (RadioButton)findViewById(R.id.m_green_color);
        RadioButton radio_button_blue_color = (RadioButton)findViewById(R.id.m_blue_color);
        String red_checkbox = radio_button_red_color.getText().toString();
        String red_file = arg_list[id_favourite_color];
        if(radio_button_red_color.getText().toString().length() == arg_list[id_favourite_color].length())
        {
            radio_button_red_color.setChecked(true);
        }
        else if(radio_button_green_color.getText().toString().length() == arg_list[id_favourite_color].length())
        {
            radio_button_green_color.setChecked(true);
        }
        else if(radio_button_blue_color.getText().toString().length() == arg_list[id_favourite_color].length())
        {
            radio_button_blue_color.setChecked(true);
        }

        List<String> string_list_activities = new ArrayList<String>();
        String activity_temp = "";
        for(char c : arg_list[id_favourite_activities].toCharArray())
        {
            if(',' == c)
            {
                string_list_activities.add(activity_temp);
                activity_temp = "";
                continue;
            }

            activity_temp += Character.toString(c);
        }

        clipboard.setFavouriteActivities(string_list_activities);
        CheckBox check_box_fly = (CheckBox)findViewById(R.id.m_check_box_fly);
        CheckBox check_box_reading = (CheckBox)findViewById(R.id.m_check_box_reading);
        CheckBox check_box_singing = (CheckBox)findViewById(R.id.m_check_box_singing);
        CheckBox check_box_programing = (CheckBox)findViewById(R.id.m_check_box_programing);
        CheckBox check_box_watching = (CheckBox)findViewById(R.id.m_check_box_watching);
        for(String activity_str : string_list_activities)
        {
            if(check_box_fly.getText().toString().length() == activity_str.length())
            {
                check_box_fly.setChecked(true);
            }
            if(check_box_reading.getText().toString().length() == activity_str.length())
            {
                check_box_reading.setChecked(true);
            }
            if(check_box_singing.getText().toString().length() == activity_str.length())
            {
                check_box_singing.setChecked(true);
            }
            if(check_box_programing.getText().toString().length() == activity_str.length())
            {
                check_box_programing.setChecked(true);
            }
            if(check_box_watching.getText().toString().length() == activity_str.length())
            {
                check_box_watching.setChecked(true);
            }
        }
    }

    private String clearFromNullChar(String in)
    {
        String out = "";
        for(int i = 0; i < in.length(); ++i)
        {
            if(in.charAt(i) == (char)0)
            {
                continue;
            }

            out += in.charAt(i);
        }

        return out;
    }

    private String loadLastLineFromFile()
    {
        String last_line = "";
        try
        {
            String load_string = "";

            //Load from file
            FileInputStream file_in = openFileInput(FILE_DATA_NAME);
            int load_char = 0;
            while((load_char = file_in.read()) != -1)
            {
                if('\n' == (char)load_char)
                {
                    last_line = load_string;
                    load_string = "";
                    continue;
                }

                load_string += Character.toString((char)load_char);
            }
            file_in.close();
            return last_line;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "null";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.m_summary_button_menu:
                if(true == collectingData())
                {
                    PodsumowanieShowToast();
                }
                return true;

            case R.id.m_refresh_button_menu:
                buttonRefreshClicked(null);
                return true;

            case R.id.m_load_last_user_button_menu:
                buttonLoadLastClicked(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, m_list_of_descriptions_favourite_activity[position], Toast.LENGTH_SHORT).show();
    }
}
