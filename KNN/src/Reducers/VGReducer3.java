package Reducers;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class VGReducer3 extends Reducer<Text, BytesWritable, Text, BytesWritable> {
	private MultipleOutputs<Text,BytesWritable> multipleOutputs;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs  = new MultipleOutputs<Text, BytesWritable>(context);
	}
	
	public void reduce(Text key, Iterable<BytesWritable> values, Context context)
			throws IOException, InterruptedException {
		
		for(BytesWritable value: values)
			multipleOutputs.write(new Text(""), value, key.toString());

	}

	@Override
	public void cleanup( Context context ) throws IOException,InterruptedException {
		 multipleOutputs.close();
	}
}

