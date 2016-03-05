package rice.ec.vr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MessageTools {
    private final static boolean DebugMode = false;
    private static ArrayList<String> DebugMessages = new ArrayList<String>();
    private Context _context;

    public static void showMessageDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public MessageTools(Context context) {
        _context = context;
    }

    public void ShowToast(String message) {
        Toast toast = Toast.makeText(_context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public synchronized void ShowDebugToast(String message) {
        if (DebugMode) {
            Toast toast = Toast.makeText(_context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Log.d("MyDebug", "MyDebug: " + message);
            /*DebugMessages.add(message);
            Intent intent = new Intent();
            intent.setAction(MyWifiUtil.MYWIFI_DEBUG_SENDTOCLIENT);
            intent.putStringArrayListExtra(MyWifiUtil.EXTRA_MYWIFI_DEBUGINFO, DebugMessages);
            _context.sendBroadcast(intent);*/
        }
    }

    public ArrayList<String> GetDebugMessages() {
        return DebugMessages;
    }
}
