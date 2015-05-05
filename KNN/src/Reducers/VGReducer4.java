package Reducers;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import util.BytesUtil;
import util.Pair;
import util.Point;

public class VGReducer4 extends Reducer<Text, BytesWritable, Text, Text> {
	
	@SuppressWarnings("unchecked")
	@Override
	public void reduce(Text key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		Pair<Point, Double> min = new Pair<Point, Double>(null, Double.MAX_VALUE);
		Pair<Point, Double> valuePair = null;
		
		for(BytesWritable value:values){
			try{
				valuePair = (Pair<Point, Double>)BytesUtil.toObject(value.getBytes());
				if(valuePair.getElement1() < min.getElement1())
					min = valuePair;
			}
			catch(ClassNotFoundException e){
				System.err.println(e);
				return;
			}
		}
		context.write(key, new Text(min.toString()));
	}
}
