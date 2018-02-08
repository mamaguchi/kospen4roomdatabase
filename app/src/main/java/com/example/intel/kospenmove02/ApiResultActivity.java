package com.example.intel.kospenmove02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ApiResultActivity extends AppCompatActivity {

    private TextView apiOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_result);

        apiOutput = (TextView) findViewById(R.id.apiOutputId);

        Bundle apiResult = getIntent().getExtras();
        if(apiResult==null) {
            apiOutput.setText("api result is NUll!");
        }
        String apiResultText = apiResult.getString("apiResult");
        if(apiResultText==null || apiResultText.isEmpty()) {
            apiOutput.setText("api result text is null or empty!");
        }
        apiOutput.setText(apiResultText);
    }

    public void returnMainBtnClicked(View view) {
        Intent i = new Intent(this, MainActivity.class);
        apiOutput.setText("");
        startActivity(i);
    }

}
