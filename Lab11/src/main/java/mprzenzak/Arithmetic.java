package mprzenzak;

import java.util.ArrayList;

public class Arithmetic {
    public static String average(ArrayList<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return String.valueOf(sum / values.size());
    }
}
