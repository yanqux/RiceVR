package rice.ec.vr;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

import PullToRefreshView.PullToRefreshLayout;
import rice.ec.panoplayer.PanoPlayer;
import rice.ec.riceutils.CheckNetworkStatusListener;
import rice.ec.riceutils.Network;

public class MyVRActivity extends Activity {

    private ListView _listView;
    private PullToRefreshLayout _pullToRefreshLayout;
    private ArrayList<VideoInfoEntity> _ImageInfoList;
    private ArrayList<VideoInfoEntity> _VideoInfoList;
    private ArrayList<VideoInfoEntity> _InfoShowList;
    private VideoInfoListAdapter _VideoInfoListAdapter;
    private int _ImagePageIndex = 0;
    private int _VideoPageIndex = 0;
    private int _selectedIndex = 0;
    private Network _network;
    private rice.ec.riceutils.MessageTools _messageTools;
    private boolean _networkIsConnected = false;
    private Handler _handler = new Handler();

    private ViewGroup _type_tab_image;
    private ViewGroup _type_tab_video;
    private ImageView _type_tab_image_image;
    private TextView _type_tab_image_title;
    private ImageView _type_tab_video_image;
    private TextView _type_tab_video_title;

    private ViewGroup _invis_type_tab_image;
    private ViewGroup _invis_type_tab_video;
    private ImageView _invis_type_tab_image_image;
    private TextView _invis_type_tab_image_title;
    private ImageView _invis_type_tab_video_image;
    private TextView _invis_type_tab_video_title;

