package com.acuity.rs.api;


//Generated by the injector on run.

public interface RSHitsplatDefinition extends RSCacheableNode {

	java.lang.String getAmount();

	int getComparisonType();

	int getDuration();

	int getFade();

	int getFontId();

	int getIconId();

	int getLeftSpriteId();

	int getMiddleSpriteId();

	int getOffsetX();

	int getOffsetY();

	int getRightSpriteId();

	int getTextColor();

	int[] getTransformIds();

	int getVarpbitIndex();

	int getVarpIndex();

	com.acuity.rs.api.RSSpritePixels invokeGetIcon(int var0);

	com.acuity.rs.api.RSSpritePixels invokeGetLeftSprite(short var0);

	com.acuity.rs.api.RSSpritePixels invokeGetMiddleSprite(int var0);

	com.acuity.rs.api.RSSpritePixels invokeGetRightSprite(byte var0);

	void setAmount(java.lang.String var0);

	void setComparisonType(int var0);

	void setDuration(int var0);

	void setFade(int var0);

	void setFontId(int var0);

	void setIconId(int var0);

	void setLeftSpriteId(int var0);

	void setMiddleSpriteId(int var0);

	void setOffsetX(int var0);

	void setOffsetY(int var0);

	void setRightSpriteId(int var0);

	void setTextColor(int var0);

	void setTransformIds(int[] var0);

	void setVarpbitIndex(int var0);

	void setVarpIndex(int var0);
}
