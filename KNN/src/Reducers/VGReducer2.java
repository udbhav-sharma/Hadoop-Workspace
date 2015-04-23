package Reducers;
import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.BytesUtil;

import VoronoiDiagram.NetworkVoronoiDiagram;

public class VGReducer2 extends Reducer<Text, BytesWritable, Text, Text> {
	public void reduce(Text key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		
		for(BytesWritable value: values){
			try{
				NetworkVoronoiDiagram nvd = (NetworkVoronoiDiagram)BytesUtil.toObject(value.getBytes());
				context.write(new Text("NVD"), new Text( nvd.toString() ));
			}
			catch(ClassNotFoundException e){
				System.err.println(e);
				return;
			}
		}
	}

}
