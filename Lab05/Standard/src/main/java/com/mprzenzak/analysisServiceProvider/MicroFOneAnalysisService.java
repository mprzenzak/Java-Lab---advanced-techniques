package com.mprzenzak.analysisServiceProvider;

import com.mprzenzak.api.AnalysisException;
import com.mprzenzak.api.DataSet;

public class MicroFOneAnalysisService implements com.mprzenzak.api.AnalysisService  {
    private double result;
    private boolean isNotTrue;
    @Override
    public void setOptions(String[] strings) throws AnalysisException {}

    @Override
    public String getName() {
        return "MicroFOneAnalysisService";
    }

    @Override
    public void submit(DataSet dataSet) throws AnalysisException {
        try {
            int tp = Integer.parseInt(dataSet.getData()[0][0]);
            int fp = Integer.parseInt(dataSet.getData()[0][1]);
            int fn = Integer.parseInt(dataSet.getData()[1][0]);
            int tn = Integer.parseInt(dataSet.getData()[1][1]);

            double precision = (double) tp / (tp + fp);
            double recall = (double) tp / (tp + fn);

            if (precision + recall == 0) {
                result = 0;
            } else {
                result = 2 * (precision * recall) / (precision + recall);
            }
        } catch (Exception e) {
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
