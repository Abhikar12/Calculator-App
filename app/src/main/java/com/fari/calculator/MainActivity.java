package com.fari.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.telecom.Call;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    TextView in_screen, out_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        in_screen = (TextView)findViewById(R.id.input_screen);
        out_screen = (TextView)findViewById(R.id.output_screen);
        in_screen.setMovementMethod(new ScrollingMovementMethod());
    }

    public void operator(View v)
    {
        Button b = (Button) v;
        if(out_screen.getText().toString().compareTo("Syntax Error!") == 0)
            out_screen.setText("");
        if(out_screen.getText().toString()!="")
        {
            in_screen.setText(out_screen.getText());
            out_screen.setText("");
        }
        in_screen.append(b.getText());
    }

    public void number(View v)
    {
        Button b = (Button) v;
        if(out_screen.getText().toString().compareTo("Syntax Error!") == 0)
            out_screen.setText("");
        if(out_screen.getText().toString()!="")
        {
            in_screen.setText(out_screen.getText());
            out_screen.setText("");
        }
        in_screen.append(b.getText());
    }

    public void equal(View v)
    {
        try {
            InfixToPostfix obj = new InfixToPostfix(in_screen.getText().toString());
            display(obj.evaluationOfPostfix());
        }
        catch (Exception e)
        {
            if(in_screen.getText().toString().compareTo("") != 0)
                out_screen.setText("Syntax Error!");
            e.printStackTrace();
        }
    }

    public void display(String val)
    {
        boolean flag = true; int i=0;
        for(i=0;i<val.length();i++)
            if(val.charAt(i)=='.')
            {
                i++;
                break;
            }
        for(;i<val.length();i++)
            if(val.charAt(i)!='0')
                flag = false;
        if(!flag)
            out_screen.setText(val);
        else
            out_screen.setText(Long.toString(Math.round(Double.parseDouble(val))));
    }

    public void clear(View v)
    {
        in_screen.setText("");
        out_screen.setText("");
    }

    public void back(View v)
    {
        out_screen.setText("");
        try {
            StringBuffer str = new StringBuffer(in_screen.getText());
            str.setLength(str.length() - 1);
            in_screen.setText(str);
        }
        catch (Exception e)
        { e.printStackTrace(); }
    }
}
