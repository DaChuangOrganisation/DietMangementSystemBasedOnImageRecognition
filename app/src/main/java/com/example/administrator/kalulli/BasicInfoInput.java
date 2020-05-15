package com.example.administrator.kalulli;

import android.content.Intent;
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

import butterknife.BindView;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.litepal.User;
import com.example.administrator.kalulli.utils.ExcelUtil;
import com.example.administrator.kalulli.utils.TestUtil;

public class BasicInfoInput extends BaseActivity {
    boolean sexinput = false;
    public boolean alreadyinput=false;
    @BindView( R.id.btn_confirm )
    Button btnconfir;
    @BindView( R.id.username1 )
    EditText username;
    String usernameStr;
    @BindView( R.id.agedit )
    EditText age;
    Integer ageInt;
    @BindView( R.id.heightedit )
    EditText height;
    Integer heightInt;
    @BindView( R.id.weightedit )
    EditText weight;
    Integer weightInt;
    @BindView( R.id.radioGroup_sex_id )
    RadioGroup radioGroupsex;
    Integer sex=0;
    User userinfo = new User(  );
    String gender1 ;
    String boolStr;
    private int selectRadioBtn(){
        RadioButton rb;
        rb = (RadioButton) BasicInfoInput.this.findViewById( radioGroupsex.getCheckedRadioButtonId());
        String choice = rb.getText().toString();
        int sex1;
        if(choice.equals( "♂男" ))
        {sex1 = 1; sexinput = true;
//            Toast.makeText( BasicInfoInput.this, "已选择 男", Toast.LENGTH_LONG ).show();
        }
        else if(choice.equals( "♀女" ))
        {sex1 = 2; sexinput = true;
//            Toast.makeText( BasicInfoInput.this, "已选择 女", Toast.LENGTH_LONG ).show();

        }
        else {
//            Toast.makeText( BasicInfoInpu.this, "请选择性别", Toast.LENGTH_LONG ).show();
            sexinput = false;
            sex1 = 0;
        }
        return sex1;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mContext = this;
//        Toast.makeText(BasicInfoInput.this,"oncreate()", Toast.LENGTH_LONG).show();

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
                saveuserinfo();
            }
        } );
    }
    public void saveuserinfo(){
        if (username.getText().toString()!=null&&!username.getText().toString().equals( "" )&&
                age.getText().toString()!=null&&!age.getText().toString().equals( "" )&&
                height.getText().toString()!=null&&!height.getText().toString().equals( "" )&&
                weight.getText().toString()!=null&&!weight.getText().toString().equals( "" )&&
                sex!=null&&sex!=0){
            usernameStr = username.getText().toString();
            ageInt = Integer.parseInt( age.getText().toString() );
            heightInt = Integer.parseInt( height.getText().toString() );
            weightInt = Integer.parseInt( weight.getText().toString() );
            if(sex==1){
                gender1 = "男";
            }
            else
                gender1 = "女";
            if(heightInt>220||heightInt<150){
                Toast.makeText(this,"请重新输入身高（范围:150-220)", Toast.LENGTH_LONG).show();
            }
            else if (weightInt>100||weightInt<40){
                Toast.makeText(this,"请重新输入体重（范围：40-100）", Toast.LENGTH_LONG).show();
            }
            else{
                userinfo.setAge( ageInt );
                userinfo.setHeight( heightInt );
                userinfo.setName( usernameStr );
                userinfo.setWeight( weightInt );
                userinfo.setGender( gender1 );
                userinfo.saveOrUpdateData();
                bl = true;
                boolStr = "true";
                SharePreUtil.put( mContext,"boolStr",boolStr );
//            Toast.makeText(BasicInfoInput.this,"boolStr==true！", Toast.LENGTH_LONG).show();
//            ExcelUtil.importSheetToDB( this,"FoodCalorieData.xls" );
                Toast.makeText(this,"正在进入，请稍后...", Toast.LENGTH_LONG).show();

                /*提示信息*/
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep( 1000 );
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                        }
//            }).start();
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 1000);
                Intent it = new Intent(  );
                it.setClass( BasicInfoInput.this,MainActivity.class );
                BasicInfoInput.this.startActivity( it );

                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        }

        else{
            Toast.makeText(this,"请填写基本信息！", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {

    }
    private void showToast(String txt){
        Toast tast=Toast.makeText(this, txt, Toast.LENGTH_LONG);
        tast.setGravity( Gravity.CENTER, 0, 0);
        View view= LayoutInflater.from(this).inflate(R.layout.custom_toast, null);
        TextView tvMsg=(TextView)view.findViewById( R.id.tvMsg);
        tvMsg.setText(txt);
        tast.setView(view);
        tast.show();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.info_input;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TestUtil.test(this);//用于测试
    }
}
