package com.codebyjordan.ancientcityapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.weather.WeatherHelper;
import com.codebyjordan.ancientcityapp.weather.models.SimpleForecastDay;

import java.util.List;

public class TenDayRecyclerAdapter extends RecyclerView.Adapter<TenDayRecyclerAdapter.TenDayView> {

    private Context mContext;
    private List<SimpleForecastDay> mForecast;

    public TenDayRecyclerAdapter(Context context, List<SimpleForecastDay> forecast) {
        mContext = context;
        mForecast = forecast;
    }

    // Viewholder
    public class TenDayView extends RecyclerView.ViewHolder{
        ImageView conditionIcon;
        TextView forecastDate;
        TextView conditionText;
        TextView forecastTemp;
        ImageView precipIcon;
        TextView precipText;

        public TenDayView(View view){
            super(view);
            conditionIcon = (ImageView) view.findViewById(R.id.conditionIcon);
            forecastDate = (TextView) view.findViewById(R.id.forecastDate);
            conditionText = (TextView) view.findViewById(R.id.conditionText);
            forecastTemp = (TextView) view.findViewById(R.id.forecastTemp);
            precipIcon = (ImageView) view.findViewById(R.id.precipIcon);
            precipText = (TextView) view.findViewById(R.id.precipText);
        }
    }

    @Override
    public TenDayView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_weather_tenday, parent, false);

        return new TenDayView(view);
    }

    @Override
    public void onBindViewHolder(TenDayView holder, int position) {
        WeatherHelper helper = new WeatherHelper(mContext);
        SimpleForecastDay forecastDay = mForecast.get(position);

        holder.conditionIcon.setImageDrawable(helper.getConditionIcon(forecastDay.getIcon()));
        holder.forecastDate.setText(forecastDay.getDate().getWeekday() +
                ", " + forecastDay.getDate().getMonthname() +
                " " + forecastDay.getDate().getDay());
        holder.conditionText.setText(forecastDay.getConditions());
        holder.forecastTemp.setText(forecastDay.getHigh().getFahrenheit() + "\u00B0" +
                "/" + forecastDay.getLow().getFahrenheit() + "\u00B0");
        holder.precipIcon.setBackgroundResource(R.drawable.icon_precipitation);
        holder.precipText.setText(forecastDay.getPop() + "%");
    }

    @Override
    public int getItemCount() {
        return mForecast.size();
    }
}
