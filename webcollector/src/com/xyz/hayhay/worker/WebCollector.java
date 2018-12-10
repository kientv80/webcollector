package com.xyz.hayhay.worker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.xyz.hayhay.ping.PingServer;
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

public class WebCollector {
	private static final int FIFTEEN_MINUTES= 15*60*1000;
	private static final int THIRTY_MINUTES= 2*FIFTEEN_MINUTES;
	private static final int ONE_HOUR= 2*THIRTY_MINUTES;
	private static final int FOUR_HOURS= 4*ONE_HOUR;
	private static final int EIGHT_HOURS= 8*ONE_HOUR;
	Logger log = Logger.getLogger(WebCollector.class);
	public static StringBuilder collectResult = new StringBuilder();
//	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
	boolean run = true;
	
	public void start() {

		//Collect News
		CollectorManager cMng = new CollectorManager();
		cMng.register(new BatDongSanCollector(ONE_HOUR));
		cMng.register(new FunyStoryCollector(FOUR_HOURS));
		cMng.register(new ICTNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new KhoiNghiepCollector(THIRTY_MINUTES));
		cMng.register(new KinhTeCollector(FIFTEEN_MINUTES));
		cMng.register(new NauAnCollector(EIGHT_HOURS));
		cMng.register(new NgoiSaoCollector(FIFTEEN_MINUTES));
		cMng.register(new SportNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new SucKhoeDoiSongNewsCollector(ONE_HOUR));
		cMng.register(new SucKhoeNewsCollector(ONE_HOUR));
		cMng.register(new TaiChinhNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new TinhYeuCollector(FOUR_HOURS));
		cMng.register(new TinTheGioiCollector(FIFTEEN_MINUTES));
		
		cMng.register(new XeCollector(FOUR_HOURS));
		cMng.register(new KinhDoanhCollector(ONE_HOUR));
		cMng.register(new BusinessNewsCollector(ONE_HOUR));
		cMng.register(new TechNewsCollector(ONE_HOUR));
		cMng.register(new ScienceNewsCollector(ONE_HOUR));
		cMng.register(new HealthCollector(ONE_HOUR));
		cMng.register(new PoliticsCollector(ONE_HOUR));
		cMng.register(new OpinionsCollector(ONE_HOUR));
		
		cMng.register(new HotNewsCollector(ONE_HOUR));
		cMng.register(new TinTrongNuocArticleCollector(FIFTEEN_MINUTES));
		
		cMng.startCollectorManager();
		

	}
	public static void main(String[] args) {
		CollectorScheduler.startScheduler();
		//PingServer.startPingServer();
	}
	public static void mainBK(String[] args) throws IOException {
		System.out.println("Main is called");
		for(Entry<String, String> e : System.getenv().entrySet()){
			System.out.println("key=" + e.getKey() + " val=" + e.getValue());
		}
		if(System.getenv().get("collect")!= null){
			new WebCollector().start();
		}else{
			System.out.println("Call process");
			
			Timer wnewsTimer = new Timer();
			wnewsTimer.schedule(new TimerTask() {
				boolean running = false;
				@Override
				public void run() {
					try {
						if(running == true)
							return;
						running =true;
						System.out.println("Start new process");
						Process p = Runtime.getRuntime().exec("sh /kientv/webcollector/startserver.sh", new String[]{"collect=true"});
						InputStream in = p.getInputStream();
						byte[] buff = new byte[1024];
						while(in.read(buff) > 0){
							System.out.println(new String(buff));
						}
						in.close();
						p.waitFor();
						System.out.println("Finished process with returned code = " + p.exitValue());
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						running = false;
						CollectorManager.lastTimeCollected = System.currentTimeMillis();
					}
				}
			}, 0, CollectorManager.COLLECTING_PERIOD);// run every 5 minutes
			
			PingServer.startPingServer();
		}
		
	}
}
