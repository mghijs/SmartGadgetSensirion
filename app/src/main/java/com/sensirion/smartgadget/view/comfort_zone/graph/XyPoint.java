package com.sensirion.smartgadget.view.comfort_zone.graph;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.sensirion.smartgadget.R;

import static com.sensirion.smartgadget.utils.XmlFloatExtractor.getFloatValueFromId;

public class XyPoint extends View {
    private float mRadius = 0;
    private float mOutlineRadius = 0;

    @Nullable
    private Paint mBackgroundPaint = null;
    @Nullable
    private Paint mInnerPaint = null;
    @Nullable
    private Paint mOutlinePaint = null;

    public XyPoint(Context context) {
        super(context);
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.WHITE);
        mOutlinePaint = new Paint();
        mOutlinePaint.setAntiAlias(true);
        mOutlinePaint.setColor(Color.TRANSPARENT);

        this.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateShader();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mOutlineRadius, mOutlineRadius, mOutlineRadius, mOutlinePaint);
        canvas.drawCircle(mOutlineRadius, mOutlineRadius, mRadius, mBackgroundPaint);
        canvas.drawCircle(mOutlineRadius, mOutlineRadius, mRadius, mInnerPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int pointSize = (int) mOutlineRadius * 3;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                // Must be this size
                width = widthSize;
                break;

            case MeasureSpec.AT_MOST:
                // Can't be bigger than...
                width = Math.min(pointSize, widthSize);
                break;

            default:
                // Be whatever you want
                width = pointSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;

            case MeasureSpec.AT_MOST:
                height = Math.min(pointSize, heightSize);
                break;

            default:
                height = pointSize;
                break;
        }

        setMeasuredDimension(width, height);
    }

    private void updateShader() {
        mInnerPaint.setShader(new RadialGradient(0.4f * 2 * mRadius, 0.4f * 2 * mRadius, mRadius,
                Color.TRANSPARENT, mInnerPaint.getColor(), Shader.TileMode.CLAMP));
    }

    @SuppressWarnings("unused")
    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public void setOutlineRadius(float outlineRadius) {
        mOutlineRadius = outlineRadius;
    }

    @Nullable
    @SuppressWarnings("unused")
    public Paint getInnerPaint() {
        return mInnerPaint;
    }

    public int getInnerColor() {
        return mInnerPaint.getColor();
    }

    public void setInnerColor(int color) {
        mInnerPaint.setColor(color);
        updateShader();
    }

    public void setOutlineColor(int color) {
        mOutlinePaint.setColor(color);
    }

    public void animateMove(float relativeX, float relativeY) {
        ObjectAnimator moverX = ObjectAnimator.ofFloat(this,
                "translationX",
                getX(),
                relativeX);
        ObjectAnimator moverY = ObjectAnimator.ofFloat(this,
                "translationY",
                getY(),
                relativeY);

        AnimatorSet move = new AnimatorSet();
        move.setDuration(getResources().getInteger(R.integer.comfort_zone_point_movement_duration));
        move.setInterpolator(new DecelerateInterpolator());
        move.playTogether(moverX, moverY);
        move.start();
        setVisibility(VISIBLE);
    }

    public void animateTouch() {
        bringToFront();

        final ObjectAnimator outX = ObjectAnimator.ofFloat(this, "scaleX", getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_outline_size), getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_outline_size_clicked));
        final ObjectAnimator outY = ObjectAnimator.ofFloat(this, "scaleY", getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_outline_size), getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_outline_size_clicked));
        final ObjectAnimator inX = ObjectAnimator.ofFloat(this, "scaleX", getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_inner_point_size), getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_inner_point_size_clicked));
        final ObjectAnimator inY = ObjectAnimator.ofFloat(this, "scaleY", getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_inner_point_size), getFloatValueFromId(getContext(), R.dimen.comfort_zone_point_inner_point_size_clicked));

        AnimatorSet onTouchAnimation = new AnimatorSet();
        onTouchAnimation.playTogether(outX, outY, inX, inY);
        onTouchAnimation.start();
    }

}