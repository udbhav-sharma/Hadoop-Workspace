package Mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.BytesUtil;
import util.Pair;
import util.Point;

import VQuadTree.Rectangle;
import VQuadTree.Tree;
import VoronoiDiagram.NetworkVoronoiDiagram;
import VoronoiDiagram.NetworkVoronoiDiagram.Edge;
import VoronoiDiagram.NetworkVoronoiDiagram.NetworkVoronoiPolygon;

public class VGMapper4 extends Mapper<LongWritable, Text, Text, BytesWritable>{
	
	private static HashMap<Point, ArrayList<Point>> points = new HashMap<Point, ArrayList<Point>>();
	
	@Override
	public void run(Mapper<LongWritable, Text, Text, BytesWritable>.Context context)
			throws IOException, InterruptedException {
		setup(context);
		
		NetworkVoronoiDiagram nvd = null;
		
		while(context.nextKeyValue()){
			
			//Parsing input 
			Text input = context.getCurrentValue();
			StringTokenizer token = new StringTokenizer(input.toString());
			ArrayList<Byte> byteList = new ArrayList<Byte>();

			if(token.hasMoreTokens()){				
				while(token.hasMoreTokens()){
					String byteString = token.nextToken();
					byte b = (byte) ((Character.digit(byteString .charAt(0), 16) << 4) 
							+ Character.digit(byteString .charAt(1), 16));
					byteList.add(b);
				}

				byte[] byteArray = new byte[byteList.size()];
				int i=0;
				for(byte b: byteList)
					byteArray[i++] = b;

				try{
					nvd = (NetworkVoronoiDiagram)BytesUtil.toObject(byteArray);
				}
				catch(ClassNotFoundException e){
					System.err.println(e);
					return;
				}
			}
		}
		
		if(nvd != null){
			//VQuad Tree construction
			double minX=Double.MAX_VALUE,minY=Double.MAX_VALUE,maxX=0,maxY=0;

			//Test cases for 1NN
			ArrayList<Point> testPoints = new ArrayList<Point>();
			testPoints.add(new Point(1,2));
			testPoints.add(new Point(2,3));
			testPoints.add(new Point(3,4));
			testPoints.add(new Point(4,5));
			testPoints.add(new Point(5,6));
			testPoints.add(new Point(6,7));
			testPoints.add(new Point(2,2));
			testPoints.add(new Point(3,3));

			Iterator<Map.Entry<Point,NetworkVoronoiPolygon>> it = nvd.nvps.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Point,NetworkVoronoiPolygon> pair = it.next();
				Point poi = pair.getKey();
				NetworkVoronoiPolygon nvp = pair.getValue();
				
				for(Edge e:nvp.graph){ 
					if(points.containsKey(e.p1)){
						ArrayList<Point> pois = points.get(e.p1);
						pois.add(poi);
					}
					else{
						ArrayList<Point> pois = new ArrayList<Point>();
						pois.add(poi);
						points.put(e.p1,pois);
					}
					
					if(points.containsKey(e.p2)){
						ArrayList<Point> pois = points.get(e.p2);
						pois.add(poi);
					}
					else{
						ArrayList<Point> pois = new ArrayList<Point>();
						pois.add(poi);
						points.put(e.p2,pois);
					}
					
					minX=Math.min(minX, e.p1.getX());
					minY=Math.min(minY, e.p1.getY());
					maxX=Math.max(maxX, e.p1.getX());
					maxY=Math.max(maxY, e.p1.getY());
					
					minX=Math.min(minX, e.p2.getX());
					minY=Math.min(minY, e.p2.getY());
					maxX=Math.max(maxX, e.p2.getX());
					maxY=Math.max(maxY, e.p2.getY());
				}
			}


			Tree tree = new Tree();
			Rectangle rect = new Rectangle();
			rect.setRect(minX,maxY,maxX-minX,maxY-minY);
			tree.init(rect, points);

			for(Point p:testPoints){
				Pair<Point,Double> g = tree.getGenerator(p);
				if(g!=null)
					map(new Text(p.toString()),new BytesWritable(BytesUtil.toByteArray(g)),context);
			}
		}
	}
	
	public void map(Text key, BytesWritable input, Context context)
			throws IOException, InterruptedException {
		context.write(key,input);
	}
}
