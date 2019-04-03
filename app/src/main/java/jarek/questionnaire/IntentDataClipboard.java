package jarek.questionnaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class IntentDataClipboard implements Serializable
{
    private String m_name = "";
    private String m_surname = "";
    private String m_city = "";
    private String m_street = "";

    public class DateBorn implements Serializable
    {
        public DateBorn()
        {

        }

        public DateBorn(int day, int month, int year)
        {
            m_day = day;
            m_month = month;
            m_year = year;
        }

        public int m_day;
        public int m_year;
        public int m_month;
    }

    private DateBorn m_date_born = new DateBorn();
    private String m_favourite_color = "";
    private List<String> m_favourite_activities = new ArrayList<String>();
    private String m_message = "";

    public void setName(String name)
    {
        m_name = name;
    }

    public void setSurname(String surname)
    {
        m_surname = surname;
    }

    public void setCity(String city)
    {
        m_city = city;
    }

    public void setStreet(String street)
    {
        m_street = street;
    }

    public void setDateBorn(DateBorn date_born)
    {
        m_date_born = date_born;
    }

    public void setDateBorn(int date_born_day, int date_born_month, int date_born_year)
    {
        m_date_born.m_day = date_born_day;
        m_date_born.m_month = date_born_month;
        m_date_born.m_year = date_born_year;
    }

    public void setFavouriteColor(String favourite_color)
    {
        m_favourite_color = favourite_color;
    }

    public void setFavouriteActivities(List<String> favourite_activities) { m_favourite_activities = favourite_activities; }

    public void setFavouriteActivities(String favourite_activity)
    {
            if (true == m_favourite_activities.remove(favourite_activity))
                return;
        m_favourite_activities.add(favourite_activity);
    }


    public void setMessage(String message)
    {
        m_message = message;
    }

    public String getName()
    {
        return m_name;
    }

    public String getSurname()
    {
        return m_surname;
    }

    public String getCity()
    {
        return m_city;
    }

    public String getStreet()
    {
        return m_street;
    }

    public DateBorn getDateBorn()
    {
        return m_date_born;
    }

    public String getDateBornAsString()
    {
        return m_date_born.m_year + "-" + m_date_born.m_month + "-" + m_date_born.m_day;
    }

    public String getFavouriteColor()
    {
        return m_favourite_color;
    }

    public List<String> getFavouriteActivities()
    {
        return m_favourite_activities;
    }

    public String getFavouriteActivitiesAsString()
    {
        String favourite_activities = "";
        for(String activity : m_favourite_activities)
        {
            favourite_activities += "\t\t" + activity + "\n";
        }

        if(favourite_activities.isEmpty())
        {
            return "\t\tbrak";
        }

        return favourite_activities;
    }

    public String getMessage()
    {
        return m_message;
    }

    public void Clear()
    {
        m_name = "";
        m_surname = "";
        m_city = "";
        m_street = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        m_date_born = new DateBorn(day,month,year);
        m_favourite_color = "";
        m_favourite_activities.clear();
        m_message = "";
    }
}
