package com.bilgeadam.boost.course01.mymovies.server;

import com.bilgeadam.boost.course01.mymovies.server.common.CommonData;

public class DataProcessing implements Runnable {

	public void run() {
		CommonData.getInstance().initializingStarted();
		CommonData.getInstance().importData();
		CommonData.getInstance().initializingStopped();
	}
}
