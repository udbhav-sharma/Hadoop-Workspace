package Reducers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import MapperIO.FirstMapperState;
import MapperIO.FirstMapperValue;
import ReducerIO.FirstReducerValue;

import com.google.gson.Gson;

import util.BytesUtil;
import util.Pair;
import util.Point;

public class VGReducer extends Reducer<Text, BytesWritable, Text, BytesWritable> {
	
	private MultipleOutputs<Text,BytesWritable> multipleOutputs;
	
	@Override
	 protected void setup(Context context) throws IOException, InterruptedException {
	  multipleOutputs  = new MultipleOutputs<Text,BytesWritable>(context);
	 }
	
	@Override
	public void reduce(Text key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		// process values

		byte[] byteArray;
		Gson gson = new Gson();

		if(key.toString().equalsIgnoreCase("Graph")){
			for(BytesWritable value:values){
				try{
				 	FirstMapperState firstMapperState = (FirstMapperState)BytesUtil.toObject(value.getBytes());
				 	byteArray = BytesUtil.toByteArray(new FirstReducerValue( firstMapperState.G ));
				 	
					multipleOutputs.write(
							new Text( String.valueOf( firstMapperState.mapperKey )),
							new BytesWritable(byteArray),
							String.valueOf( firstMapperState.mapperKey )
						);
				}
				catch(ClassNotFoundException e){
					System.err.println(e);
					return;
				}
			}
		}
		else{
			Pair<Point,Double> minG = new Pair<Point, Double>(null, Double.MAX_VALUE);
			Point endPoint = gson.fromJson(key.toString(), Point.class);
			ArrayList<Integer> mapperKeys = new ArrayList<Integer>();

			for(BytesWritable value:values){
				try{
					FirstMapperValue ob = (FirstMapperValue)BytesUtil.toObject(value.getBytes());
					mapperKeys.add(ob.mapperKey);

					if(minG.getElement1() > ob.distance){
						minG.setElement0(ob.poi);
						minG.setElement1(ob.distance);
					}
				}
				catch(ClassNotFoundException e){
					System.err.println(e);
					return;
				}
			}

			if(mapperKeys.size()>1){
				for(int mapperKey:mapperKeys){
					byteArray = BytesUtil.toByteArray( new FirstReducerValue( endPoint, minG.getElement0(), minG.getElement1() ) );
					multipleOutputs.write(
								new Text( String.valueOf(mapperKey) ),
								new BytesWritable( byteArray ),
								String.valueOf(mapperKey)
							);
				}
			}
		}
	}
	
	@Override
	public void cleanup( Context context ) throws IOException,InterruptedException {
		 multipleOutputs.close();
	}
}
