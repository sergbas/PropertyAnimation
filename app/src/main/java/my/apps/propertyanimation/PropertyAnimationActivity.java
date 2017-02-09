package my.apps.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class PropertyAnimationActivity extends Activity {

    AnimatorSet sunAnimatorSet;
    AnimatorSet cloud1AnimatorSet;
    AnimatorSet cloud2AnimatorSet;
    ValueAnimator skyAnimator;
    ValueAnimator groundAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);


        sunAnimatorSet = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.animator.sun_movement);
        ImageView sun = (ImageView) findViewById(R.id.sun);
        sunAnimatorSet.setTarget(sun);

        sunAnimatorSet.addListener(

                new AnimatorListenerAdapter() {

                    public void onAnimationStart(Animator animation) {
                        Toast.makeText(getApplicationContext(), "Animation started!",
                                Toast.LENGTH_SHORT).show();
                    }

                    public void onAnimationEnd(Animator animation) {
                        Toast.makeText(getApplicationContext(), "Animation ended!",
                                Toast.LENGTH_SHORT).show();
                    }

                });

        // cloud1
        ImageView cloud1 = (ImageView) findViewById(R.id.cloud1);
        cloud1AnimatorSet = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.animator.cloud_movement);
        cloud1AnimatorSet.setTarget(cloud1);

        // cloud2
        ImageView cloud2 = (ImageView) findViewById(R.id.cloud2);
        cloud2AnimatorSet = (AnimatorSet)AnimatorInflater.loadAnimator(this, R.animator.cloud_movement);
        cloud2AnimatorSet.setTarget(cloud2);

        skyAnimator = ObjectAnimator.ofInt
                (findViewById(R.id.sky), "backgroundColor",
                        Color.rgb(0x00, 0x00, 0x4c), Color.rgb(0xae, 0xc2, 0xff));
        //set same duration and animation properties as others
        skyAnimator.setDuration(10000);
        skyAnimator.setEvaluator(new ArgbEvaluator());
        skyAnimator.setRepeatCount(ValueAnimator.INFINITE);
        skyAnimator.setRepeatMode(ValueAnimator.REVERSE);

        skyAnimator.addUpdateListener(

                new ValueAnimator.AnimatorUpdateListener() {

                    TextView textView = (TextView) findViewById(R.id.textView);
                    float animatedFractionPrev = 0.0f;
                    float animatedFractionCurr = 0.0f;

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        animatedFractionCurr = valueAnimator.getAnimatedFraction();
                        if (animatedFractionCurr > animatedFractionPrev) {
                            if (animatedFractionCurr > 0.0 && animatedFractionCurr <= 0.70) {
                                textView.setText("Good morning!");
                            } else {
                                textView.setText("Good day!");
                            }
                        } else {
                            if (animatedFractionCurr >= 0.8) {
                                textView.setText("Good day!");
                            } else if (animatedFractionCurr < 0.8 && animatedFractionCurr >= 0.1) {
                                textView.setText("Good afternoon!");
                            } else {
                                textView.setText("Good Evening!");
                            }
                        }
                        animatedFractionPrev = animatedFractionCurr;
                    }
                }
        );




        groundAnimator = ObjectAnimator.ofInt
                (findViewById(R.id.ground), "backgroundColor",
                        Color.rgb(0x00, 0x47, 0x00), Color.rgb(0x85, 0xae, 0x85));
        //set same duration and animation properties as others
        groundAnimator.setDuration(10000);
        groundAnimator.setEvaluator(new ArgbEvaluator());
        groundAnimator.setRepeatCount(ValueAnimator.INFINITE);
        groundAnimator.setRepeatMode(ValueAnimator.REVERSE);



    }

    public void onToggleClicked(View view) {

        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            sunAnimatorSet.start();
            skyAnimator.start();
            groundAnimator.start();
            cloud1AnimatorSet.start();
            cloud2AnimatorSet.start();
        } else {
            sunAnimatorSet.cancel();
            skyAnimator.cancel();
            groundAnimator.cancel();
            cloud1AnimatorSet.cancel();
            cloud2AnimatorSet.cancel();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((ToggleButton)findViewById(R.id.toggleAnimate)).setChecked(false);
    }
}