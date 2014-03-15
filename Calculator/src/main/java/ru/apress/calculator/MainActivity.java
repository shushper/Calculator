package ru.apress.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements Keyboard.KeyboardListener {
    private final String TAG = "MainActivity";
    private final boolean D = true;

    private TextView mText;
    private Keyboard mKeyboard;

    boolean resultOnTheScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.textView);
        mKeyboard = (Keyboard) findViewById(R.id.keyboard);
        mKeyboard.setKeyboardListener(this);
    }


    @Override
    public void onBtnClick(char ch) {
        /*
         * Нажата кнопка на клавиатуре. Выводим на экран
         * символ, соответствующий кнопке. Если на экране был
         * результат предыдущей операции - очищаем экран.
         */
        if (resultOnTheScreen) {
            mText.setText("");
            resultOnTheScreen = false;
        }

        StringBuffer sb = new StringBuffer( mText.getText());
        sb.append(ch);
        mText.setText(sb.toString());
    }

    @Override
    public void onEqualsClick() {
        /*
         * Нажата кнопка равно =. Необходимо вычислить результат
         * математического выражения, отображаемого на экране.
         */


        if (resultOnTheScreen) return;

        String infixStr = mText.getText().toString();

        if (TextUtils.isEmpty(infixStr)) return;

        /* Переводим выражение в обратную польскую нотацию */
        String postfixStr = PostfixNotationUtils.convertInfixToPostfix(infixStr);

        if (postfixStr == null) {
            showError();
            return;
        }

        /* Вычисляем выражение, записанное в обратной польской нотации */
        String result = PostfixNotationUtils.calculatePostfixExpression(postfixStr);

        if (result == null) {
            showError();
            return;
        }

        mText.setText(result);
        if(D) Log.d(TAG, "Result = " + result);

        resultOnTheScreen = true;
    }

    @Override
    public void onClearClick() {
        if (resultOnTheScreen) {
            /* Если на экране результат - очищаем полностью */
            mText.setText("");
            resultOnTheScreen = false;
        } else {
            /* Удаляем один символ из выражения на экране */
            CharSequence sequence = mText.getText();
            if(sequence.length() == 0) return;
            sequence = sequence.subSequence(0, sequence.length() - 1);
            mText.setText(sequence);
        }
    }

    @Override
    public void onClearLongClick() {
        /*
         * При долгом нажатии на кнопку CLR очищаем экран
         * полностью.
         */
        mText.setText("");
        resultOnTheScreen = false;
    }

    /**
     * Выводит на экран сообщение об ошибке.
     */
    public void showError(){
        mText.setText(R.string.error);
        if(D) Log.d(TAG, "Result = " + "ERROR");
        resultOnTheScreen = true;
    }
}
