package rice.ec.vr;

import android.app.Fragment;

import java.util.HashMap;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {

    private HashMap<String, BaseFragment> _fragments = null;

    public FragmentFactory() {
        _fragments = new HashMap<String, BaseFragment>();
    }

    public BaseFragment getInstanceByIndex(int index) {
        BaseFragment fragment = null;
        switch (index) {
            case R.id.rb_home:
                fragment = _fragments.get("rb_home");
                if (fragment == null) {
                    fragment = new FragmentHome();
                    _fragments.put("rb_home", fragment);
                } else {
                    fragment.SetIsNew(false);
                }
                break;
            case R.id.rb_share:
                fragment = _fragments.get("rb_share");
                if (fragment == null) {
                    fragment = new FragmentShare();
                    _fragments.put("rb_share", fragment);
                } else {
                    fragment.SetIsNew(false);
                }
                break;
            case R.id.rb_mine:
                fragment = _fragments.get("rb_mine");
                if (fragment == null) {
                    fragment = new FragmentMine();
                    _fragments.put("rb_mine", fragment);
                } else {
                    fragment.SetIsNew(false);
                }
                break;
        }
        return fragment;
    }
}
