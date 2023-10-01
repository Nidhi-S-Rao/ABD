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



public class cityTemperature {
	public static class cityTemperatureMapper extends Mapper<LongWritable, Text,Text,FloatWritable> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
					String line=value.toString();
					String[] tokens=line.split(",");
					String city_name=context.getConfiguration().get("City");
					if(tokens[1].equals(city_name)) {
						context.write(new Text(tokens[1]),new FloatWritable(Float.parseFloat(tokens[5])));
					}
				
					
				
					}
			}
	
	
	public static class cityTemperatureReducer extends Reducer <Text,FloatWritable,Text,Text > {
		public void reduce(Text key, Iterable<FloatWritable> value,Context context) 
			throws IOException, InterruptedException {
				float max=0;
				float min=99999;
				for(FloatWritable x:value) {
					if(x.get()>max) {
						max=x.get();
					}
					if(x.get()<min) {
						min=x.get();
					}
				}
				context.write(key, new Text("Min:"+min+"Max:"+max));
		}
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		conf.set("City", args[2]);
		
		Job job = Job.getInstance(conf, "City Temperature");

		job.setJarByClass(cityTemperature.class);
		job.setMapperClass(cityTemperatureMapper.class);
		job.setReducerClass(cityTemperatureReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0: 1);
	}

}
