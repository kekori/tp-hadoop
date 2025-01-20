package org.epf.hadoop.colfil1;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;

public class ColFilJob1 {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Job Relations");

        job.setJarByClass(ColFilJob1.class);

        //Mapper
        job.setMapperClass(RelationshipMapper.class);
        //Reducer
        job.setReducerClass(RelationshipReducer.class);
        //Clé sortie
        job.setOutputKeyClass(Text.class);
        //Valeur sortie
        job.setOutputValueClass(Text.class);
        //Format d'entrée
        job.setInputFormatClass(RelationshipInputFormat.class);
        //Entrée
        FileInputFormat.addInputPath(job, new Path(args[0]));
        //Sortie
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}