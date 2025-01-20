package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {
    private Text sortieKey = new Text();
    private Text sortieValue = new Text();

    @Override
    protected void map(LongWritable key, Relationship value, Context context) throws IOException, InterruptedException {

        String id1 = value.getId1();
        String id2 = value.getId2();

        sortieKey.set(id1);
        sortieValue.set(id2);
        context.write(sortieKey, sortieValue);

        sortieKey.set(id2);
        sortieValue.set(id1);
        context.write(sortieKey, sortieValue);
    }
}
