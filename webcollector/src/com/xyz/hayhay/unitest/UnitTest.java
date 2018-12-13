package com.xyz.hayhay.unitest;

import com.xyz.hayhay.website.collector.*;
import com.xyz.hayhay.website.collector.worldwebsite.*;

public class UnitTest {
	public static void main(String[] args) throws Exception {
		new ScienceNewsCollector(1).test();
		System.out.println("============================================");
	}
}
