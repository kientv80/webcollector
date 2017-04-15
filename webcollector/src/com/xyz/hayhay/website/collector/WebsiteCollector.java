package com.xyz.hayhay.website.collector;

import org.apache.log4j.Logger;

import com.xyz.hayhay.entirty.CollectorLog;

public  abstract class WebsiteCollector implements Runnable{
	Logger l = Logger.getLogger(WebsiteCollector.class);
	Boolean isRunning = false;
	public WebsiteCollector() {
	}
	public abstract void processWebsiteContent() throws Exception;
	public abstract String getCollectorName();
	public void run() {
		try {
			if(!isRunning){
				isRunning = true;
				l.info("Run " + getCollectorName());
				processWebsiteContent();
				l.info("End Running " + getCollectorName());
				isRunning = false;
			}
		} catch (Throwable  e) {
			l.error("============================================", e);
			//SocialServiceFactory.getZaloService().sendTextMessageByPhoneNum(new ZaloMessage(84908995558l, "Error in " + getCollectorName() + " error=" + e.getMessage()));
			CollectorLog clog = new CollectorLog(-1, getCollectorName(), 0, 0, "", null, "error", e.getMessage());
			e.printStackTrace();
		}
	}
}
