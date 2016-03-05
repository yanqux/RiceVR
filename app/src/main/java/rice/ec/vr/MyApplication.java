package rice.ec.vr;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class MyApplication extends Application {
    private static boolean IS_LOGIN = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setIsLogin(Boolean value) {
        IS_LOGIN = value;
    }

    public Boolean getIsLogin() {
        return IS_LOGIN;
    }

    public void SetLoginSharedPreferences(LoginInfoEntity loginInfo) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppInfo", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putLong("storeid", loginInfo.StoreID);
        editor.putInt("roletype", loginInfo.RoleType);
        editor.putLong("userid", loginInfo.UserID);
        editor.putString("username", loginInfo.UserName);
        editor.commit();
    }

    public void DeleteLoginSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppInfo", Context.MODE_PRIVATE); //私有数据
        sharedPreferences.edit().clear().commit();
    }

    public String GetLoginSharePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppInfo", Context.MODE_PRIVATE); //私有数据
        String login_username = sharedPreferences.getString("username", "");
        return login_username;
    }

    public LoginInfoEntity GetLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppInfo", Context.MODE_PRIVATE); //私有数据
        long storeid = sharedPreferences.getLong("storeid", 0);
        int roletype = sharedPreferences.getInt("roletype", 1);
        long id = sharedPreferences.getLong("userid", 0);
        String username = sharedPreferences.getString("username", "");
        LoginInfoEntity loginInfo = new LoginInfoEntity();
        loginInfo.StoreID = storeid;
        loginInfo.RoleType = roletype;
        loginInfo.UserID = id;
        loginInfo.UserName = username;
        return loginInfo;
    }

    public void SetFirstUseSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("FirstUseInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("FirstUse", false);
        editor.commit();
    }

    public Boolean GetFirstUseSharedPreferences() {
        SharedPreferences sharedpreferences = getSharedPreferences("FirstUseInfo",
                Context.MODE_PRIVATE);
        Boolean isFirstUse = sharedpreferences.getBoolean("FirstUse", true);
        return isFirstUse;
    }
}
