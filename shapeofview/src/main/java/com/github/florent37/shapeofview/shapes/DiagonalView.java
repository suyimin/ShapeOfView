package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class DiagonalView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    @IntDef(value = {POSITION_BOTTOM, POSITION_TOP, POSITION_LEFT, POSITION_RIGHT})
    public @interface DiagonalPosition{};

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    @IntDef(value = {DIRECTION_LEFT, DIRECTION_RIGHT})
    public @interface DiagonalDirection{};

    @DiagonalPosition
    private int diagonalPosition = POSITION_TOP;
    private int diagonalDirection = POSITION_TOP;
    private int diagonalAngle = 0;

    public DiagonalView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DiagonalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DiagonalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalView);
            diagonalAngle = attributes.getInteger(R.styleable.DiagonalView_diagonal_angle, diagonalAngle);
            diagonalDirection = attributes.getInteger(R.styleable.DiagonalView_diagonal_direction, diagonalDirection);
            diagonalPosition = attributes.getInteger(R.styleable.DiagonalView_diagonal_position, diagonalPosition);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                final float perpendicularHeight = (float) (width * Math.tan(Math.toRadians(diagonalAngle)));
                final boolean isDirectionLeft = diagonalDirection == DIRECTION_LEFT;
                
                switch (diagonalPosition) {
                    case POSITION_BOTTOM:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingRight());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.close();
                        }
                        break;
                    case POSITION_TOP:
                        if (isDirectionLeft) {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_RIGHT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_LEFT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft() + perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft() + perpendicularHeight, height - getPaddingBottom());
                            path.close();
                        }
                        break;
                }
                return path;
            }
        });
    }

    public void setDiagonalPosition(@DiagonalPosition int diagonalPosition) {
        this.diagonalPosition = diagonalPosition;
        postInvalidate();
    }

    public int getDiagonalPosition() {
        return diagonalPosition;
    }

    public int getDiagonalDirection() {
        return diagonalDirection;
    }

    public void setDiagonalDirection(int diagonalDirection) {
        this.diagonalDirection = diagonalDirection;
        postInvalidate();
    }

    public int getDiagonalAngle() {
        return diagonalAngle;
    }

    public void setDiagonalAngle(int diagonalAngle) {
        this.diagonalAngle = diagonalAngle;
        postInvalidate();
    }
}
