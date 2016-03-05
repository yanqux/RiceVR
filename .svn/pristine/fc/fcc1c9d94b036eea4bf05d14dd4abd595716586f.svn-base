package rice.ec.vr;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import rice.ec.panoplayer.PanoPlayer;
import rice.ec.panostitching.ErrorListener;
import rice.ec.panostitching.PanoStitching;
import rice.ec.panostitching.UploadListener;
import rice.ec.panostitching.UploadedListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentShare extends BaseFragment {
    private Button BtnShare;
    private PanoStitching _panoStitching;
    private Handler _handler = new Handler();
    private ProgressDialog _progressDialog;
    private String _selectedPath;
    private View _rootView;
    private rice.ec.riceutils.MessageTools _messageTools;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        _rootView = view;
        BtnShare = (Button) view.findViewById(R.id.btnShare);
        _panoStitching = new PanoStitching(getActivity(), "0", uploadListener, uploadedListener, errorListener);
        _progressDialog = new ProgressDialog(getActivity());
        _messageTools = new rice.ec.riceutils.MessageTools(getActivity());
        InitProgressDialog();
        BtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share();
            }
        });
        return view;
    }

    private void Share()
    {
        String userid = GetUserID();
        if(userid.equals("0"))
        {
            _messageTools.ShowDialog("请先登录之后才能进行分享");
            return;
        }

        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 10);
    }

    UploadListener uploadListener = new UploadListener() {
        @Override
        public void onLoading(final int progress) {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _progressDialog.setProgress(progress);
                    //设置_progressDialog 进度条进度
                    _progressDialog.show();
                    //让_progressDialog显示
                }
            });
        }
    };

    private void InitProgressDialog() {
        //实例化
        _progressDialog.setProgressStyle(_progressDialog.STYLE_HORIZONTAL);
        //设置进度条风格，风格为长形，有刻度的
        _progressDialog.setTitle("");
        //设置_progressDialog 标题
        _progressDialog.setMessage("上传进度");
        //设置_progressDialog 提示信息
        _progressDialog.setIcon(R.drawable.sharefragment_vr);
        //设置_progressDialog 标题图标
        _progressDialog.setIndeterminate(false);
        //设置_progressDialog 的进度条是否不明确
        _progressDialog.setCancelable(true);
        //设置_progressDialog 是否可以按退回按键取消
    }

    UploadedListener uploadedListener = new UploadedListener() {
        @Override
        public void onLoaded(final String key, final String title, final String url, final String preview) {
            _handler.post(new Runnable() {
                @Override
                public void run() {
                    _progressDialog.dismiss();
                    String shareUrl = "http://vr.rice.ec/tour.php?path=" + key;
                    PanoPlayer player = new PanoPlayer(getActivity(), "0");
                    player.OpenLocal(title, PanoPlayer.PANOPLAYER_TYPE_IMAGE, _selectedPath);
                }
            });
        }
    };

    ErrorListener errorListener = new ErrorListener() {
        @Override
        public void onError(String message) {
            showDialog(message);
        }
    };

    private void showDialog(String mess) {
        new AlertDialog.Builder(FragmentShare.this.getActivity()).setTitle("Message")
                .setMessage(mess)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {

            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};

            //好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
            //按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            //将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            //最后根据索引值获取图片路径
            String uploadFile = cursor.getString(column_index);
            _selectedPath = uploadFile;

            String userid = GetUserID();

            PanoPlayer player = new PanoPlayer(getActivity(), userid);
            player.OpenLocal("新建全景图片", PanoPlayer.PANOPLAYER_TYPE_IMAGE, uploadFile);

        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private void StartUploadImage(final String uploadFile)
    {
        final EditText input = new EditText(getActivity());
        input.setHint("请输入图片名称");
        input.setFocusable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("图片名称").setIcon(android.R.drawable.ic_dialog_info).setView(input)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String inputTitle = input.getText().toString();
                if (inputTitle.isEmpty()) {
                    inputTitle = "新建全景图片";
                }

                _panoStitching.Stitch(uploadFile, inputTitle);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);
            }
        });

        dialog.show();

    }
}