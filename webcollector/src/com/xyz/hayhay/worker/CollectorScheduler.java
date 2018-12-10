package com.xyz.hayhay.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.xyz.hayhay.website.collector.BatDongSanCollector;
import com.xyz.hayhay.website.collector.CollectorManager;
import com.xyz.hayhay.website.collector.FunyStoryCollector;
import com.xyz.hayhay.website.collector.ICTNewsCollector;
import com.xyz.hayhay.website.collector.KhoiNghiepCollector;
import com.xyz.hayhay.website.collector.KinhDoanhCollector;
import com.xyz.hayhay.website.collector.KinhTeCollector;
import com.xyz.hayhay.website.collector.NauAnCollector;
import com.xyz.hayhay.website.collector.NgoiSaoCollector;
import com.xyz.hayhay.website.collector.SportNewsCollector;
import com.xyz.hayhay.website.collector.SucKhoeDoiSongNewsCollector;
import com.xyz.hayhay.website.collector.SucKhoeNewsCollector;
import com.xyz.hayhay.website.collector.TaiChinhNewsCollector;
import com.xyz.hayhay.website.collector.TinTheGioiCollector;
import com.xyz.hayhay.website.collector.TinTrongNuocArticleCollector;
import com.xyz.hayhay.website.collector.TinhYeuCollector;
import com.xyz.hayhay.website.collector.XeCollector;
import com.xyz.hayhay.website.collector.worldwebsite.BusinessNewsCollector;
import com.xyz.hayhay.website.collector.worldwebsite.HealthCollector;
import com.xyz.hayhay.website.collector.worldwebsite.HotNewsCollector;
import com.xyz.hayhay.website.collector.worldwebsite.OpinionsCollector;
import com.xyz.hayhay.website.collector.worldwebsite.PoliticsCollector;
import com.xyz.hayhay.website.collector.worldwebsite.ScienceNewsCollector;
import com.xyz.hayhay.website.collector.worldwebsite.TechNewsCollector;

public class CollectorScheduler {
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void startScheduler() {
		scheduler.scheduleWithFixedDelay(new WebCollectorExecutor(), 0, 5, TimeUnit.MINUTES);
	}
}

class WebCollectorExecutor implements Runnable {
	boolean isRunning = false;
	@Override
	public void run() {
		try {
			if(isRunning)
				return;
			isRunning = true;
			
			System.out.println("======================== Scheduler start CollectorManager");
			CollectorManager cMng = new CollectorManager();
			cMng.register(new BatDongSanCollector(1));
			cMng.register(new FunyStoryCollector(1));
			cMng.register(new ICTNewsCollector(1));
			cMng.register(new KhoiNghiepCollector(1));
			cMng.register(new KinhTeCollector(1));
			cMng.register(new NauAnCollector(1));
			cMng.register(new NgoiSaoCollector(1));
			cMng.register(new SportNewsCollector(1));
			cMng.register(new SucKhoeDoiSongNewsCollector(1));
			cMng.register(new SucKhoeNewsCollector(1));
			cMng.register(new TaiChinhNewsCollector(1));
			cMng.register(new TinhYeuCollector(1));
			cMng.register(new TinTheGioiCollector(1));

			cMng.register(new XeCollector(1));
			cMng.register(new KinhDoanhCollector(1));
			cMng.register(new BusinessNewsCollector(1));
			cMng.register(new TechNewsCollector(1));
			cMng.register(new ScienceNewsCollector(1));
			cMng.register(new HealthCollector(1));
			cMng.register(new PoliticsCollector(1));
			cMng.register(new OpinionsCollector(1));

			cMng.register(new HotNewsCollector(1));
			cMng.register(new TinTrongNuocArticleCollector(1));

			cMng.start();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			isRunning = false;
			System.out.println("======================== Scheduler end CollectorManager");
		}

	}

}
