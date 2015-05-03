
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import Mappers.VGMapper;
import Mappers.VGMapper2;
import Mappers.VGMapper3;
import Reducers.VGReducer;
import Reducers.VGReducer2;
import Reducers.VGReducer3;

public class VGDriver extends Configured{

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "VoronoiGraphs");
		job.setJarByClass(VGDriver.class);
		
		job.setMapperClass(VGMapper.class);
		job.setReducerClass(VGReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);
		
		//job.setNumReduceTasks(1);
		
		FileInputFormat.setInputPaths(job, new Path("/KNN/in/"));
		FileOutputFormat.setOutputPath(job, new Path("/KNN/out"));
		
		job.waitForCompletion(true);
		
		Configuration conf2 = new Configuration();
		Job job2 = Job.getInstance(conf2, "VoronoiGraphs2");
		job2.setJarByClass(VGDriver.class);

		job2.setMapperClass(VGMapper2.class);
		job2.setReducerClass(VGReducer2.class);
		
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(BytesWritable.class);

		FileInputFormat.setInputPaths(job2, new Path("/KNN/out/"));
		FileOutputFormat.setOutputPath(job2, new Path("/KNN/out2"));

		job2.waitForCompletion(true);
		
		Configuration conf3 = new Configuration();
		Job job3 = Job.getInstance(conf3, "VoronoiGraphs3");
		job3.setJarByClass(VGDriver.class);

		job3.setMapperClass(VGMapper3.class);
		job3.setReducerClass(VGReducer3.class);
		
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job3, new Path("/KNN/out2/"));
		FileOutputFormat.setOutputPath(job3, new Path("/KNN/out3"));

		job3.waitForCompletion(true);
	}

}
