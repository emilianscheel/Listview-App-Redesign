package de.emilianscheel.listview.Einführung;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.emilianscheel.listview.Controller.Activity;
import de.emilianscheel.listview.Einstellungen.SettingsField;
import de.emilianscheel.listview.Objekte.Defaults;
import de.emilianscheel.listview.R;

public class CheckBoxActivity extends AppCompatActivity {

    Switch switchButton;
    CheckBox checkBox;
    LinearLayout nextButton;

    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(new Defaults(this).getBool(SettingsField.IS_NIGHT_MODE.getRawValue()) ? R.style.AppTheme_Dark : R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_check_box);

        switchButton = findViewById(R.id.CheckBoxActivity_switchButton);
        checkBox = findViewById(R.id.CheckBoxActivity_checkBox);
        nextButton = findViewById(R.id.CheckBoxActivity_nextButton);

        checkBox.measure(0, 0);
        width = checkBox.getMeasuredWidth();

        showCheckBox(new Defaults(CheckBoxActivity.this).getBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue()));
        switchButton.setChecked(new Defaults(CheckBoxActivity.this).getBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue()));

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new Defaults(CheckBoxActivity.this).setBool(SettingsField.SHOWS_CHECK_BOXES.getRawValue(), isChecked);

                showCheckBox(isChecked);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Öffne die neue Aktivität.
                new Activity(CheckBoxActivity.this).open(IntroTagsActivity.class);
            }
        });
    }

    private void showCheckBox(Boolean  isChecked) {
        ValueAnimator anim;

        if (isChecked) {
            anim = ValueAnimator.ofInt(0, width);
        } else {
            anim = ValueAnimator.ofInt(width, 0);
        }

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = checkBox.getLayoutParams();
                layoutParams.width = val;
                checkBox.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(300);
        anim.start();
    }
}