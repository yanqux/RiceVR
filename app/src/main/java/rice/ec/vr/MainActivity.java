package rice.ec.vr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import rice.ec.riceutils.AppUpdate;
import rice.ec.riceutils.CheckNetworkStatusListener;
import rice.ec.riceutils.CheckUpdateListener;
import rice.ec.riceutils.Network;
import rice.ec.riceutils.MessageTools;

public class MainActivity extends Activity {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private FragmentFactory _fragmentFactory;
    private BaseFragment _fragment = null;
    private MessageTools _messageTools;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CheckUpdate();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        InitControl();
    }

    private void InitControl()
    {
        _messageTools = new MessageTools(this);
        fragmentManager = getFragmentManager();
        _fragmentFactory = new FragmentFactory();
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        ((RadioButton) radioGroup.findViewById(R.id.rb_home)).setChecked(true);
        ReplaceFragment(R.id.rb_home);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ReplaceFragment(checkedId);
            }
        });

        SetRadioButton(R.id.rb_home, R.drawable.tab_nav_home_selector);
        SetRadioButton(R.id.rb_share, R.drawable.tab_nav_share_selector);
        SetRadioButton(R.id.rb_mine, R.drawable.tab_nav_mine_selector);
    }

    private void SetRadioButton(int id, int drawableid)
    {
        float dpValue = 24;
        int size = rice.ec.riceutils.Display.dip2px(MainActivity.this, dpValue);
        RadioButton radioButton = (RadioButton)findViewById(id);
        Drawable drawable = getResources().getDrawable(drawableid);
        drawable.setBounds(0, 0, size, size);
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }

    private void CheckUpdate()
    {
        Network network = new Network(MainActivity.this);
        network.CheckNetworkStatus(new CheckNetworkStatusListener() {
            @Override
            public void onChecked(boolean b) {
                if(b)
                {
                    CheckUpdateListener checkUpdateListener = new CheckUpdateListener() {
                        @Override
                        public void onChecked(boolean needUpdate, String newVersion) {
                            if(!needUpdate)
                            {
                            }

                        }
                    };

                    AppUpdate update = new AppUpdate(MainActivity.this);
                    update.Check(3, checkUpdateListener, true);
                }
            }
        });
    }

    private void ReplaceFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (_fragment != null) {
            transaction.hide(_fragment);
        }

        _fragment = _fragmentFactory.getInstanceByIndex(index);
        if (_fragment.GetIsNew()) {
            transaction.add(R.id.content, _fragment);
        }

        transaction.show(_fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

}
