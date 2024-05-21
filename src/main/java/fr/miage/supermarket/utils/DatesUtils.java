package fr.miage.supermarket.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe utilitaire pour les opérations sur les dates.
 * Cette classe fournit des méthodes pour générer une série de dates pour les stocks.
 * @author AlexP
 */
public class DatesUtils {

    /**
     * Génère une série de dates à partir d'une date de départ.
     * Cette méthode crée une liste de dates, en commençant par la date spécifiée
     * et en ajoutant un jour à chaque itération jusqu'à atteindre le nombre de jours spécifié.
     * @param date0 la date de départ
     * @param nbJours le nombre de jours à générer
     * @return une liste de dates couvrant le nombre de jours spécifié
     */
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
