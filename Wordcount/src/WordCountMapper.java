
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
	public void map(LongWritable key, Text value, Context context) throws IOException , InterruptedException{
		String line=value.toString();
		String wordsList[]=line.split("\\s+");
		
		for(int j=0;j<wordsList.length;j++)
			context.write(new Text(wordsList[j]), new IntWritable(1));
	}
	
	public void run (Context context) throws IOException , InterruptedException{
		setup(context);
		while(context.nextKeyValue()){
			map(context.getCurrentKey(),context.getCurrentValue(),context);
		}
		cleanup(context);
	}
}