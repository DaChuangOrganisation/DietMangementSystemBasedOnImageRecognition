package com.example.administrator.kalulli.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.User;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateInfo extends BaseActivity {

    @BindView(R.id.btn_confirm)
    Button btnconfir;
    @BindView(R.id.username1)
    EditText username;
    String usernameStr;
    @BindView(R.id.agedit)
    EditText age;
    Integer ageInt;
    @BindView(R.id.heightedit)
    EditText height;
    Integer heightInt;
    @BindView(R.id.weightedit)
    EditText weight;
    Integer weightInt;
    @BindView(R.id.radioGroup_sex_id)
    RadioGroup radioGroupsex;
    Integer sex = 0;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    private void showToast(String txt) {
        Toast tast = Toast.makeText( this, txt, Toast.LENGTH_LONG );
        tast.setGravity( Gravity.CENTER, 0, 0 );
        View view = LayoutInflater.from( this ).inflate( R.layout.custom_toast, null );
        TextView tvMsg = (TextView) view.findViewById( R.id.tvMsg );
        tvMsg.setText( txt );
        tast.setView( view );
        tast.show();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        User user = LitePal.findFirst( User.class );
        usernameStr = user.getName();
        username.setText( usernameStr );
        ageInt = user.getAge();
        age.setText( String.valueOf( ageInt ) );
        heightInt = (int) user.getHeight();
        height.setText( String.valueOf( heightInt ) );
        weightInt = (int) user.getWeight();
        weight.setText( String.valueOf( weightInt ) );
//        sex = user.getGender()
    }

    @Override
    protected int getLayoutId() {
        return R.layout.change_info;
    }

    @OnClick(R.id.backmain)
    public void onBackRegisterImageClicked() {
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 200);
    }

}