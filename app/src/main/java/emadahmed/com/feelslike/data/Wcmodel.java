package emadahmed.com.feelslike.data;

import android.widget.EditText;

/**
 * Created by Emad Ahmed on 5/8/2016.
 */
public class Wcmodel {

    private static int lowerTempRange = -50;
    private  static int upperTempRange = 50;

    public static boolean isEmpty(EditText field) {


        if (field.length() ==0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public static  boolean isInRange(int data, int lower, int upper)
    {
        if (data>=lower && data<=upper)
        {
            return true;
        }
        else return false;
    }

    public static boolean isTempValid(EditText field) {
        if (isEmpty(field)) {
            field.setError("Please enter a Number");
            return  false;
        }
        else {
            try {
                lowerTempRange = -50;
               upperTempRange = 50;
                int data = Integer.parseInt(field.getText().toString());
                if (isInRange(data,lowerTempRange,upperTempRange))
                {
                    return true;
                }
                else
                {
                    field.setError("Temp must be between " + lowerTempRange + " & " + upperTempRange + "." );
                    return false;
                }
            }catch (Exception e) {

                return false;

            }


        }
    }

    public static boolean isWindValid(EditText field) {
        if (isEmpty(field)) {
            field.setError("Please enter a Number");
            return  false;
        }
        else {
            try {
                lowerTempRange = 4;
                upperTempRange = 109;
                int data = Integer.parseInt(field.getText().toString());
                if (isInRange(data,lowerTempRange,upperTempRange))
                {
                    return true;
                }
                else
                {
                    field.setError("Wind must be between " + lowerTempRange + " & " + upperTempRange + "." );
                    return false;
                }
            }catch (Exception e) {

                return false;

            }


        }
    }


    public static String calculateWindChill(EditText editWind, EditText editTemp)
    {
        String results = "Results:";

        if (isWindValid(editWind) && isTempValid(editTemp))
        {
            double wind = Double.parseDouble(editWind.getText().toString());

            int temp = Integer.parseInt(editTemp.getText().toString());

            double windchill =  (35.74 + 0.6215*temp - 35.75*Math.pow(wind,0.16) + 0.4275*temp*Math.pow(wind,0.16));

            results += "Feels Like " + Math.round(windchill) + " degrees Farenheit";

        }
        else {
            results+= "There was a problem with your inputs";
        }



        return results;
    }




}
