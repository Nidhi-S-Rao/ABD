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


public class characterCount {
	public static class testCharacterCountMapper extends Mapper<LongWritable, Text,IntWritable,IntWritable> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
					String line=value.toString();
					StringTokenizer token=new StringTokenizer(line);
					while(token.hasMoreTokens()) {
						String str=token.nextToken();
						value.set(str);
						context.write(new IntWritable(str.length()), new IntWritable(1));
					}
					
					
					}
					
		}
	
	public static class testCharacterCountReducer extends Reducer <IntWritable,IntWritable,IntWritable,IntWritable > {
		public void reduce(IntWritable key,Iterable <IntWritable> value,Context context) 
			throws IOException, InterruptedException {
			int sum=0;
		for(IntWritable x:value) {
			sum++;
		}
				context.write(key, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf, "character count");

		job.setJarByClass(characterCount.class);
		job.setMapperClass(testCharacterCountMapper.class);
		job.setReducerClass(testCharacterCountReducer.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0: 1);
	}
}
