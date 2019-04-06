package com.magdamiu.archcomponents.ui.additem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.magdamiu.archcomponents.R;

import static com.magdamiu.archcomponents.ui.items.MainActivity.EXTRA_DATA_ID;
import static com.magdamiu.archcomponents.ui.items.MainActivity.EXTRA_DATA_UPDATE_ITEM;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.magdamiu.archcomponents.REPLY";
    public static final String EXTRA_REPLY_ID = "com.magdamiu.archcomponents.REPLY_ID";

    private EditText mEditWordView;
    private Bundle mExtras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mEditWordView = findViewById(R.id.edit_word);
        int id = -1;

        mExtras = getIntent().getExtras();

        if (mExtras != null) {
            String word = mExtras.getString(EXTRA_DATA_UPDATE_ITEM, "");
            if (!word.isEmpty()) {
                mEditWordView.setText(word);
                mEditWordView.setSelection(word.length());
                mEditWordView.requestFocus();
            }
        }
    }

    public void saveItemOnClick(View view) {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = mEditWordView.getText().toString();
            replyIntent.putExtra(EXTRA_REPLY, word);
            if (mExtras != null && mExtras.containsKey(EXTRA_DATA_ID)) {
                int id = mExtras.getInt(EXTRA_DATA_ID, -1);
                if (id != -1) {
                    replyIntent.putExtra(EXTRA_REPLY_ID, id);
                }
            }
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
