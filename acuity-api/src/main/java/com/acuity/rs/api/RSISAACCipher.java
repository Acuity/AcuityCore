package com.acuity.rs.api;


//Generated by the injector on run.

public interface RSISAACCipher {

	int[] getMm();

	int[] getRandResult();

	int getValuesRemaining();

	void invokeGenerateMoreResults(int var0);

	int invokeNextInt(int var0);

	void setMm(int[] var0);

	void setRandResult(int[] var0);

	void setValuesRemaining(int var0);
}
