package com.mprzenzak.analysisServiceProvider;

import com.mprzenzak.api.AnalysisException;
import com.mprzenzak.api.DataSet;

public class KappaAnalysisService implements com.mprzenzak.api.AnalysisService  {
    private double result;
    private boolean isNotTrue;
    @Override
    public void setOptions(String[] strings) throws AnalysisException {}

    @Override
    public String getName() {
        return "KappaAnalysisService";
    }

    @Override
    public void submit(DataSet dataSet) throws AnalysisException {
        try {
            int tt = Integer.parseInt(dataSet.getData()[0][0]);
            int ft = Integer.parseInt(dataSet.getData()[0][1]);
            int tf = Integer.parseInt(dataSet.getData()[1][0]);
            int ff = Integer.parseInt(dataSet.getData()[1][1]);

            int total = tt + ft + tf + ff;
            double p0 = (double) (tt + ff) / total;
            double p1 = (double) (tt + tf) * (tt + ft) / (total * total);
            double p2 = (double) (tf + ff) * (ft + ff) / (total * total);
            double pe = p1 + p2;

            if (pe == 1) {
                result = 1;
            } else {
                result = (p0 - pe) / (1 - pe);
            }
        }
        catch (Exception e) {
            isNotTrue = true;
            throw new AnalysisException("Error while processing data");
        }
    }

    @Override
    public double retrieve(boolean clear) throws AnalysisException {
        if (isNotTrue) {
            throw new AnalysisException("Error while processing data");
        }
        double result_temp = result;
        if(clear){
            result = 0;
            return result_temp;
        } else {
            result_temp = 0;
            return result;
        }
    }
}
