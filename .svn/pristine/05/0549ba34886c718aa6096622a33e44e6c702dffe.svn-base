package rice.ec.vr;

import android.animation.TypeConverter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yanqux on 2016/1/12.
 */
public class VideoInfoListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<VideoInfoEntity> mList;
    private Context mContext;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int i = position;
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.listitem_video, null);

        //初始化控件对象
        ImageView iv_videopreview = (ImageView) vi.findViewById(R.id.iv_videopreview);
        TextView tv_videoname = (TextView) vi.findViewById(R.id.tv_videoname);
        TextView tv_createtime = (TextView) vi.findViewById(R.id.tv_createtime);
        TextView tv_profile = (TextView) vi.findViewById(R.id.tv_profile);

        VideoInfoEntity entity = mList.get(position);
        String title = entity.Title.isEmpty() ? "暂未命名" : entity.Title;
        Date date = StrToDate(entity.CreateTime);
        String createtime = DateToStr(date);
        String profile = entity.Profile.isEmpty() ? "暂无简介" : entity.Profile;

        AQuery aq = new AQuery(vi);
        aq.id(R.id.iv_videopreview).image(entity.Preview, true, true, 0, 0, null, AQuery.FADE_IN);
        tv_videoname.setText(title);
        tv_createtime.setText(createtime);
        tv_profile.setText(profile);
        return vi;
    }

    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public VideoInfoListAdapter(Context context, ArrayList<VideoInfoEntity> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(context);
    }
}
