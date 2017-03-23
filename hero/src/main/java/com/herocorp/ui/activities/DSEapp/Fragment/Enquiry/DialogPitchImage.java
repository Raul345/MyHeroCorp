package com.herocorp.ui.activities.DSEapp.Fragment.Enquiry;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.herocorp.R;
import com.herocorp.ui.activities.DSEapp.Pitch.ImageLoader;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by rsawh on 13-Feb-17.
 */

public class DialogPitchImage extends DialogFragment implements View.OnTouchListener {
    ImageView scalepitchimage;
    LinearLayout layout_pitch;

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mmode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dse_dialog_pitchimage, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        init(view);
        return view;
    }

    public void init(View rootView) {
        scalepitchimage = (ImageView) rootView.findViewById(R.id.scalepitchimage);

        layout_pitch = (LinearLayout) rootView.findViewById(R.id.layout_pitch);
        PhotoViewAttacher mAttacher;
        mAttacher = new PhotoViewAttacher(scalepitchimage);
        //     scalepitchimage.setOnTouchListener(this);

        Bundle bundle = this.getArguments();
        if (bundle.containsKey("id"))
            id = bundle.getString("id");
        if (bundle.containsKey("url")) {
            ImageLoader imageLoader = new ImageLoader(getContext());
            imageLoader.DisplayImage(bundle.getString("url"), scalepitchimage);
            mAttacher.update();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mmode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                mmode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mmode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float dx; // postTranslate X distance
                float dy; // postTranslate Y distance
                float[] matrixValues = new float[9];
                float matrixX = 0; // X coordinate of matrix inside the ImageView
                float matrixY = 0; // Y coordinate of matrix inside the ImageView
                float width = 0; // width of drawable
                float height = 0; // height of drawable


                if (mmode == DRAG) {
                    matrix.set(savedMatrix);

                    matrix.getValues(matrixValues);
                    matrixX = matrixValues[2];
                    matrixY = matrixValues[5];
                    width = matrixValues[0] * (((ImageView) view).getDrawable()
                            .getIntrinsicWidth());
                    height = matrixValues[4] * (((ImageView) view).getDrawable()
                            .getIntrinsicHeight());

                    dx = event.getX() - start.x;
                    dy = event.getY() - start.y;

                    //if image will go outside left bound
                    if (matrixX + dx < 0) {
                        dx = -matrixX;
                    }
                    //if image will go outside right bound
                    if (matrixX + dx + width > view.getWidth()) {
                        dx = view.getWidth() - matrixX - width;
                    }
                    //if image will go oustside top bound
                    if (matrixY + dy < 0) {
                        dy = -matrixY;
                    }
                    //if image will go outside bottom bound
                    if (matrixY + dy + height > view.getHeight()) {
                        dy = view.getHeight() - matrixY - height;
                    }
                    matrix.postTranslate(dx, dy);



                 /*   matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
               */
                } else if (mmode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled

    }

     /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Show an event in the LogCat view, for debugging
     */
    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}
