package rice.ec.vr;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据服务
 */
public class UserDataService {
    /*
        注册新用户
        @username:用户名称
        @password:登陆密码
        @return: 返回true代表注册成功，false代表用户已存在
     */

    private final String SERVICE_PATH = "http://passport.waitme.mobi:20080/services/";

    public boolean Register(String username, String password, String TokenID, String Code) {
        String url = SERVICE_PATH + "user/register.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mobile", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("verifytokenid", TokenID));
        params.add(new BasicNameValuePair("verifycode", Code));
        UserEntity entity = new UserEntity();
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                String code = json.getString("code");
                if (code.equals("1")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void RegisterAsync(final String username, final String password, final String TokenID, final String Code, final DataServiceListener<Boolean> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = Register(username, password, TokenID, Code);
                    dataServiceListener.onQuery(result, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    /*
        根据用户名获取用户记录
        @username:用户名称
        @return:返回用户实体对象
     */
    public UserEntity GetUserByName(String username, String password) {
        String url = SERVICE_PATH + "user/login.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mobile", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("terminalname", ""));
        params.add(new BasicNameValuePair("terminalip", ""));
        params.add(new BasicNameValuePair("terminalmac", ""));
        UserEntity entity = new UserEntity();
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                String code = json.getString("code");
                if (code.equals("1")) {
                    return null;
                } else {
                    JSONObject data = json.getJSONObject("data");
                    entity.UserName = data.getString("UserName");
                    entity.UserID = data.getString("UserID");
                    entity.NickName = data.getString("NickName");
                    entity.Phone = data.getString("Phone");
                    entity.Sex = data.getString("Sex");
                    entity.HeadImageUrl = data.getString("HeadImageUrl");
                    entity.Province = data.getString("Province");
                    entity.City = data.getString("City");
                    entity.Country = data.getString("Country");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(entity);
        return entity;
    }

    public void GetUserByNameAsync(final String username, final String password, final DataServiceListener<UserEntity> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserEntity entity = GetUserByName(username, password);
                    dataServiceListener.onQuery(entity, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void GetTokenIDAsync(final String username, final DataServiceListener<String> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String _TokenID = "";
                    String _Code = "";
                    String url = SERVICE_PATH + "getverifytoken.php";
                    HttpPost httpPost = new HttpPost(url);
                    HttpResponse httpResponse = null;
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("mobile", username));
                    params.add(new BasicNameValuePair("debug", "0"));
                    UserEntity entity = new UserEntity();
                    try {
                        // 设置httpPost请求参数
                        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        httpResponse = new DefaultHttpClient().execute(httpPost);
                        //System.out.println(httpResponse.getStatusLine().getStatusCode());
                        if (httpResponse.getStatusLine().getStatusCode() == 200) {
                            // 第三步，使用getEntity方法活得返回结果
                            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                            JSONObject json = new JSONObject(result);
                            String code = json.getString("code");
                            if (code.equals("1")) {
                                return;
                            } else {
                                JSONObject data = json.getJSONObject("data");
                                _TokenID = data.getString("TokenID");
                                _Code = data.getString("Code");
                                System.out.print("tokenid" + _TokenID);
                                System.out.println(_Code);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dataServiceListener.onQuery(_TokenID, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    /*
        修改用户密码
        @username:用户名称
        @password:登陆密码
        @return:返回true代表修改密码成功，false代表用户不存在
     */
    public boolean ChangePassword(String username, String password, final String verifytokenid, final String verifycode) {
        String url = SERVICE_PATH + "user/forget.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mobile", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("verifytokenid", verifytokenid));
        params.add(new BasicNameValuePair("verifycode", verifycode));
        UserEntity entity = new UserEntity();
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                String code = json.getString("code");
                if (code.equals("1")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void ChangePasswordAsync(final String username, final String password, final String verifytokenid, final String verifycode, final DataServiceListener<Boolean> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = ChangePassword(username, password, verifytokenid, verifycode);
                    dataServiceListener.onQuery(result, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public boolean Logout(final String username) {
        String url = SERVICE_PATH + "user/logout.php";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mobile", username));
        UserEntity entity = new UserEntity();
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                JSONObject json = new JSONObject(result);
                String code = json.getString("code");
                if (code.equals("1")) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void LogoutAsync(final String username, final DataServiceListener<Boolean> dataServiceListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = Logout(username);
                    dataServiceListener.onQuery(result, 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
