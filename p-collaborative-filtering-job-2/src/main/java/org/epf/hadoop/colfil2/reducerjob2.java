package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class reducerjob2 extends Reducer<UserPair, Text, UserPair, Text> {

    private final Text result = new Text();

    @Override
    protected void reduce(UserPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        boolean isDirectConnection = false;
        int commonRelationsCount = 0;

        for (Text value : values) {
            // Vérifie si une relation directe existe
            if (value.toString().equals("0")) {
                isDirectConnection = true;
            } else {
                // Compte les relations en commun
                commonRelationsCount++;
            }
        }

        // Émet la paire uniquement si elle n'est pas une relation directe
        if (!isDirectConnection && commonRelationsCount > 0) {
            result.set(String.valueOf(commonRelationsCount));
            context.write(key, result);
        }
    }
}
