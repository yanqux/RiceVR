package rice.ec.vr;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    商户的数据服务
 */
public class StoreDataService {
    private Context _context;

    public StoreDataService(Context context) {
        _context = context;

    }

    public void GetAppAdvertsAsync(final DataServiceListener<ArrayList<AppAdvertEntity>> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<AppAdvertEntity> entities = GetAppAdverts();
                    dataServiceListener.onQuery(entities, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<AppAdvertEntity> GetAppAdverts() {
        String url = "http://openapi.rice.ec/GetAppAdverts.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        ArrayList<AppAdvertEntity> appAdvertArray = new ArrayList<AppAdvertEntity>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("", null));
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                JSONArray dataArray = json.getJSONArray("data");
                int length = dataArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject itemObject = dataArray.getJSONObject(i);
                    AppAdvertEntity entity = new AppAdvertEntity();
                    entity.ID = itemObject.getString("ID");
                    entity.ImageUrl = itemObject.getString("ImageUrl");
                    entity.LinkUrl = itemObject.getString("LinkUrl");
                    entity.Remark = itemObject.getString("Remark");
                    entity.Sort = itemObject.getString("Sort");
                    appAdvertArray.add(entity);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appAdvertArray;
    }

    public ArrayList<VideoInfoEntity> GetVideoInfoEntities(String type, String pagesize, String pageindex) {
        String url = "http://openapi.rice.ec/GetResources2.php";
        ArrayList<VideoInfoEntity> InfoEntitiesArray = new ArrayList<VideoInfoEntity>();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("pagesize", pagesize));
        params.add(new BasicNameValuePair("pageindex", pageindex));
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                JSONObject data = json.getJSONObject("data");
                JSONArray dataArray = data.getJSONArray("data");
                int length = dataArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject itemObject = dataArray.getJSONObject(i);
                    VideoInfoEntity entity = new VideoInfoEntity();
                    entity.Url = (String) itemObject.get("Url");
                    entity.Preview = (String) itemObject.get("Preview");
                    //   entity.Creator = (String) itemObject.get("Creator");
                    //    entity.Type = (String) itemObject.get("Type");
                    //    entity.ID = (String) itemObject.get("ID");
                    entity.Profile = (String) itemObject.get("Profile");
                    //    entity.ServerType = (String) itemObject.get("ServerType");
                    //    entity.Version = (String) itemObject.get("Version");
                    entity.Code = (String) itemObject.get("Code");
                    entity.Title = (String) itemObject.get("Title");
                    entity.Size = (String) itemObject.get("Size");
                    entity.CreateTime = itemObject.getString("CreateTime");
                    InfoEntitiesArray.add(entity);
                }
                System.out.print("");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return InfoEntitiesArray;
    }

    public int GetVideoInfoEntitiesCount(String type, String pagesize, String pageindex) {
        int itemCount = 0;
        String url = "http://openapi.rice.ec/GetResources2.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("pagesize", pagesize));
        params.add(new BasicNameValuePair("pageindex", pageindex));
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                JSONObject data = json.getJSONObject("data");
                JSONArray dataArray = data.getJSONArray("data");
                String count = data.getString("count");
                itemCount = Integer.parseInt(count);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemCount;
    }

    public void GetVideoInfoEntitiesAsync(final String type, final String pagesize, final String pageindex, final DataServiceListener<ArrayList<VideoInfoEntity>> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<VideoInfoEntity> entities = GetVideoInfoEntities(type, pagesize, pageindex);
                    //int itemCount = GetVideoInfoEntitiesCount(type, pagesize, pageindex);
                    int itemCount = 0;
                    dataServiceListener.onQuery(entities, itemCount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<VideoInfoEntity> GetMyResourceEntities(String type, String userid, String pagesize, String pageindex) {
        String url = "http://openapi.rice.ec/GetResources3.php";
        ArrayList<VideoInfoEntity> InfoEntitiesArray = new ArrayList<VideoInfoEntity>();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("pagesize", pagesize));
        params.add(new BasicNameValuePair("pageindex", pageindex));
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                JSONObject data = json.getJSONObject("data");
                JSONArray dataArray = data.getJSONArray("data");
                int length = dataArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject itemObject = dataArray.getJSONObject(i);
                    VideoInfoEntity entity = new VideoInfoEntity();
                    entity.Url = (String) itemObject.get("Url");
                    entity.Preview = (String) itemObject.get("Preview");
                    //   entity.Creator = (String) itemObject.get("Creator");
                    //    entity.Type = (String) itemObject.get("Type");
                    //    entity.ID = (String) itemObject.get("ID");
                    entity.Profile = (String) itemObject.get("Profile");
                    //    entity.ServerType = (String) itemObject.get("ServerType");
                    //    entity.Version = (String) itemObject.get("Version");
                    entity.Code = (String) itemObject.get("Code");
                    entity.Title = (String) itemObject.get("Title");
                    entity.Size = (String) itemObject.get("Size");
                    entity.CreateTime = itemObject.getString("CreateTime");
                    InfoEntitiesArray.add(entity);
                }
                System.out.print("");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return InfoEntitiesArray;
    }

    public void GetMyResourceEntitiesAsync(final String type, final String userid, final String pagesize, final String pageindex, final DataServiceListener<ArrayList<VideoInfoEntity>> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<VideoInfoEntity> entities = GetMyResourceEntities(type, userid, pagesize, pageindex);
                    int itemCount = 0;
                    dataServiceListener.onQuery(entities, itemCount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}