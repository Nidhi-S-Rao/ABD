
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class dataPointCount {
	public static class dataPointCountMapper extends Mapper<LongWritable, Text,Text,IntWritable> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
					String line=value.toString();
					String[] tokens=line.split(",");
						context.write(new Text(tokens[1]),new IntWritable(1));
					
				
					}
			}
	
	
	public static class dataPointCountReducer extends Reducer <Text,IntWritable,Text,IntWritable > {
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
		
		
		Job job = Job.getInstance(conf, "DataPoint");

		job.setJarByClass(dataPointCount.class);
		job.setMapperClass(dataPointCountMapper.class);
		job.setReducerClass(dataPointCountReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0: 1);
	}

}
