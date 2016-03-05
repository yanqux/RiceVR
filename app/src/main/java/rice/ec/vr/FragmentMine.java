package rice.ec.vr;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentMine extends BaseFragment {
    private LinearLayout ll_loginClickZone;
    private TextView tv_mycardclip;
    private TextView tv_username;
    private TextView tv_cashier;
    private rice.ec.riceutils.MessageTools _messageTools;
    private Boolean isLogin = false;
    private MyApplication application;
    private View _view;
    private UserDataService userDataService;
    //private Button btn_exit;
    //private Button btn_changePsw;
    private Button button_vrlist;
    private Button button_chgpwd;
    private Button button_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_mine, container, false);
        _messageTools = new rice.ec.riceutils.MessageTools(getActivity());
        userDataService = new UserDataService();
        InitControl();
        ChangeLoginStatus();
        ChangeLoginTxtStatus();
        RegisterReceiver();
        super.onCreateView(inflater, container, savedInstanceState);
        return _view;
    }

    private void RegisterReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("LoginSuccess");
        myIntentFilter.addAction("LogoutSuccess");
        //注册广播
        FragmentMine.this.getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private void ChangeLoginTxtStatus() {
        if (!isLogin) {
            tv_username.setText("点击登录");
            //btn_exit.setVisibility(View.INVISIBLE);
            ll_loginClickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    Intent it = new Intent();
                    it.setClass(FragmentMine.this.getActivity(), LoginActivity.class);
                    startActivityForResult(it, 1);
                }
            });

        } else {
            tv_username.setText(application.GetLoginSharePreferences());
            //btn_exit.setVisibility(View.VISIBLE);
            ll_loginClickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    Intent it = new Intent();
                    it.setClass(FragmentMine.this.getActivity(), LoginActivity.class);
                    startActivityForResult(it, 1);
                }
            });
        }
    }

    private void InitControl() {
        tv_username = (TextView) _view.findViewById(R.id.tv_username);
        ll_loginClickZone = (LinearLayout) _view.findViewById(R.id.ll_loginClickZone);

        /*btn_exit = (Button) _view.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDataService.LogoutAsync(tv_username.getText().toString(), dataServiceListener);
            }
        });

        btn_changePsw = (Button) _view.findViewById(R.id.btn_changePsw);
        btn_changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(FragmentMine.this.getActivity(), RegisterGetPswActivity.class);
                startActivity(it);
            }
        });*/


        button_vrlist = (Button) _view.findViewById(R.id.button_vrlist);
        button_vrlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMyVRList();
            }
        });

        button_chgpwd = (Button) _view.findViewById(R.id.button_chgpwd);
        button_chgpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(FragmentMine.this.getActivity(), RegisterGetPswActivity.class);
                startActivity(it);
            }
        });

        button_logout = (Button) _view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDataService.LogoutAsync(tv_username.getText().toString(), dataServiceListener);
            }
        });
    }

    final DataServiceListener dataServiceListener = new DataServiceListener() {
        @Override
        public void onQuery(Object result, int itemCount) {
            System.out.println((Boolean) result);
            if ((Boolean) result) {
                Looper.prepare();
                _messageTools.ShowToast("注销成功！");
                final MyApplication application = (MyApplication) FragmentMine.this.getActivity().getApplication();
                application.setIsLogin(false);
                application.DeleteLoginSharedPreferences();
                Intent it = new Intent("LogoutSuccess");
                getActivity().sendBroadcast(it);
                Looper.loop();
            } else {
                Looper.prepare();
                _messageTools.ShowToast("注销失败！");
                Looper.loop();
            }
        }
    };


    private void ChangeLoginStatus() {
        application = (MyApplication) FragmentMine.this.getActivity().getApplication();
        LoginInfoEntity logininfo = application.GetLoginInfo();
        if (logininfo.UserID == 0) {
            isLogin = false;
        } else {
            isLogin = true;
        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("LoginSuccess")) {
                tv_username.setText(intent.getStringExtra("username"));
                ll_loginClickZone.setEnabled(false);
                //btn_exit.setVisibility(View.VISIBLE);
                //btn_exit.setEnabled(true);
                ChangeLoginStatus();
            } else if (action.equals("LogoutSuccess")) {
                tv_username.setText("点击登录");
                ll_loginClickZone.setEnabled(true);
                //btn_exit.setVisibility(View.INVISIBLE);
                //btn_exit.setEnabled(false);
                ChangeLoginStatus();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentMine.this.getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private String GetUserID()
    {
        MyApplication application = (MyApplication)getActivity().getApplication();
        LoginInfoEntity loginInfo = application.GetLoginInfo();
        String userid = "0";
        if(loginInfo != null)
        {
            userid = String.valueOf(loginInfo.UserID);
        }

        return userid;
    }

    private void OpenMyVRList()
    {
        String userid = GetUserID();
        if(userid.equals("0"))
        {
            _messageTools.ShowDialog("请先登录");
            return;
        }

        Intent intent = new Intent();
        intent.setClassName(getActivity(), "rice.ec.vr.MyVRActivity");
        getActivity().startActivity(intent);
    }
}