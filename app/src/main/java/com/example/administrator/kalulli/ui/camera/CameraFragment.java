package com.example.administrator.kalulli.ui.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.utils.SampleUtil;
import com.example.administrator.kalulli.utils.SaveBitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {


    private static final String TAG = "CameraFragment";
    @BindView(R.id.camera)
    JCameraView camera;
    Unbinder unbinder;
    private static JSONObject jsonObject;
    private static String str;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(getActivity(),CameraResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("json",jsonObject.toString());
            bundle.putString("str",str);
            intent.putExtras(bundle);
            Log.i(TAG, "handleMessage: "+jsonObject.toString());
            //Toast.makeText(getContext(), "识别成功", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    };

    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment getInstance() {
        return new CameraFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        unbinder = ButterKnife.bind(this, view);
        camera.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        camera.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        camera.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(final Bitmap bitmap) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 传入可选参数调用接口
                        Log.i(TAG, "run: size = "+bitmap.getByteCount());
                        HashMap<String, String> options = new HashMap<String, String>();
                        options.put("top_num", "1");
                        options.put("filter_threshold", "0.7");
                        options.put("baike_num", "1");
                        str = SaveBitmap.saveImageToGallery(getActivity(),bitmap);
                        //API识别获得数据
//                        JSONObject res = SampleUtil.client.dishDetect(str, options);
//                        Log.i(TAG, "run: "+res.toString());
//                        //Log.i(TAG, "run: "+res.toString());
//                        jsonObject = res;

                        //以下手动获得数据便于测试
                        try {
                            jsonObject = getJsonObject();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //以上手动获得数据便于测试

                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();

                //Log.i(TAG, "captureSuccess: "+jsonObject.toString());

/*                if (jsonObject != null){
                    Intent intent = new Intent();
                    intent.putExtra("json",jsonObject.toString());
                    Toast.makeText(getContext(), "识别成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }*/

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {

            }
        });
        //左边按钮点击事件
        camera.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        camera.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

        return view;



    }

    //创建json用于测试
    private JSONObject getJsonObject() throws JSONException {
        JSONObject baike=new JSONObject();
        baike.put("baike_url", "http://baike.baidu.com/item/%E9%85%B8%E6%B1%A4%E9%B1%BC/1754055");
        baike.put("description", "酸汤鱼，是黔桂湘交界地区的一道侗族名菜");

        JSONObject result=new JSONObject();
        result.put("calorie", "200");
        result.put("has_calorie", 1);
        result.put("name", "酸菜鱼");
        result.put("baike_info", baike);

        JSONArray result2=new JSONArray();
        result2.put(result);


        JSONObject ob1=new JSONObject();
        ob1.put("result_num", 1);
        ob1.put("log_id", 12456);
        ob1.put("result", result2);

        return ob1;
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        camera.onPause();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}