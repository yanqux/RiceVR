package rice.ec.vr;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by yanqux on 2015/4/15.
 */
public class RegisterGetPswActivity extends Activity {
    private LinearLayout ll_back;
    private EditText et_username;
    private EditText et_password;
    private EditText et_validate;
    private Button btn_validate;
    private Button btn_comfirm;
    private MessageTools _messageTools;
    private UserDataService userDataService;
    private TimeCount time;
    private String _TokenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpassword);
        overridePendingTransition(R.animator.in_from_right,
                R.animator.out_to_left);
        ll_back = (LinearLayout) findViewById(R.id.back);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_validate = (EditText) findViewById(R.id.et_validate);
        btn_validate = (Button) findViewById(R.id.btn_validate);
        btn_comfirm = (Button) findViewById(R.id.btn_comfirm);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        et_password = (EditText) findViewById(R.id.et_password);
//        et_comfirmpsw = (EditText) findViewById(R.id.et_comfirmpsw);
//        et_validate = (EditText) findViewById(R.id.et_validate);
//        _back = (LinearLayout) findViewById(R.id.back);
//        username = getIntent().getStringExtra("username");

        time = new TimeCount(60000, 1000);
        userDataService = new UserDataService();
        _messageTools = new MessageTools(this);
        final DataServiceListener getTokenid_dataServiceListener = new DataServiceListener() {
            @Override
            public void onQuery(Object tokenid, int itemCount) {
                System.out.println((String) tokenid);
                _TokenID = (String) tokenid;
            }
        };

        final DataServiceListener changePsw_dataServiceListener = new DataServiceListener() {
            @Override
            public void onQuery(Object result, int itemCount) {
                System.out.println((Boolean) result);
                if ((Boolean) result) {
                    Looper.prepare();
                    _messageTools.ShowToast("修改密码成功！");
                    finish();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    _messageTools.ShowToast("修改密码失败，该用户还未注册！");
                    Looper.loop();
                }
            }
        };
//        _back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        btn_sendvalidationcode = (Button) findViewById(R.id.btn_sendvalidationcode);
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String telRegex = "[1][358]\\d{9}";
                if (et_username.getText().toString().matches(telRegex)) {
                    time.start();
                    userDataService.GetTokenIDAsync(et_username.getText().toString(), getTokenid_dataServiceListener);
                } else {
                    _messageTools.ShowToast("请输入正确的手机号码！");
                }
            }
        });

        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDataService.ChangePasswordAsync(et_username.getText().toString(), et_password.getText().toString(), _TokenID, et_validate.getText().toString(), changePsw_dataServiceListener);
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