    private boolean _dataLoading = false;
    private final int PageSize = 10;
    private String _userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_vr);

        MyApplication application = (MyApplication)getApplication();
        LoginInfoEntity loginInfo = application.GetLoginInfo();
        if(loginInfo != null)
        {
            _userid = String.valueOf(loginInfo.UserID);
        }
        else
        {
            _userid = "0";
        }

        _network = new Network(this);
        _messageTools = new rice.ec.riceutils.MessageTools(this);
        InitControl();

        _pullToRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                ReloadData();
            }
        });
    }

    private void ReloadData()
    {
        _pullToRefreshLayout.manualRefresh();

        _network.CheckNetworkStatus(new CheckNetworkStatusListener() {
            @Override
            public void onChecked(boolean b) {
                if(MyVRActivity.this.isFinishing())
                {
                    return;
                }

                if (b) {

                    CheckTypteTab(0);
                    setPageIndex(_selectedIndex, 0);
                    GetList("3", "0", true);
                    InitDataToCache("2");

                    _networkIsConnected = true;
                } else {
                    _networkIsConnected = false;
                    _pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    _messageTools.ShowToast("网络连接不通");
                }
            }
        });

    }

    private void InitDataToCache(final String type)
    {
        StoreDataService dataService = new StoreDataService(MyVRActivity.this);
        DataServiceListener<ArrayList<VideoInfoEntity>> dataServiceListener = new DataServiceListener<ArrayList<VideoInfoEntity>>() {
            @Override
            public void onQuery(ArrayList<VideoInfoEntity> result, int itemCount) {
                if(result != null && result.size() > 0) {
                    GetListData(type, true, result);
                }
            }
        };
        dataService.GetMyResourceEntitiesAsync(type, _userid, String.valueOf(PageSize), "0", dataServiceListener);
    }

    private void addHeader()
    {
        LayoutInflater inflater = (LayoutInflater)MyVRActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerLayout = inflater.inflate(R.layout.listview_header, null);
        _listView.addHeaderView(headerLayout);
    }

    private void addTypeBar()
    {
        LayoutInflater inflater = (LayoutInflater)MyVRActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View typeBar = inflater.inflate(R.layout.listview_typebar, null);
        _listView.addHeaderView(typeBar);
    }

    private void setPageIndex(int selectedIndex, int pageIndex)
    {
        if (selectedIndex == 0) {
            _ImagePageIndex = pageIndex;
        } else if (selectedIndex == 1) {
            _VideoPageIndex = pageIndex;
        }
    }

    private int getPageIndex(int selectedIndex)
    {
        int pageIndex = 0;
        if (selectedIndex == 0) {
            pageIndex = _ImagePageIndex;
        } else if (selectedIndex == 1) {
            pageIndex = _VideoPageIndex;
        }
        return pageIndex;
    }

    private void InitControl() {

        _listView = (ListView) findViewById(R.id.list_show);
        _pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);

        //addHeader();
        addTypeBar();

        final ViewGroup invis = (ViewGroup)findViewById(R.id.invis);

        _listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    invis.setVisibility(View.VISIBLE);
                } else {

                    invis.setVisibility(View.GONE);
                }
            }
        });

        _pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                if(!_networkIsConnected)
                {
                    ReloadData();
                }
                else
                {
                    setPageIndex(_selectedIndex, 0);
                    if (_selectedIndex == 0) {
                        GetVideoInfoListData("3", "0", true);
                    } else if (_selectedIndex == 1) {
                        GetVideoInfoListData("2", "0", true);
                    }
                }
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

                setPageIndex(_selectedIndex, getPageIndex(_selectedIndex) + 1);
                if (_selectedIndex == 0) {
                    GetVideoInfoListData("3", String.valueOf(getPageIndex(_selectedIndex)), false);
                } else if (_selectedIndex == 1) {
                    GetVideoInfoListData("2", String.valueOf(getPageIndex(_selectedIndex)), false);
                }
            }
        });

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyApplication application = (MyApplication)MyVRActivity.this.getApplication();
                LoginInfoEntity loginInfo = application.GetLoginInfo();
                String userid = "0";
                if(loginInfo != null)
                {
                    userid = String.valueOf(loginInfo.UserID);
                }
                VideoInfoEntity entity = (VideoInfoEntity)adapterView.getAdapter().getItem(i); //由于ListView里面有header或者footer，以该方式返回的item才是正确的，否则获取的位置会有偏移。
                PanoPlayer player = new PanoPlayer(MyVRActivity.this, userid);
                String key = entity.Code;
                String title = entity.Title;
                String resUrl = entity.Url;
                String shareUrl = "http://vr.rice.ec/tour.php?path=" + key;
                String preview = entity.Preview;

                if (_selectedIndex == 0) {
                    player.OpenRemote(title, PanoPlayer.PANOPLAYER_TYPE_IMAGE, key, resUrl, shareUrl, preview);
                } else if (_selectedIndex == 1) {
                    player.OpenRemote(title, PanoPlayer.PANOPLAYER_TYPE_VIDEO, key, resUrl, shareUrl, preview);
                }
            }
        });

        _type_tab_image = (ViewGroup)findViewById(R.id.type_tab_image);
        _type_tab_video = (ViewGroup)findViewById(R.id.type_tab_video);
        _type_tab_image_image = (ImageView)findViewById(R.id.type_tab_image_image);
        _type_tab_image_title = (TextView)findViewById(R.id.type_tab_image_title);
        _type_tab_video_image = (ImageView)findViewById(R.id.type_tab_video_image);
        _type_tab_video_title = (TextView)findViewById(R.id.type_tab_video_title);

        _invis_type_tab_image = (ViewGroup)findViewById(R.id.invis_type_tab_image);
        _invis_type_tab_video = (ViewGroup)findViewById(R.id.invis_type_tab_video);
        _invis_type_tab_image_image = (ImageView)findViewById(R.id.invis_type_tab_image_image);
        _invis_type_tab_image_title = (TextView)findViewById(R.id.invis_type_tab_image_title);
        _invis_type_tab_video_image = (ImageView)findViewById(R.id.invis_type_tab_video_image);
        _invis_type_tab_video_title = (TextView)findViewById(R.id.invis_type_tab_video_title);

        _type_tab_image.setOnClickListener(_type_tab_imageOnClickListener);
        _type_tab_video.setOnClickListener(_type_tab_videoOnClickListener);
        _invis_type_tab_image.setOnClickListener(_type_tab_imageOnClickListener);
        _invis_type_tab_video.setOnClickListener(_type_tab_videoOnClickListener);

        CheckTypteTab(0);
    }

    View.OnClickListener _type_tab_imageOnClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            if(_dataLoading)
            {
                return;
            }
            CheckTypteTab(0);
            GetCacheList("3");
        }
    };

    View.OnClickListener _type_tab_videoOnClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            if(_dataLoading)
            {
                return;
            }
            CheckTypteTab(1);
            GetCacheList("2");
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void CheckTypteTab(int type)
    {
        switch (type)
        {
            case 0: {
                Drawable drawable = getResources().getDrawable(R.drawable.typetab_border_selected);
                _type_tab_image.setBackground(drawable);
                _invis_type_tab_image.setBackground(drawable);
                drawable = getResources().getDrawable(R.drawable.tab_type_image_on);
                _type_tab_image_image.setBackground(drawable);
                _invis_type_tab_image_image.setBackground(drawable);
                _type_tab_image_title.setTextColor(Color.parseColor("#5face8"));
                _invis_type_tab_image_title.setTextColor(Color.parseColor("#5face8"));

                drawable = getResources().getDrawable(R.drawable.typetab_layout_border);
                _type_tab_video.setBackground(drawable);
                _invis_type_tab_video.setBackground(drawable);
                drawable = getResources().getDrawable(R.drawable.tab_type_video_off);
                _type_tab_video_image.setBackground(drawable);
                _invis_type_tab_video_image.setBackground(drawable);
                _type_tab_video_title.setTextColor(Color.parseColor("#BFBFBF"));
                _invis_type_tab_video_title.setTextColor(Color.parseColor("#BFBFBF"));
                break;
            }

            case 1: {
                Drawable drawable = getResources().getDrawable(R.drawable.typetab_border_selected);
                _type_tab_video.setBackground(drawable);
                _invis_type_tab_video.setBackground(drawable);
                drawable = getResources().getDrawable(R.drawable.tab_type_video_on);
                _type_tab_video_image.setBackground(drawable);
                _invis_type_tab_video_image.setBackground(drawable);
                _type_tab_video_title.setTextColor(Color.parseColor("#5face8"));
                _invis_type_tab_video_title.setTextColor(Color.parseColor("#5face8"));

                drawable = getResources().getDrawable(R.drawable.typetab_layout_border);
                _type_tab_image.setBackground(drawable);
                _invis_type_tab_image.setBackground(drawable);
                drawable = getResources().getDrawable(R.drawable.tab_type_image_off);
                _type_tab_image_image.setBackground(drawable);
                _invis_type_tab_image_image.setBackground(drawable);
                _type_tab_image_title.setTextColor(Color.parseColor("#BFBFBF"));
                _invis_type_tab_image_title.setTextColor(Color.parseColor("#BFBFBF"));
                break;
            }
        }

        _selectedIndex = type;
    }

    Handler listViewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(MyVRActivity.this == null || MyVRActivity.this.isFinishing())
            {
                return;
            }

            ListViewHandlerObj obj = (ListViewHandlerObj) msg.obj;

            if (obj.FirstLoad) {
                if(_InfoShowList != null) {
                    _VideoInfoListAdapter = new VideoInfoListAdapter(MyVRActivity.this.getApplication(), _InfoShowList);
                    _listView.setAdapter(_VideoInfoListAdapter);
                }
            } else {
                _VideoInfoListAdapter.notifyDataSetChanged();
            }

            if(obj.ShowRefreshLayout)
            {
                _pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            _dataLoading = false;
            super.handleMessage(msg);
        }
    };

    private void GetVideoInfoListData(final String type, final String pageindex, final boolean firstload) {

        _network.CheckNetworkStatus(new CheckNetworkStatusListener() {
            @Override
            public void onChecked(boolean b) {
                if (b) {
                    GetList(type, pageindex, firstload);
                    _networkIsConnected = true;
                } else {
                    _networkIsConnected = false;
                    _pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    _messageTools.ShowToast("网络连接不通");
                }
            }
        });

    }

    public class ListViewHandlerObj
    {
        public boolean FirstLoad;
        public boolean ShowRefreshLayout;
    }

    private void GetList(final String type, final String pageindex, final boolean firstload)
    {
        GetList(type, pageindex, firstload, true);
    }

    private ArrayList<VideoInfoEntity> GetListData(String type, boolean firstload, ArrayList<VideoInfoEntity> result)
    {
        ArrayList<VideoInfoEntity> list = null;

        if(type == "3")
        {
            if (firstload) {
                _ImageInfoList = result;

            } else {
                _ImageInfoList.addAll(result);
            }

            list = _ImageInfoList;
        }
        else if(type == "2")
        {
            if (firstload) {
                _VideoInfoList = result;

            } else {
                _VideoInfoList.addAll(result);
            }

            list = _VideoInfoList;
        }

        return list;

    }

    private void GetList(final String type, final String pageindex, final boolean firstload, final boolean showRefreshLayout)
    {
        _dataLoading = true;

        StoreDataService dataService = new StoreDataService(MyVRActivity.this);
        DataServiceListener<ArrayList<VideoInfoEntity>> dataServiceListener = new DataServiceListener<ArrayList<VideoInfoEntity>>() {
            @Override
            public void onQuery(ArrayList<VideoInfoEntity> result, int itemCount) {
                if(result != null && result.size() > 0) {
                    _InfoShowList = GetListData(type, firstload, result);
                    ListViewHandlerObj listViewHandlerObj = new ListViewHandlerObj();
                    listViewHandlerObj.FirstLoad = firstload;
                    listViewHandlerObj.ShowRefreshLayout = showRefreshLayout;
                    Message msg = Message.obtain();
                    msg.obj = listViewHandlerObj;
                    listViewHandler.sendMessage(msg);
                }
                else
                {
                    _handler.post(new Runnable() {
                        @Override
                        public void run() {
                            _pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOMORE);
                            _dataLoading = false;
                        }
                    });
                }
            }
        };
        dataService.GetMyResourceEntitiesAsync(type, _userid, String.valueOf(PageSize), pageindex, dataServiceListener);
    }

    private void GetCacheList(String type)
    {
        _dataLoading = true;

        if(type == "2")
        {
            _InfoShowList = _VideoInfoList;
        }
        else if(type == "3")
        {
            _InfoShowList = _ImageInfoList;
        }

        ListViewHandlerObj listViewHandlerObj = new ListViewHandlerObj();
        listViewHandlerObj.FirstLoad = true;
        listViewHandlerObj.ShowRefreshLayout = false;
        Message msg = Message.obtain();
        msg.obj = listViewHandlerObj;
        listViewHandler.sendMessage(msg);
    }

}
