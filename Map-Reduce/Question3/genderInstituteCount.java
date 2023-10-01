import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class genderInstituteCount {

	public static class genderInstituteCountMapper extends Mapper<LongWritable, Text,Text,IntWritable> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
					String line=value.toString();
					String[] tokens=line.split(",");
					String inst=context.getConfiguration().get("Institution");
					if(tokens[2].equals(inst)) {
						context.write(new Text(tokens[4]),new IntWritable(1));
					}
				
					}
			}
	
	
	public static class genderInstituteCountReducer extends Reducer <Text, IntWritable,Text,IntWritable > {
		public void reduce(Text key, Iterable<IntWritable> value,Context context) 
			throws IOException, InterruptedException {
				int sum=0;
				for(IntWritable x:value) {
					sum+=x.get();
				}
				context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		conf.set("Institution", args[2]);
		
		Job job = Job.getInstance(conf, "Gender Institute count");

		job.setJarByClass(genderInstituteCount.class);
		job.setMapperClass(genderInstituteCountMapper.class);
		job.setReducerClass(genderInstituteCountReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0: 1);
	}

}
