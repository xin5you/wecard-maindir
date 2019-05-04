package com.cn.thinkx.pms.base.utils;

public class GeoConstants {

	

	public enum GeoConstantsEnum{
		widthDistance1(1,5009400,1252300), 
		widthDistance2(2,1252300,156500),
		widthDistance3(3,156500,39100),
		widthDistance4(4,39100,4900),
		widthDistance5(5,4900,1200),
		widthDistance6(6,1200,152),
		widthDistance7(7,152,0);
		
		private int geoLength;
		
		private int maxWidthDistance;
		
		private int minWidthDistance;
		
		GeoConstantsEnum(int geoLength,int maxWidthDistance,int minWidthDistance){
			this.geoLength=geoLength;
			this.maxWidthDistance=maxWidthDistance;
			this.minWidthDistance=minWidthDistance;
		}
		
		public int getGeoLength() {
			return geoLength;
		}

		public int getMaxWidthDistance() {
			return maxWidthDistance;
		}

		public int getMinWidthDistance() {
			return minWidthDistance;
		}

		public static GeoConstantsEnum findEnumByDistance(int distance) {
			for (GeoConstantsEnum t : GeoConstantsEnum.values()) {
				if (distance<t.getMaxWidthDistance() && distance>t.getMinWidthDistance()) {
					return t;
				}
			}
			return widthDistance5;
		}
	}
}
