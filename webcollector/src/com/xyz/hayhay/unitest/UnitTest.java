package com.xyz.hayhay.unitest;

import com.xyz.hayhay.website.collector.ICTNewsCollector;
import com.xyz.hayhay.website.collector.TinTrongNuocArticleCollector;
import com.xyz.hayhay.website.collector.worldwebsite.HotNewsCollector;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		new ICTNewsCollector(1).test();
	}
}
