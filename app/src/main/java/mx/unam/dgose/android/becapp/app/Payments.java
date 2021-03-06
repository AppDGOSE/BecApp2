package mx.unam.dgose.android.becapp.app;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase para contener la información
 * del prefil de un becario.
 */
public class Payments extends Information {
    public static String path = "/payments/";
    public static int JANUARY   = 0;
    public static int FEBRUARY  = 1;
    public static int MARCH     = 2;
    public static int APRIL     = 3;
    public static int MAY       = 4;
    public static int JUNE      = 5;
    public static int JULY      = 6;
    public static int AUGUST    = 7;
    public static int SEPTEMBER = 8;
    public static int OCTOBER   = 9;
    public static int NOVEMBER  = 10;
    public static int DECEMBER  = 11;

    public int currentMonth;


    /* NOTE: Todas las siguientes propiedades
     * son de sólo lectura. Lo que implica que
     * deberían ser privadas y tener getters.
     * Sin embargo: http://goo.gl/H9yjXh
     *
     * NOTE: Los correos y números se guardan como
     * cadenas y no como tales porque el usuario
     * no va a llamar o mandar correo a si mismo.
     */

    public class Payment {
        public String month;
        public String amount;
        public String done;
        public String status;
        public int monthNumber;

        Payment(String month, String amount, String done, String status) {
            this.month = month;
            this.amount = amount;
            this.done = done;
            this.status = status;

            if (this.month.equals("Enero")) {
                this.monthNumber = JANUARY;
            } else if (this.month.equals("Febrero")) {
                this.monthNumber = FEBRUARY;
            } else if (this.month.equals("Marzo")) {
                this.monthNumber = MARCH;
            } else if (this.month.equals("Abril")) {
                this.monthNumber = APRIL;
            } else if (this.month.equals("Mayo")) {
                this.monthNumber = MAY;
            } else if (this.month.equals("Junio")) {
                this.monthNumber = JUNE;
            } else if (this.month.equals("Julio")) {
                this.monthNumber = JULY;
            } else if (this.month.equals("Agosto")) {
                this.monthNumber = AUGUST;
            } else if (this.month.equals("Septiembre")) {
                this.monthNumber = SEPTEMBER;
            } else if (this.month.equals("Octubre")) {
                this.monthNumber = OCTOBER;
            } else if (this.month.equals("Noviembre")) {
                this.monthNumber = NOVEMBER;
            } else {
                this.monthNumber = DECEMBER;
            }

            if (this.monthNumber < (currentMonth - 1))
                this.monthNumber += 12;
        }
    }

    public ArrayList<Payment> calendar;

    public Payments (Session session) {
        this.session = session;

        int currentMonthJava = Calendar.getInstance().get(Calendar.MONTH);

        if (currentMonthJava == Calendar.JANUARY) {
            this.currentMonth = JANUARY;
        } else if (currentMonthJava == Calendar.FEBRUARY) {
            this.currentMonth = FEBRUARY;
        } else if (currentMonthJava == Calendar.MARCH) {
            this.currentMonth = MARCH;
        } else if (currentMonthJava == Calendar.APRIL) {
            this.currentMonth = APRIL;
        } else if (currentMonthJava == Calendar.MAY) {
            this.currentMonth = MAY;
        } else if (currentMonthJava == Calendar.JUNE) {
            this.currentMonth = JUNE;
        } else if (currentMonthJava == Calendar.JULY) {
            this.currentMonth = JULY;
        } else if (currentMonthJava == Calendar.AUGUST) {
            this.currentMonth = AUGUST;
        } else if (currentMonthJava == Calendar.SEPTEMBER) {
            this.currentMonth = SEPTEMBER;
        } else if (currentMonthJava == Calendar.OCTOBER) {
            this.currentMonth = OCTOBER;
        } else if (currentMonthJava == Calendar.NOVEMBER) {
            this.currentMonth = NOVEMBER;
        } else {
            this.currentMonth = DECEMBER;
        }
    }

    public void getData() {

        status = "0";
        message = "Failure";

        try {

            JSONObject result = session.send(path, "GET");

            status = result.getString("status");
            message = result.getString("message");

            if (status.equals("200")) {
                JSONObject payments = result.getJSONObject("payments");

                JSONArray months = payments.getJSONArray("calendar");
                calendar = new ArrayList<Payment>(months.length());
                for (int i = 0; i < months.length(); i++) {
                    JSONObject temp = months.getJSONObject(i);
                    String month = temp.getString("month");
                    String amount = temp.getString("amount");
                    String done = temp.getString("done");
                    String status = temp.getString("status");

                    calendar.add(i, new Payment(month, amount, done, status));

                    //Sorting
                    Collections.sort(calendar, new Comparator<Payment>() {
                            @Override
                            public int compare(Payment a, Payment  b) {
                                return  (a.monthNumber - b.monthNumber);
                            }
                        });
                }
            }

        } catch (JSONException e) {
        } catch (NullPointerException e) {
        }
    }
}
