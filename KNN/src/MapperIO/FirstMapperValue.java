package MapperIO;

import java.io.Serializable;

import util.Point;



public class FirstMapperValue implements Serializable {
	private static final long serialVersionUID = 8676735786773507220L;
	public int mapperKey;
	public double distance;
	public Point poi;

	public FirstMapperValue(int mapperKey, Point poi, double distance) {
		this.mapperKey = mapperKey;
		this.distance = distance;
		this.poi = poi;
	}
	
}
