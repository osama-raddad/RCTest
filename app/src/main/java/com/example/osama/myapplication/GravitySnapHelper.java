package com.example.osama.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

public class GravitySnapHelper extends LinearSnapHelper {

    @Nullable
    private OrientationHelper verticalHelper;
    @Nullable
    private OrientationHelper horizontalHelper;
    private int gravity;

    private boolean isRTL;
    private boolean isGravityHorizontal;

    private boolean isFlingingInPositiveDirection;

    /**
     * Constructor
     *
     * @param gravity The {@link Gravity} specifies the edge of the {@link RecyclerView} that items should snap to.
     */
    public GravitySnapHelper(int gravity) {
        switch (gravity) {
            case Gravity.LEFT:
            case Gravity.START:
                this.isRTL = false;
                this.gravity = Gravity.START;
                this.isGravityHorizontal = true;
                break;
            case Gravity.RIGHT:
            case Gravity.END:
                this.isRTL = true;
                this.gravity = Gravity.END;
                this.isGravityHorizontal = true;
                break;
            case Gravity.TOP:
            case Gravity.BOTTOM:
                this.gravity = gravity;
                break;
            default:
                throw new IllegalArgumentException("Illegal Gravity passed to constructor.");
        }
    }

    @Override
    @Nullable
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {
            if (gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
            } else { // END
                out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager));
            }
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager));
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager));
            }
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override
    @Nullable
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            switch (gravity) {
                case Gravity.START:
                    return findStartView(layoutManager, getHorizontalHelper(layoutManager));
                case Gravity.TOP:
                    return findStartView(layoutManager, getVerticalHelper(layoutManager));
                case Gravity.END:
                    return findEndView(layoutManager, getHorizontalHelper(layoutManager));
                case Gravity.BOTTOM:
                    return findEndView(layoutManager, getVerticalHelper(layoutManager));
                default:
                    break;
            }
        }

        return super.findSnapView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        isFlingingInPositiveDirection = isRTL ? Math.max(velocityX, velocityY) < 0 : Math.max(velocityX, velocityY) > 0;
        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }

    /**
     * Calculates the passed view's distance from the start of this parent.
     */
    private int distanceToStart(View targetView, OrientationHelper helper) {
        if (isRTL && isGravityHorizontal) {
            //TODO Check if this is still needed after updating support library. The helper currently assumes start == left and right == end.
            return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
        }
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    /**
     * Calculates the passed view's distance from the end of this parent.
     */
    private int distanceToEnd(View targetView, OrientationHelper helper) {
        if (isRTL && isGravityHorizontal) {
            //TODO Check if this is still needed after updating support library. The helper currently assumes start == left and right == end.
            return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
        }
        return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
    }

    /**
     * Returns the child view that is currently closest to the start of this parent.
     *
     * @param layoutManager The {@link RecyclerView.LayoutManager} associated with the attached
     *                      {@link RecyclerView}.
     * @param helper        The relevant {@link OrientationHelper} for the attached {@link RecyclerView}.
     * @return the child view that is currently closest to the start of this parent.
     */
    @Nullable
    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            //TODO Check if this is still needed after updating support library. The helper currently assumes start == left and right == end.
            boolean shouldReturnChild;
            if (isRTL && isGravityHorizontal) {
                shouldReturnChild = helper.getDecoratedStart(child) <= helper.getDecoratedMeasurement(child) / 2;
            } else {
                shouldReturnChild = helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2;
            }

            if (isFlingingInPositiveDirection
                    && ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null;
            } else if (shouldReturnChild) {
                return child;
            } else {
                return layoutManager.findViewByPosition(firstChild + 1);
            }
        }

        return super.findSnapView(layoutManager);
    }

    /**
     * Returns the child view that is currently closest to the end of this parent.
     *
     * @param layoutManager The {@link RecyclerView.LayoutManager} associated with the attached
     *                      {@link RecyclerView}.
     * @param helper        The relevant {@link OrientationHelper} for the attached {@link RecyclerView}.
     * @return the child view that is currently closest to the end of this parent.
     */
    @Nullable
    private View findEndView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (lastChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(lastChild);

            //TODO Check if this is still needed after updating support library. The helper currently assumes start == left and right == end.
            boolean shouldReturnChild;
            if (isRTL && isGravityHorizontal) {
                shouldReturnChild = helper.getDecoratedEnd(child) - helper.getDecoratedMeasurement(child) / 2 >= layoutManager.getPaddingLeft();
            } else {
                shouldReturnChild = helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2 <= helper.getTotalSpace();
            }

            if (!isFlingingInPositiveDirection
                    && ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0) {
                return null;
            } else if (shouldReturnChild) {
                return child;
            } else {
                return layoutManager.findViewByPosition(lastChild - 1);
            }
        }

        return super.findSnapView(layoutManager);
    }

    /**
     * Creates and returns a vertical {@link OrientationHelper} for the passed {@link RecyclerView.LayoutManager}.
     */
    @NonNull
    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return verticalHelper;
    }

    /**
     * Creates and returns a horizontal {@link OrientationHelper} for the passed {@link RecyclerView.LayoutManager}.
     */
    @NonNull
    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }
}
