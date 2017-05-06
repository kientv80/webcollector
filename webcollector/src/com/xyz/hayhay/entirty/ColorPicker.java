package com.xyz.hayhay.entirty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorPicker {
	int index = -1;
	List<String> categoryColors = new ArrayList<>();
	Map<String,String> cateColorMapping = new  HashMap<>();
	private static ColorPicker instance;
	public static final String RED = "#cc0000";
	public static final String Orange = "#ff9900";
	public static final String GREY = "#3e5062";
	public static final String GREEN = "#8bd623";
	public static final String BLUE = "#54d0e2";
	public static final String LIGHT_BLUE = "#2ea3f2";
	
	public static final String DACK_BLUE = "#059";
	public static final String PURPLE = "#8d6fcf";
	public static final String YELLOW = "#ffff00";
	public static final String PING = "#dc51a0";
	public static final String BROW = "#663300";
	public static final String BLACK = "#000000";
	
	public static final ColorPicker getInstance(){
		if(instance == null)
			instance = new ColorPicker();
		return instance;
	}
	public ColorPicker() {

		cateColorMapping.put(NewsTypes.TYPE_TINTRONGNUOC, LIGHT_BLUE);
		cateColorMapping.put(NewsTypes.TYPE_TINQUOCTE, DACK_BLUE);
		cateColorMapping.put("tintuc", LIGHT_BLUE);
		
		cateColorMapping.put(NewsTypes.NGOISAO, BLUE);
		cateColorMapping.put(NewsTypes.SHOWBIZVIET, BLUE);
		cateColorMapping.put(NewsTypes.PHONGCACH, BLUE);
		cateColorMapping.put(NewsTypes.HOLLYWOOD, BLUE);
		cateColorMapping.put(NewsTypes.CHAUA, BLUE);
		cateColorMapping.put(NewsTypes.BENLE, BLUE);
		cateColorMapping.put("ngoisao", BLUE);
		
		cateColorMapping.put(NewsTypes.TYPE_SPORTNEWS, BLACK);
		cateColorMapping.put(NewsTypes.BONGDA, BLACK);
		cateColorMapping.put(NewsTypes.TENNIS, BLACK);
		cateColorMapping.put(NewsTypes.GOLF, BLACK);
		cateColorMapping.put(NewsTypes.HAUTRUONG, BLACK);
		cateColorMapping.put(NewsTypes.THETHEO_VIDEO, BLACK);
		cateColorMapping.put("thethao", GREY);
		
		cateColorMapping.put(NewsTypes.TYPE_ECONOMY, RED);
		cateColorMapping.put(NewsTypes.TYPE_KINHTE, RED);
		cateColorMapping.put(NewsTypes.TAICHINH, RED);
		cateColorMapping.put(NewsTypes.CHUNGKHOAN, RED);
		cateColorMapping.put(NewsTypes.TYGIA, RED);
		cateColorMapping.put(NewsTypes.NGANHANG, RED);
		cateColorMapping.put(NewsTypes.NGANSACH, RED);
		cateColorMapping.put(NewsTypes.THITRUONGVANG, RED);
		cateColorMapping.put(NewsTypes.DIA_OC, RED);
		cateColorMapping.put("kinhte", RED);
		
		cateColorMapping.put(NewsTypes.TYPE_CONGNGHE, Orange);
		cateColorMapping.put(NewsTypes.CNTT, Orange);
		cateColorMapping.put(NewsTypes.VIENTHONG, Orange);
		cateColorMapping.put(NewsTypes.INTERNET, Orange);
		cateColorMapping.put(NewsTypes.TGS, Orange);
		cateColorMapping.put(NewsTypes.KHOINGHIEP, Orange);
		cateColorMapping.put("congnghe", Orange);
		
		cateColorMapping.put(NewsTypes.TINHYEU, PING);
		cateColorMapping.put(NewsTypes.GOCTAMHON, PING);
		cateColorMapping.put(NewsTypes.GIADINH, PING);
		cateColorMapping.put(NewsTypes.GIOITINH, PING);
		cateColorMapping.put(NewsTypes.HUONGTHIEN, PING);
		cateColorMapping.put("tinhyeu", PING);
		
		cateColorMapping.put(NewsTypes.TYPE_SUCKHOE, GREEN);
		cateColorMapping.put(NewsTypes.AMTHUC, GREEN);
		cateColorMapping.put(NewsTypes.DINHDUONG, GREEN);
		cateColorMapping.put(NewsTypes.THUOCQUANHTA, GREEN);
		cateColorMapping.put(NewsTypes.BAITHUOC, GREEN);
		cateColorMapping.put(NewsTypes.YHOCCOTRUYEN, GREEN);
		cateColorMapping.put(NewsTypes.BAITHUOCHAY, GREEN);
		cateColorMapping.put("suckhoe", GREEN);
		
		cateColorMapping.put(NewsTypes.NAUAN, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_HEO, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_GA, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_BO, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_CA, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_MUC, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_TOM, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_HAISAN, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_NAM, GREEN);
		cateColorMapping.put(NewsTypes.NAUAN_DAUHU, GREEN);
	}

	public String getColor(String cateName){
		String color = cateColorMapping.get(cateName);
		if(color == null)
			color = GREY;
		return color;
	}
}
