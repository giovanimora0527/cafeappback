package co.com.grupoasd.fae.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utilidades para el manejo de fechas.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class DateUtil {
    
    private static final TimeZone TIMEZONE = TimeZone.getTimeZone("America/Bogota");
    private static final ZoneId ZONE_ID = ZoneId.of("America/Bogota");
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    
    /**
     * Constructor privado. Patrón singleton.
     */
    private DateUtil() {
        
    }
    
    /**
     * Obtiene la fecha actual del sistema.
     * @return Fecha actual.
     */
    public static Date now() {
        return Calendar.getInstance(TIMEZONE).getTime();
    }
    
    /**
     * Obtiene la fecha actual del sistema.
     * @return Fecha actual.
     */
    public static LocalDateTime nowDateTime() {
        return now().toInstant().atZone(ZONE_ID).toLocalDateTime();
    }
    
    /**
     * Convierte una fecha de tipo Date a LocalDate
     * @param date Fecha de tipo Date
     * @return Fecha de tipo LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }
    
    /**
     * Convierte una fecha de tipo Date a LocalTime.
     * @param date Fecha de tipo Date
     * @return Fecha de tipo LocalTime
     */
    public static LocalTime dateToLocalTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalTime();
    }
    
    /**
     * Convierte una cadena con formato AAAA-MM-DD a LocalDate.
     * @param fecha Cadena con la fecha.
     * @return LocalDate
     */
    public static LocalDate stringToLocalDate(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }
    
    public static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }
    
    public static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
}
