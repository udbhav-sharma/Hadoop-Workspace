package Reducers;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import util.BytesUtil;

import VoronoiDiagram.NetworkVoronoiDiagram.NetworkVoronoiPolygon;

public class VGReducer2 extends Reducer<Text, BytesWritable, Text, BytesWritable> {
	private MultipleOutputs<Text,BytesWritable> multipleOutputs;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs  = new MultipleOutputs<Text, BytesWritable>(context);
	}
	
	public void reduce(Text key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		
		for(BytesWritable value: values){
			try{
				NetworkVoronoiPolygon nvp = (NetworkVoronoiPolygon)BytesUtil.toObject(value.getBytes());
				multipleOutputs.write(new Text(""), new BytesWritable(BytesUtil.toByteArray( nvp )), key.toString());
			}
			catch(ClassNotFoundException e){
				System.err.println(e);
				return;
			}
		}
	}

	@Override
	public void cleanup( Context context ) throws IOException,InterruptedException {
		 multipleOutputs.close();
	}
}
