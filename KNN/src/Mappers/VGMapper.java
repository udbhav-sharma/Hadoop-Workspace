package Mappers;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.gson.Gson;

import MapperIO.FirstMapperState;
import MapperIO.FirstMapperValue;
import VoronoiDiagram.Graph;
import VoronoiDiagram.ParallelDijkstra;
import VoronoiDiagram.Vertex;

import util.BytesUtil;
import util.Point;

public class VGMapper extends Mapper<LongWritable, Text, Text, BytesWritable> {

	@Override
	public void run(Mapper<LongWritable, Text, Text, BytesWritable>.Context context)
			throws IOException, InterruptedException {
		setup(context);
		
		Random rand = new Random();
		int mapperId = rand.nextInt(10000);
		
		Graph G = new Graph();
		double i,j,x,y,w;
		
		while(context.nextKeyValue()){
			String line=context.getCurrentValue().toString();
			String input[]=line.split("\\s+");
			if(input.length==5){
				i = Double.parseDouble(input[0]);
				j = Double.parseDouble(input[1]);
				x = Double.parseDouble(input[2]);
				y = Double.parseDouble(input[3]);

				Vertex u = G.findV(new Point(i,j));
				Vertex v = G.findV(new Point(x,y));

				if( u == null )
					u = G.addV(new Point(i,j),false);

				if( v == null )
					v = G.addV(new Point(x,y),false);

				w = Double.parseDouble(input[4]);
				G.addE(u,v,w);
			}
			else if(input.length == 2){
				i = Double.parseDouble(input[0]);
				j = Double.parseDouble(input[1]);
				G.addV(new Point(i,j), true);
			}
		}
		
		ParallelDijkstra parallelDijkstra = new ParallelDijkstra();
		parallelDijkstra.init(G);
		parallelDijkstra.generateVoronoi();
		
		byte[] byteArray;
		Gson gson = new Gson();

		for(Vertex v: G.getV()){
			byteArray = BytesUtil.toByteArray( new FirstMapperValue(mapperId, v.pi, v.dist) );
			map(
					new Text(gson.toJson(v.p)),
					new BytesWritable(byteArray),
					context
				);
		}
		
		byteArray = BytesUtil.toByteArray(new FirstMapperState( mapperId, G ));
		map(
				new Text("Graph"),
				new BytesWritable(byteArray),
				context
			);
		
		cleanup(context);
	}

	public void map(Text key, BytesWritable input, Context context)
			throws IOException, InterruptedException {
		context.write(key,input);
	}

}
