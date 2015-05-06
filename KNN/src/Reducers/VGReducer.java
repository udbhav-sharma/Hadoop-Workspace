package Reducers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import MapperIO.FirstMapperKey;
import MapperIO.FirstMapperState;
import MapperIO.FirstMapperValue;
import ReducerIO.FirstReducerValue;

import util.BytesUtil;
import util.Point;

public class VGReducer extends Reducer<BytesWritable, BytesWritable, Text, BytesWritable> {
	
	private MultipleOutputs<Text,BytesWritable> multipleOutputs;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs  = new MultipleOutputs<Text,BytesWritable>(context);
	}
	
	@Override
	public void reduce(BytesWritable key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		// process values

		byte[] byteArray;
		FirstMapperKey firstMapperKey;
		try{
			firstMapperKey = (FirstMapperKey)BytesUtil.toObject(key.getBytes()); 

			if(firstMapperKey.objType == FirstMapperKey.GRAPH){
				for(BytesWritable value:values){
					try{
						FirstMapperState firstMapperState = (FirstMapperState)BytesUtil.toObject(value.getBytes());
						byteArray = BytesUtil.toByteArray(new FirstReducerValue( firstMapperState.mapperId, firstMapperState.G ));

						multipleOutputs.write(
								new Text( String.valueOf( firstMapperState.mapperId )),
								new BytesWritable(byteArray),
								String.valueOf( firstMapperState.mapperId )
								);
					}
					catch(ClassNotFoundException e){
						System.err.println(e);
						return;
					}
				}
			}
			else{
				double minDist = Double.MAX_VALUE;
				Point POI = null;
				int poiMapperId = -1;

				Point endPoint = firstMapperKey.p;
				ArrayList<Integer> mapperIds = new ArrayList<Integer>();

				for(BytesWritable value:values){
					try{
						FirstMapperValue ob = (FirstMapperValue)BytesUtil.toObject(value.getBytes());
						mapperIds.add(ob.mapperId);

						if(minDist > ob.distance){
							minDist = ob.distance;
							POI = ob.poi;
							poiMapperId = ob.mapperId;
						}
					}
					catch(ClassNotFoundException e){
						System.err.println(e);
						return;
					}
				}

				if(mapperIds.size()>1){
					for(int mapperId:mapperIds){
						byteArray = BytesUtil.toByteArray( new FirstReducerValue( poiMapperId, endPoint, POI, minDist ) );
						multipleOutputs.write(
								new Text( String.valueOf(mapperId) ),
								new BytesWritable( byteArray ),
								String.valueOf(mapperId)
								);
					}
				}
			}
		}
		catch(ClassNotFoundException e){
			System.err.println(e);
			return;
		}
	}
	
	@Override
	public void cleanup( Context context ) throws IOException,InterruptedException {
		 multipleOutputs.close();
	}
}
