package ru.apress.calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Класс отвечает за отображение клавиатуры и за обработку нажатий клавиш.
 */
public class Keyboard extends FrameLayout implements View.OnClickListener, View.OnLongClickListener {

    private KeyboardListener mListener;

    public Keyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout =  (LinearLayout) inflater.inflate(R.layout.layout_keyboard, null);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        addView(layout);

        initButtons();
    }

    private void initButtons() {
        Button btn;

        btn = (Button) findViewById(R.id.btn_zero);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_equals);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_plus);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_one);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_two);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_three);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_minus);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_four);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_five);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_six);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_multiply);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_seven);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_eight);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_nine);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_divide);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_opn_bracket);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_cls_bracket);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_separator);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_clr);
        btn.setOnClickListener(this);
        btn.setOnLongClickListener(this);

    }

    public void setKeyboardListener(KeyboardListener l) {
        this.mListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) return;

        switch (v.getId()) {
            case R.id.btn_one:
                mListener.onBtnClick('1');
                break;
            case R.id.btn_two:
                mListener.onBtnClick('2');
                break;
            case R.id.btn_three:
                mListener.onBtnClick('3');
                break;
            case R.id.btn_four:
                mListener.onBtnClick('4');
                break;
            case R.id.btn_five:
                mListener.onBtnClick('5');
                break;
            case R.id.btn_six:
                mListener.onBtnClick('6');
                break;
            case R.id.btn_seven:
                mListener.onBtnClick('7');
                break;
            case R.id.btn_eight:
                mListener.onBtnClick('8');
                break;
            case R.id.btn_nine:
                mListener.onBtnClick('9');
                break;
            case R.id.btn_zero:
                mListener.onBtnClick('0');
                break;

            case R.id.btn_plus:
                mListener.onBtnClick('+');
                break;
            case R.id.btn_minus:
                mListener.onBtnClick('-');
                break;
            case R.id.btn_multiply:
                mListener.onBtnClick('*');
                break;
            case R.id.btn_divide:
                mListener.onBtnClick('/');
                break;
            case R.id.btn_opn_bracket:
                mListener.onBtnClick('(');
                break;
            case R.id.btn_cls_bracket:
                mListener.onBtnClick(')');
                break;
            case R.id.btn_separator:
                mListener.onBtnClick('.');
                break;

            case R.id.btn_equals:
                mListener.onEqualsClick();
                break;

            case R.id.btn_clr:
                mListener.onClearClick();
                break;

        }

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clr:
                mListener.onClearLongClick();
                break;
        }

        return false;
    }

    public interface KeyboardListener {
        /**
         * Нажата кнопка на клавиатуре. В метод передётся символ кнопки.
         * @param ch символ кнопки.
         */
        public void onBtnClick(char ch);

        /**
         * Нажата кнопка =
         */
        public void onEqualsClick();

        /**
         * Нажата кнопка очищения экрана CLR
         */
        public void onClearClick();


        /**
         * Долгое нажате кнопки очищения экрана
         */
        public void onClearLongClick();
    }
}
