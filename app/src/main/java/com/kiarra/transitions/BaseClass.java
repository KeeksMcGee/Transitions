package com.kiarra.transitions;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BaseClass extends AppCompatActivity {

    //Constant used for the Intent to indicate the type of transition.
    private static final String TRANSITION_TYPE = "Transition Type";

    //Constants used for shared element transitions.
    private static final String ANDROID_TRANSITION = "switchAndroid";
    private static final String BLUE_TRANSITION = "switchBlue";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Get the transition type from the Intent and set it.
        if(getIntent().hasExtra(TRANSITION_TYPE)){
            switch(getIntent().getStringExtra(TRANSITION_TYPE)){
                case "Explode":
                    getWindow().setEnterTransition(new Explode());
                    break;
                case "Slide":
                    getWindow().setEnterTransition(new Slide());
                    break;
                case "Fade":
                    getWindow().setEnterTransition(new Fade());
                    break;
                    default:
                        break;
            }
        }
    }

    protected void explodeTransition(final Context context, ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Relaunch the activity with the transition information
                Intent intent = new Intent(context, context.getClass());
                intent.putExtra(TRANSITION_TYPE, "Explode");
                getWindow().setExitTransition(new Explode());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)context).toBundle());
            }
        });
    }

    protected void fadeTransition(final Context context, ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Relaunch the activity with the transition information
                Intent intent = new Intent(context,context.getClass());
                intent.putExtra(TRANSITION_TYPE, "Fade");
                getWindow().setExitTransition(new Fade());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)
                        context).toBundle());

            }
        });
    }

    protected void rotateView(ImageView imageView){
        //Create hte object animator with desired options.
        final ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 360F);
        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start it when the view is clicked.
                animator.start();
            }
        });
    }

    protected void switchAnimation(final ImageView androidImage, final ImageView otherImage,
                                   final Intent intent, final Context context){
        androidImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the transition details and start the seocnd activity.
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context,
                        Pair.create((View)androidImage, ANDROID_TRANSITION),
                        Pair.create((View)otherImage,BLUE_TRANSITION));

                startActivity(intent,options.toBundle());
            }
        });
    }
}
