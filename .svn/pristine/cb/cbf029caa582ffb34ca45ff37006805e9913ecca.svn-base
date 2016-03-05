package rice.ec.vr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by yanqux on 2015/5/29.
 */
public class LoginActivity extends Activity {
    private Button btn_selLogin;
    private Button btn_selRegister;
    private RelativeLayout rl_login;
    private RelativeLayout rl_register;
    private TextView tv_changepsw;
    private LinearLayout ll_back;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private MessageTools _messageTools;
    private UserDataService userDataService;
    private String username;
    private String password;

    private EditText et_reg_username;
    private EditText et_reg_password;
    private EditText et_reg_validate;
    private Button btn_validate;
    private Button btn_reg_comfirm;

    private UserDataService reg_userDataService;
    private String _TokenID = "";
    private String _Code = "";
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginandregister);
        overridePendingTransition(R.animator.in_from_right, R.animator.out_to_left);
        btn_selLogin = (Button) findViewById(R.id.btn_sel_login);
        btn_selRegister = (Button) findViewById(R.id.btn_sel_register);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        ll_back = (LinearLayout) findViewById(R.id.back);
        tv_changepsw = (TextView) findViewById(R.id.tv_changepsw);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_reg_username = (EditText) findViewById(R.id.et_reg_username);
        et_reg_password = (EditText) findViewById(R.id.et_reg_password);
        et_reg_validate = (EditText) findViewById(R.id.et_reg_validate);
        btn_validate = (Button) findViewById(R.id.btn_validate);
        btn_reg_comfirm = (Button) findViewById(R.id.btn_reg_comfirm);
        time = new TimeCount(60000, 1000);
        btn_selLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_login.setVisibility(View.VISIBLE);
                rl_register.setVisibility(View.INVISIBLE);
            }
        });

        btn_selRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_register.setVisibility(View.VISIBLE);
                rl_login.setVisibility(View.INVISIBLE);
            }
        });

        tv_changepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this, RegisterGetPswActivity.class);
                startActivity(it);
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        userDataService = new UserDataService();
        _messageTools = new MessageTools(this);
        final DataServiceListener dataServiceListener = new DataServiceListener() {
            @Override
            public void onQuery(Object result, int itemCount) {
                UserEntity entity = (UserEntity) result;
                if (entity == null) {
                    Looper.prepare();
                    _messageTools.ShowToast("用户名或密码输入错误!");
                    Looper.loop();
                    return;
                } else {
                    Looper.prepare();
                    _messageTools.ShowToast("登陆成功!");
                    MyApplication application = (MyApplication) LoginActivity.this.getApplication();
                    application.setIsLogin(true);
                    LoginInfoEntity loginInfo = new LoginInfoEntity();
                    loginInfo.StoreID = 0;
                    loginInfo.RoleType = 0;
                    loginInfo.UserID = Long.parseLong(entity.UserID);
                    loginInfo.UserName = entity.UserName;
                    application.SetLoginSharedPreferences(loginInfo);
                    Intent mIntent = new Intent("LoginSuccess");
                    mIntent.putExtra("username", entity.UserName);
                    sendBroadcast(mIntent);
                    LoginActivity.this.finish();
                    Looper.loop();
                }
            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                userDataService.GetUserByNameAsync(username, password, dataServiceListener);
            }
        });

        reg_userDataService = new UserDataService();
        final DataServiceListener dataServiceListener2 = new DataServiceListener() {
            @Override
            public void onQuery(Object tokenid, int itemCount) {
                System.out.println((String) tokenid);
                _TokenID = (String) tokenid;
            }
        };

        final DataServiceListener dataServiceListener3 = new DataServiceListener() {
            @Override
            public void onQuery(Object result, int itemCount) {
                System.out.println((Boolean) result);
                if ((Boolean) result) {
                    Looper.prepare();
                    _messageTools.ShowToast("注册成功！");
                    finish();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    _messageTools.ShowToast("注册失败，用户已经存在！");
                    Looper.loop();
                }
            }
        };

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhone = et_reg_username.getText().toString();
                String telRegex = "[1][358]\\d{9}";
                if (userPhone.matches(telRegex)) {
                    time.start();
                    reg_userDataService.GetTokenIDAsync(userPhone, dataServiceListener2);
                } else {
                    _messageTools.ShowToast("请输入正确的手机号码！");
                }
            }
        });

        btn_reg_comfirm = (Button) findViewById(R.id.btn_reg_comfirm);
        btn_reg_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_reg_username.getText().toString().equals("")) {
                    _messageTools.ShowToast("手机号码不能为空！");
                    return;
                }
                if (et_reg_validate.getText().toString().equals("")) {
                    _messageTools.ShowToast("验证码不能为空！");
                    return;
                }
                if (et_reg_password.getText().toString().equals("")) {
                    _messageTools.ShowToast("密码不能为空！");
                    return;
                }

                {
                    reg_userDataService.RegisterAsync(et_reg_username.getText().toString(), et_password.getText().toString(), _TokenID, et_reg_validate.getText().toString(), dataServiceListener3);
                }
            }
        });
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_validate.setBackgroundResource(R.drawable.tab_register_revalidation_selector);
            btn_validate.setClickable(true);
            btn_validate.setText("");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_validate.setClickable(false);
            btn_validate.setBackgroundResource(R.drawable.register_validation_countdown);
            btn_validate.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
