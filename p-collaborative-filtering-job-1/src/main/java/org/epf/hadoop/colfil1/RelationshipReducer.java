package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Utilisation d'un HashSet pour éviter les doublons
        HashSet<String> uniqueRelations = new HashSet<>();

        for (Text value : values) {
            uniqueRelations.add(value.toString().trim());
        }

        // Construction de la chaîne de relations uniques
        StringBuilder relationsList = new StringBuilder();
        for (String relation : uniqueRelations) {
            if (relationsList.length() > 0) {
                relationsList.append(", ");
            }
            relationsList.append(relation);
        }

        // Préparer le résultat final
        result.set(relationsList.toString());
        context.write(key, result);
    }
}
