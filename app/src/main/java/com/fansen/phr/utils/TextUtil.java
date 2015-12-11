package com.fansen.phr.utils;

import com.fansen.phr.activities.EncounterCoreInfoActivity;
import com.fansen.phr.entity.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by 310078142 on 2015/12/9.
 */
public class TextUtil {
    public static ArrayList<String> toArrayList(String text, String delimiter){
        if(text == null){
            return null;
        }

        ArrayList<String> arrayList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(text, delimiter);

        while(stringTokenizer.hasMoreElements()){
            arrayList.add(stringTokenizer.nextToken());
        }

        return arrayList;
    }

    public static String toString(ArrayList<String> arrayList, String delimiter){
        if (arrayList == null){
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (int i=0; i<arrayList.size(); i++){
            stringBuffer.append(arrayList.get(i));
            if (i != arrayList.size()-1){
                stringBuffer.append(delimiter);
            }
        }

        return stringBuffer.toString();
    }

    public static String convertDiagnosisListToString(ArrayList<Diagnosis> diagnosises, String delimiter){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0; i<diagnosises.size(); i++){
            stringBuffer.append(diagnosises.get(i).getDiagnosis_dict().getName());
            if (i != diagnosises.size()-1){
                stringBuffer.append(delimiter);
            }
        }

        return stringBuffer.toString();
    }
}
