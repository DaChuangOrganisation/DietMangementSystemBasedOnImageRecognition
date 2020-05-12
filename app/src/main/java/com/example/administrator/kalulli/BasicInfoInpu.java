package com.example.administrator.kalulli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.User;

import static com.avos.avoscloud.LogUtil.log.show;

public class BasicInfoInpu extends BaseActivity {
    boolean sexinput = false;


    @BindView( R.id.btn_confirm )
    Button btnconfir;
    @BindView( R.id.username1 )
    EditText username;
    String usernameStr;
    @BindView( R.id.agedit )
    EditText age;
    int ageInt;
    @BindView( R.id.heightedit )
    EditText height;
    int heightInt;
    @BindView( R.id.weightedit )
    EditText weight;
    int weightInt;
    @BindView( R.id.radioGroup_sex_id )
    RadioGroup radioGroupsex;
    int sex;
    User userinfo;
    private int selectRadioBtn(){
        RadioButton rb;
        rb = (RadioButton) BasicInfoInpu.this.findViewById( radioGroupsex.getCheckedRadioButtonId());
        String choice = rb.getText().toString();
        int sex1;
        if(choice.equals( "男" ))
        {sex1 = 1; sexinput = true;}
        else if(choice.equals( "女" ))
        {sex1 = 2; sexinput = true;}
        else {
//            Toast.makeText( BasicInfoInpu.this, "请选择性别", Toast.LENGTH_LONG ).show();
            sexinput = false;
            sex1 = 0;
        }
        return sex;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        radioGroupsex.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                Toast.makeText(BasicInfoInpu.this,selectRadioBtn(), Toast.LENGTH_LONG).show();
                sex = selectRadioBtn();
            }
        } );
        btnconfir.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                user
                Intent it = new Intent(  );
                it.setClass( BasicInfoInpu.this,MainActivity.class );
                BasicInfoInpu.this.startActivity( it );
                mActivity.finish();
            }
        } );
    }
    public void saveuserinfo(){
        usernameStr = username.getText().toString();
        ageInt = Integer.parseInt( age.getText().toString() );
        heightInt = Integer.parseInt( height.getText().toString() );
        weightInt = Integer.parseInt( weight.getText().toString() );
        userinfo.setAge( ageInt );
        userinfo.setHeight( heightInt );
        userinfo.
    }
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_input;
    }
}
