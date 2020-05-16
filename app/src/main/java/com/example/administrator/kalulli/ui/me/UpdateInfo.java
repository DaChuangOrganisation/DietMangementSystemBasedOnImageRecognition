package com.example.administrator.kalulli.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.example.administrator.kalulli.BasicInfoInput;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.utils.TableUtil;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateInfo extends BaseActivity {
    @BindView( R.id.xiugaibtn )
    Button confirbtn;
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
    String sex;
    RadioButton rb;
    User userinfo=LitePal.findFirst( User.class );

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
        sex = user.getGender();
        if(sex.equals( "男" )){
            rb = (RadioButton) UpdateInfo.this.findViewById(R.id.boy_id);
            rb.setChecked( true );
        }
        else{
            rb = (RadioButton) UpdateInfo.this.findViewById(R.id.girl_id);
            rb.setChecked( true );
        }
        confirbtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                savechangeinfo();
            }
        } );
    }
    private void selectRadioBtn(){
        RadioButton rb1;
        rb1 = (RadioButton) UpdateInfo.this.findViewById( radioGroupsex.getCheckedRadioButtonId());
        String choice = rb1.getText().toString();
        if(choice.equals( "♂男" ))
        {sex = "男";
//            Toast.makeText( BasicInfoInput.this, "已选择 男", Toast.LENGTH_LONG ).show();
        }
        else if(choice.equals( "♀女" ))
        {sex = "女";
//            Toast.makeText( BasicInfoInput.this, "已选择 女", Toast.LENGTH_LONG ).show();

        }
        else {
//            Toast.makeText( BasicInfoInpu.this, "请选择性别", Toast.LENGTH_LONG ).show();

            sex = "中性";
        }
    }

    public void savechangeinfo() {
        Toast.makeText( this,"已点击按钮",Toast.LENGTH_SHORT );
        if (username.getText().toString()!=null&&!username.getText().toString().equals( "" )&&
                age.getText().toString()!=null&&!age.getText().toString().equals( "" )&&
                height.getText().toString()!=null&&!height.getText().toString().equals( "" )&&
                weight.getText().toString()!=null&&!weight.getText().toString().equals( "" )&&
                sex!=null&&!sex.equals( "" ))
        {
            usernameStr = username.getText().toString();
            ageInt = Integer.parseInt( age.getText().toString() );
            heightInt = Integer.parseInt( height.getText().toString() );
            weightInt = Integer.parseInt( weight.getText().toString() );
            if(heightInt>220||heightInt<150){
                Toast.makeText(this,"请重新输入身高（范围:150-220)", Toast.LENGTH_LONG).show();
            }
            else if (weightInt>100||weightInt<40){
                Toast.makeText(this,"请重新输入体重（范围：40-100）", Toast.LENGTH_LONG).show();
            }
            else {
                usernameStr = username.getText().toString();
                ageInt = Integer.parseInt( age.getText().toString() );
                heightInt = Integer.parseInt( height.getText().toString() );
                weightInt = Integer.parseInt( weight.getText().toString() );
                selectRadioBtn();
                userinfo.setAge( ageInt );
                userinfo.setHeight( heightInt );
                userinfo.setName( usernameStr );
                userinfo.setWeight( weightInt );
                userinfo.setGender( sex );
                userinfo.saveOrUpdateData();
                Toast.makeText( this,"基本信息修改成功",Toast.LENGTH_SHORT );
                finish();
            }
        }
        else{
            Toast.makeText( this,"基本信息不能为空",Toast.LENGTH_LONG );
        }
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