package top.sclab.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class JavaScriptComponent {

    public static final String JS_COMPONENT_NAME = "sclab";

    private final Activity context;

    private final WebView webView;

    public JavaScriptComponent(Activity context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }

    public Activity getContext() {
        return context;
    }

    public WebView getWebView() {
        return webView;
    }

    @JavascriptInterface
    @SuppressLint("SourceLockedOrientationActivity")
    public void screenOrientation(String orientation) {
        switch (orientation) {
            case Constant.SCREEN_ORIENTATION_LANDSCAPE:
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Constant.SCREEN_ORIENTATION_PORTRAIT:
            default:
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @JavascriptInterface
    public void reloadApplication() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(Constant.APPLICATION_URL);
            }
        });
    }

    private volatile boolean isExit;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.EXIT_APPLICATION_CODE) {
                isExit = false;
            }
        }
    };

    @JavascriptInterface
    public void backToIndex() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(context.getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(Constant.EXIT_APPLICATION_CODE, 2000);
        } else {
            context.finish();
        }
    }

    // NoJavascriptInterface
    public void clickBack() {
        webView.loadUrl(String.format("javascript:window.%s.clickBack();", JS_COMPONENT_NAME));
    }
}
