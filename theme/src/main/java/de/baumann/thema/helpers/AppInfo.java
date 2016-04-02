package de.baumann.thema.helpers;

import android.graphics.drawable.Drawable;

public class AppInfo
{
	private String code = null;
	private String name = null;
	private Drawable icon;
	private boolean selected = false;

	public AppInfo(String paramString1, String paramString2, Drawable paramDrawable)
	{
		code = paramString1;
		name = paramString2;
		icon = paramDrawable;
		selected = false;
	}

	public String getCode()
	{
		return code;
	}

	public Drawable getImage()
	{
		return icon;
	}

	public String getName()
	{
		return name;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setCode(String paramString)
	{
		code = paramString;
	}

	public void setImage(Drawable paramDrawable)
	{
		icon = paramDrawable;
	}

	public void setName(String paramString)
	{
		name = paramString;
	}

	public void setSelected(boolean paramBoolean)
	{
		selected = paramBoolean;
	}


}