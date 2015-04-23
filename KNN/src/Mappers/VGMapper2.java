package Mappers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ReducerIO.FirstReducerValue;
import VoronoiDiagram.Graph;
import VoronoiDiagram.NetworkVoronoiDiagram;
import VoronoiDiagram.ParallelDijkstra;
import VoronoiDiagram.Vertex;

import util.BytesUtil;
import util.Point;

public class VGMapper2 extends Mapper<LongWritable, Text, Text, BytesWritable> { 
	
	@Override
	public void run(Mapper<LongWritable, Text, Text, BytesWritable>.Context context)
			throws IOException, InterruptedException {
		setup(context);
		
		Graph G = null;
		ArrayList<SourcePoint> sourcePointList = new ArrayList<SourcePoint>();
		
		while(context.nextKeyValue()){
			
			//Parsing input 
			Text input = context.getCurrentValue();
			StringTokenizer token = new StringTokenizer(input.toString());
			ArrayList<Byte> byteList = new ArrayList<Byte>();

			if(token.hasMoreTokens()){
				token.nextToken();

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
						FirstReducerValue firstReducerValue = (FirstReducerValue)BytesUtil.toObject(byteArray);
						if(firstReducerValue.objType == FirstReducerValue.GRAPH_TYPE){
							G = firstReducerValue.G;
						}
						else if(firstReducerValue.objType == FirstReducerValue.SOURCE_POINT_TYPE){
							sourcePointList.add(new SourcePoint(firstReducerValue.sp, firstReducerValue.poi, firstReducerValue.distance));
						}
					}
					catch(ClassNotFoundException e){
						System.err.println(e);
						return;
					}
			}
		}
		
		if(G!=null){
			
			//Initialize Source Vertices
			for( SourcePoint sourcePoint : sourcePointList ){
				Vertex v = G.findV(sourcePoint.p);
				if(v.dist > sourcePoint.distance){
					v.dist = sourcePoint.distance;
					Vertex sourcePointPOI = null;
					
					sourcePointPOI = G.findV(sourcePoint.poi);
					if(sourcePointPOI == null)
						sourcePointPOI = G.addV(sourcePoint.poi, true);
					G.addE(sourcePointPOI, v, sourcePoint.distance);					
				}
				else{
					//For KNN
				}
			}

			//Run Parallel Dijkstra
			ParallelDijkstra parallelDijkstra = new ParallelDijkstra();
			parallelDijkstra.init(G);
			parallelDijkstra.generateVoronoi();
			NetworkVoronoiDiagram nvd = parallelDijkstra.getNVD();
			
			byte[] byteArray = BytesUtil.toByteArray(nvd);
			
			context.write(
							new Text("New NVD"),
							new BytesWritable(byteArray)
					);
		}
			
	}
	
	public void map(LongWritable key, Text input, Context context)
		throws IOException, InterruptedException {}

}

class SourcePoint{
	public Point p;
	public Point poi;
	public double distance;
	
	public SourcePoint(Point p, Point poi, double distance) {
		this.p = p;
		this.poi = poi;
		this.distance = distance;
	}
	
	public String toString(){
		String output = "-----Source Points-----\n";
		output += "P: "+this.p+"\n";
		output += "POI: "+this.poi+"\n";
		output += "Distance: "+this.distance;
		
		return output;
	}
}