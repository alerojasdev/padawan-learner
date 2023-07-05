package com.rale.tavyraiList.arlanguagedetector.dto;
import java.util.Map;
public class Root{
    public String app_version;
    public double time_taken;
    public String msg;
    public boolean ok;
    public Map<String, Double> language_probability;

//    public static class LanguageProbability{
//        public String es;
//    }

    @Override
    public String toString() {
        return "Root{" +
                "app_version='" + app_version + '\'' +
                ", time_taken=" + time_taken +
                ", msg='" + msg + '\'' +
                ", ok=" + ok +
                ", language_probability=" + language_probability +
                '}';
    }
}


