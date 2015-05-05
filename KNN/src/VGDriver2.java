
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import Mappers.VGMapper4;
import Reducers.VGReducer4;

public class VGDriver2 extends Configured{

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "1NN");
		job.setJarByClass(VGDriver.class);
		
		job.setMapperClass(VGMapper4.class);
		job.setReducerClass(VGReducer4.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path("/KNN/out3/"));
		FileOutputFormat.setOutputPath(job, new Path("/KNN/out4"));
		
		job.waitForCompletion(true);
	}

}
