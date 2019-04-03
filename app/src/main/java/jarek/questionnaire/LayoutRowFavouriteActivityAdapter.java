package jarek.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LayoutRowFavouriteActivityAdapter extends ArrayAdapter<String>
{
    private Context m_contex;
    private int[] m_list_images;
    private String[] m_list_titles;
    private String[] m_list_descriptions;

    LayoutRowFavouriteActivityAdapter(Context contex, String[] list_titles, int[] list_images, String[] list_descriptions)
    {
        super(contex, R.layout.layout_row_favourite_activity, R.id.m_name_row_favourite_activity, list_titles);
        this.m_contex = contex;
        this.m_list_images = list_images;
        this.m_list_titles = list_titles;
        this.m_list_descriptions = list_descriptions;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)m_contex.getSystemService(m_contex.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_row_favourite_activity, parent, false);
        ImageView image = (ImageView)row.findViewById(R.id.m_image_row_favourite_activity);
        TextView title = (TextView)row.findViewById(R.id.m_name_row_favourite_activity);

        image.setImageResource(m_list_images[position]);
        title.setText(m_list_titles[position]);

        return row;
    }
}
