package fr.miage.supermarket.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatesUtils {

    public static List<Date> getSerieDates(Date date0, int nbJours) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date0);
        
        for (int i = 0; i < nbJours; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return dates;
    }
}
