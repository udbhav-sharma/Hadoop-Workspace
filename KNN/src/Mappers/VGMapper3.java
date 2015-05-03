package Mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import util.BytesUtil;
import util.Pair;
import util.Point;

import VoronoiDiagram.NetworkVoronoiDiagram;
import VoronoiDiagram.NetworkVoronoiDiagram.Edge;
import VoronoiDiagram.NetworkVoronoiDiagram.NetworkVoronoiPolygon;

public class VGMapper3 extends Mapper<LongWritable, Text, Text, Text>{
	
	@Override
	public void run(Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		setup(context);
		
		NetworkVoronoiDiagram nvd = new NetworkVoronoiDiagram();
		
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
					NetworkVoronoiPolygon newNVP = (NetworkVoronoiPolygon)BytesUtil.toObject(byteArray);
					
					if(nvd.nvps.containsKey(newNVP.p)){
						NetworkVoronoiPolygon nvp = nvd.nvps.get(newNVP.p);
						
						for(Edge e: newNVP.graph)
							nvp.graph.add(e);
						
						for(Pair<Point, Double> pair: newNVP.borderPoints)
							nvp.borderPoints.add(pair);
						
					}
					else
						nvd.nvps.put(newNVP.p, newNVP);
				}
				catch(ClassNotFoundException e){
					System.err.println(e);
					return;
				}
			}
		}
		
		Random rand = new Random();
		int mapperId = rand.nextInt(10000);
		
		if(nvd.nvps.size()>0)
			context.write(new Text(String.valueOf(mapperId)), new Text(nvd.toString()));
	}
	
	public void map(LongWritable key, Text input, Context context)
			throws IOException, InterruptedException {}
}
