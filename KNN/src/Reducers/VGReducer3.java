package Reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class VGReducer3 extends Reducer<Text, Text, Text, Text> {
	private MultipleOutputs<Text,Text> multipleOutputs;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException {
		multipleOutputs  = new MultipleOutputs<Text, Text>(context);
	}
	
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		for(Text value: values)
			multipleOutputs.write(new Text(""), value, key.toString());

	}

	@Override
	public void cleanup( Context context ) throws IOException,InterruptedException {
		 multipleOutputs.close();
	}
}

