package com.herocorp.ui.utility;

import android.content.Context;
import android.graphics.Typeface;

public class CustomTypeFace {

	public Typeface georgiaBold;
	public Typeface gillSans;
	public Typeface gillSansBold;
	public Typeface gillSansLight;
	public Typeface gillSansItalic;
	public Typeface gillSansBoldItalic;
	public Typeface gillSansLightItalic;

	public CustomTypeFace(Context context) {
		
		gillSans 		= Typeface.createFromAsset(context.getAssets(), "fonts/GillSans.ttf");
		gillSansBold 	= Typeface.createFromAsset(context.getAssets(), "fonts/GillSansBold.ttf");
		gillSansLight 	= Typeface.createFromAsset(context.getAssets(), "fonts/GillSansLight.ttf");
		gillSansItalic 	= Typeface.createFromAsset(context.getAssets(), "fonts/GillSansItalic.ttf");
		gillSansBoldItalic 	= Typeface.createFromAsset(context.getAssets(), "fonts/GillSansBoldItalic.ttf");
		gillSansLightItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GillsansLightItalic.ttf");
		georgiaBold 	= Typeface.createFromAsset(context.getAssets(), "fonts/GeorgiaBold.ttf");
	}
}
