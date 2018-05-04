package com.amator.htprinter.db.factory;

import android.util.SparseArray;

import com.amator.htprinter.db.BannerDbHandler;
import com.amator.htprinter.db.DBHandler;
import com.amator.htprinter.db.HomePageDbHandler;
import com.amator.htprinter.db.UserDbHandler;
import com.amator.htprinter.db.VoiceHandler;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public class DbHandlerFactory {

    private static SparseArray<DBHandler> map = new SparseArray<>();
    public static final int BANNER = 0;
    public static final int HOME_PAGE = 1;
    public static final int VOICE = 2;
    public static final int USER = 3;

    public static DBHandler create(int index) {
        DBHandler handler = map.get(index);
        if (handler == null) {
            switch (index) {
                case BANNER:
                    handler = new BannerDbHandler();
                    break;
                case HOME_PAGE:
                    handler = new HomePageDbHandler();
                    break;
                case VOICE:
                    handler = new VoiceHandler();
                    break;
                case USER:
                    handler = new UserDbHandler();
                    break;
            }
        }
        return handler;
    }

}
