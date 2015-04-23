import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;

public class WordCountDriver{
	public static void main(String args[]) throws Exception{
		Configuration conf=new Configuration();
		
		Job job=new Job(conf,"WordCount");
		job.setJarByClass(WordCountDriver.class);
		job.setJobName("Word Count");

		FileInputFormat.addInputPath(job,new Path("/test/in.txt"));
		FileOutputFormat.setOutputPath(job,new Path("/test/out"));
		
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0:1);
	}
}
