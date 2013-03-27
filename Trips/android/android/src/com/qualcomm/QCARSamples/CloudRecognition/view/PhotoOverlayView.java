/*==============================================================================
            Copyright (c) 2012-2013 QUALCOMM Austria Research Center GmbH.
            All Rights Reserved.
            Qualcomm Confidential and Proprietary

@file
    BookOverlayView.java

@brief
    Custom View to display the book overlay data

==============================================================================*/
package com.qualcomm.QCARSamples.CloudRecognition.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import be.kdg.android.R;


/** Custom View with Book Overlay Data */
public class PhotoOverlayView extends RelativeLayout
{
    public PhotoOverlayView(Context context)
    {
        this(context, null);
    }


    public PhotoOverlayView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }


    public PhotoOverlayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        inflateLayout(context);

    }


    /** Inflates the Custom View Layout */
    private void inflateLayout(Context context)
    {

        final LayoutInflater inflater = LayoutInflater.from(context);

        // Generates the layout for the view
        inflater.inflate(R.layout.bitmap_layout, this, true);
    }


    /** Sets Book title in View */
    public void setStopTitle(String bookTitle)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_title);
        tv.setText(bookTitle);
    }


    /** Sets Book Author in View */
    public void setStopDescription(String bookAuthor)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_content);
        tv.setText(bookAuthor);
    }
}
