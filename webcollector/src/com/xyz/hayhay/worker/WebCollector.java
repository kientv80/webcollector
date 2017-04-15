package com.xyz.hayhay.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.xyz.hayhay.ping.PingServer;
import com.xyz.hayhay.website.collector.BatDongSanCollector;
import com.xyz.hayhay.website.collector.CollectorManager;
import com.xyz.hayhay.website.collector.FilmCollector;
import com.xyz.hayhay.website.collector.FunyStoryCollector;
import com.xyz.hayhay.website.collector.ICTNewsCollector;
import com.xyz.hayhay.website.collector.KhoiNghiepCollector;
import com.xyz.hayhay.website.collector.KinhDoanhCollector;
import com.xyz.hayhay.website.collector.KinhTeCollector;
import com.xyz.hayhay.website.collector.MovieCollector;
import com.xyz.hayhay.website.collector.MuaBanNhaDatCollector;
import com.xyz.hayhay.website.collector.MusicCollector;
import com.xyz.hayhay.website.collector.NauAnCollector;
import com.xyz.hayhay.website.collector.NgoiSaoCollector;
import com.xyz.hayhay.website.collector.PcworldCollector;
import com.xyz.hayhay.website.collector.SportNewsCollector;
import com.xyz.hayhay.website.collector.SucKhoeDoiSongNewsCollector;
import com.xyz.hayhay.website.collector.SucKhoeNewsCollector;
import com.xyz.hayhay.website.collector.TaiChinhNewsCollector;
import com.xyz.hayhay.website.collector.TinTheGioiCollector;
import com.xyz.hayhay.website.collector.TinTrongNuocArticleCollector;
import com.xyz.hayhay.website.collector.TinhYeuCollector;
import com.xyz.hayhay.website.collector.XeCollector;
import com.xyz.hayhay.website.collector.XoSoKienThietCollector;
import com.xyz.hayhay.website.collector.tv.HaNoiTVCollector;
import com.xyz.hayhay.website.collector.tv.VTVCabCollector;
import com.xyz.hayhay.website.collector.tv.VTVProgramCollector;

public class WebCollector {
	private static final int FIFTEEN_MINUTES= 15*60*1000;
	private static final int THIRTY_MINUTES= 2*FIFTEEN_MINUTES;
	private static final int ONE_HOUR= 2*THIRTY_MINUTES;
	private static final int FOUR_HOURS= 4*ONE_HOUR;
	private static final int EIGHT_HOURS= 8*ONE_HOUR;
	Logger log = Logger.getLogger(WebCollector.class);
	public static StringBuilder collectResult = new StringBuilder();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
	boolean run = true;
	
	public void start() {
		if(!run)
			return;
		
		//		Collect TV Schedule
		VTVProgramCollector vtv = new VTVProgramCollector();
		VTVCabCollector vtvCab = new VTVCabCollector();
		HaNoiTVCollector bv = new HaNoiTVCollector();
		scheduler.scheduleAtFixedRate(vtv, 3600, FOUR_HOURS, TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(vtvCab, 2*60, FOUR_HOURS, TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(bv, 6*60, FOUR_HOURS, TimeUnit.SECONDS);
		//Collect News
		CollectorManager cMng = CollectorManager.getInstance();
		cMng.register(new BatDongSanCollector(ONE_HOUR));
		cMng.register(new FilmCollector(FOUR_HOURS));
		cMng.register(new FunyStoryCollector(FOUR_HOURS));
		cMng.register(new ICTNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new KhoiNghiepCollector(THIRTY_MINUTES));
		cMng.register(new KinhTeCollector(FIFTEEN_MINUTES));
		cMng.register(new MovieCollector(FOUR_HOURS));
		cMng.register(new MuaBanNhaDatCollector(FIFTEEN_MINUTES));
		cMng.register(new MusicCollector(ONE_HOUR));
		cMng.register(new NauAnCollector(EIGHT_HOURS));
		cMng.register(new NgoiSaoCollector(FIFTEEN_MINUTES));
		cMng.register(new PcworldCollector(ONE_HOUR));
		cMng.register(new SportNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new SucKhoeDoiSongNewsCollector(ONE_HOUR));
		cMng.register(new SucKhoeNewsCollector(ONE_HOUR));
		cMng.register(new TaiChinhNewsCollector(FIFTEEN_MINUTES));
		cMng.register(new TinhYeuCollector(FOUR_HOURS));
		cMng.register(new TinTheGioiCollector(FIFTEEN_MINUTES));
		cMng.register(new TinTrongNuocArticleCollector(FIFTEEN_MINUTES));
		cMng.register(new XeCollector(FOUR_HOURS));
		cMng.register(new KinhDoanhCollector(ONE_HOUR));
		
		cMng.register(new XoSoKienThietCollector(ONE_HOUR));
		cMng.startCollectorManager();
		
		PingServer.startPingServer();

	}
	public static void main(String[] args) {
		new WebCollector().start();
	}
}
