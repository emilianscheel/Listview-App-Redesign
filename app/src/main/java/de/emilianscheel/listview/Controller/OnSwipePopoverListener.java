package de.emilianscheel.listview.Controller;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnSwipePopoverListener implements View.OnTouchListener {

    View view;
    SwipePopoverMeta meta;

    public OnSwipePopoverListener() {
        this.meta = new SwipePopoverMeta();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        this.view = v;
        // save default base layout height and width
        if (this.meta.getDefaultViewHeight() == -1) this.meta.setDefaultViewHeight(this.view.getHeight());
        if (this.meta.getDefaultViewWidth() == -1) this.meta.setDefaultViewWidth(this.view.getWidth());

        // Get finger position on screen
        this.meta.setyFingerPosition((int) event.getRawY());
        this.meta.setxFingerPosition((int) event.getRawX());

        // Switch on motion event type
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                // Init finger position
                this.meta.setPreviousYFingerPosition(this.meta.getyFingerPosition());
                this.meta.setPreviousXFingerPosition(this.meta.getxFingerPosition());
                // Set view layout position
                this.meta.setBaseLayoutYPosition((int) this.view.getY());
                this.meta.setBaseLayoutXPosition((int) this.view.getX());
                break;

            case MotionEvent.ACTION_UP:
                this.meta.setyPosition((int) this.view.getY());
                this.meta.setxPosition((int) this.view.getX());

                // Has user scroll enough to "auto close" popup?
                if (Math.abs(this.meta.getBaseLayoutYPosition() - this.meta.getyPosition()) > this.meta.getDefaultViewHeight() / 2) {

                    Log.i("LISTVIEW", "scrolledTo");

                    // If user was doing a scroll up
                    if (this.meta.isScrollingUp()) {
                        this.meta = overscrollTop(this.view, this.meta);
                        // We are not in scrolling up mode anymore
                        this.meta.setScrollingDown(false);
                        return true;
                    }
                    // If user was doing a scroll down
                    else if (this.meta.isScrollingDown()) {
                        this.meta = overscrollBottom(this.view, this.meta);
                        // We are not in scrolling down mode anymore
                        this.meta.setScrollingDown(false);
                        return true;
                    }

                } else {
                    // If user was doing a scroll up
                    if (this.meta.isScrollingUp()) {
                        this.meta = isScrollingUp(this.view, this.meta);
                    }
                    // If user was doing a scroll down
                    else if (this.meta.isScrollingDown()) {
                        this.meta = isScrollingDown(this.view, this.meta);
                        // We are not in scrolling down mode anymore
                        this.meta.setScrollingDown(false);
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (this.meta.isNotClosing()) {
                    this.meta.setyPosition((int) this.view.getY());
                    this.meta.setxPosition((int) this.view.getX());

                    // If we scroll up
                    if (this.meta.getPreviousYFingerPosition() > this.meta.getyFingerPosition()) {
                        // First time android rise an event for "up" move
                        if (this.meta.isNotScrollingUp()) {
                            this.meta.setScrollingUp(true);
                        }

                        // Expand view height from difference between previous y-position and current y-position.
                        this.view.getLayoutParams().height = this.view.getHeight() + (this.meta.getPreviousYFingerPosition() - this.meta.getyFingerPosition());
                        this.view.requestLayout();
                    }
                    // If we scroll down
                    else {
                        // Reset View Height to default view height.
                        this.view.getLayoutParams().height = this.meta.getDefaultViewHeight();
                        this.view.requestLayout();

                        if (this.meta.isNotScrollingDown()) {
                            // First time android rise an event for "down" move
                            this.meta.setScrollingDown(true);
                        }
                    }
                    // Change base layout size and position (must change position because view anchor is top left corner)
                    this.view.setY(this.view.getY() + (this.meta.getyFingerPosition() - this.meta.getPreviousYFingerPosition()));

                    // Update previos finger position
                    this.meta.setPreviousYFingerPosition(this.meta.getyFingerPosition());
                }
                break;
        }
        return true;
    }

    public void resetY() {
        ObjectAnimator.ofFloat(this.view, "y", 0).setDuration(300).start();
    }

    public void resetX() {
        ObjectAnimator.ofFloat(this.view, "x", 0).setDuration(300).start();
    }

    public void resetHeight() {
        view.getLayoutParams().height = this.meta.getDefaultViewHeight();
        view.requestLayout();
    }

    public void resetWidth() {
        view.getLayoutParams().width = this.meta.getDefaultViewWidth();
        view.requestLayout();
    }

    public void close() {
        int screenHeight = new Point().y;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", this.meta.getyPosition(), screenHeight + this.view.getHeight());
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                meta = hasClosed(view, meta);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public SwipePopoverMeta hasClosed(View view, SwipePopoverMeta newMeta) {
        return newMeta;
    }

    public SwipePopoverMeta isScrollingUp(View view, SwipePopoverMeta newMeta) {
        // Reset baselayout position
        this.resetY();
        return newMeta;
    }

    public SwipePopoverMeta isScrollingDown(View view, SwipePopoverMeta newMeta) {
        // Reset baselayout position
        this.resetY();
        // Reset base layout size
        this.resetHeight();
        return newMeta;
    }

    public SwipePopoverMeta overscrollTop(View view, SwipePopoverMeta newMeta) {
        // Start animation, which closes the activity.
        this.close();
        return newMeta;
    }

    public SwipePopoverMeta overscrollBottom(View view, SwipePopoverMeta newMeta) {
        // Start animation, which closes the activity.
        this.close();
        return newMeta;
    }
}

