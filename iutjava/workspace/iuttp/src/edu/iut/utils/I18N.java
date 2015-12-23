package edu.iut.utils;

import edu.iut.app.ApplicationSession;

public class I18N {
    public static String get(String key){
        return ApplicationSession.instance().getString(key);
    }
}
