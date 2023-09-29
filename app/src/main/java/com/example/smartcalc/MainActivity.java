package com.example.smartcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView solutionTv,resultTv;
    MaterialButton buttonAC,buttonDot,buttonC,buttonBracketOpen,buttonBracketClose;
    MaterialButton buttonDivide,buttonMultiply,buttonAdd,buttonSubtract,buttonEqual;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv=findViewById(R.id.result_tv);
        solutionTv=findViewById(R.id.solution_tv);

        assignID(buttonAC,R.id.btn_allClear);
        assignID(buttonDot,R.id.btn_dot);
        assignID(buttonC,R.id.btn_clear);
        assignID(buttonBracketOpen,R.id.btn_bracketOpen);
        assignID(buttonBracketClose,R.id.btn_bracketClose);
        assignID(buttonDivide,R.id.btn_divide);
        assignID(buttonMultiply,R.id.btn_multiply);
        assignID(buttonAdd,R.id.btn_add);
        assignID(buttonSubtract,R.id.btn_subtract);
        assignID(buttonEqual,R.id.btn_equal);
        assignID(button0,R.id.btn_0);
        assignID(button1,R.id.btn_1);
        assignID(button2,R.id.btn_2);
        assignID(button3,R.id.btn_3);
        assignID(button4,R.id.btn_4);
        assignID(button5,R.id.btn_5);
        assignID(button6,R.id.btn_6);
        assignID(button7,R.id.btn_7);
        assignID(button8,R.id.btn_8);
        assignID(button9,R.id.btn_9);

    }
    void assignID(MaterialButton btn, int id){
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button=(MaterialButton) v;
        String buttonText=button.getText().toString();
        String dataToCalculate=solutionTv.getText().toString();
        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        else if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText().toString());
            return;
        }
        else if (buttonText.equals("C")) {
            if(dataToCalculate.length()>0){
                dataToCalculate=dataToCalculate.substring(0,dataToCalculate.length()-1);
            }
        }
        else {
            dataToCalculate+=buttonText;
        }
        solutionTv.setText(dataToCalculate);
        if (dataToCalculate.length()>0){
            String finalResult=getResult(dataToCalculate);
            if(!finalResult.equals("err")){
                resultTv.setText(finalResult);
            }
        } else{
            resultTv.setText("0");
        }
    }
    String getResult(String data){
        try{
            Context context=Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable=context.initStandardObjects();
            String finalResult=context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult=finalResult.replace(".0","");
            }
            return finalResult;
        } catch(Exception e){
            return "err";
        }
    }
}