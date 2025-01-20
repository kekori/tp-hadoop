package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

public class RelationshipRecordReader extends RecordReader<LongWritable, Relationship> {
    //On utilise LineRecordReader, plus adapté à Hadoop
    private LineRecordReader lineRecordReader = new LineRecordReader();
    private LongWritable currentKey = new LongWritable();
    private Relationship currentValue = new Relationship();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        lineRecordReader.initialize(split, context);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // Lecture de la ligne suivante
        boolean hasNext = lineRecordReader.nextKeyValue();
        if (hasNext) {
            // Mise à jour de la clé actuelle avec le numéro de ligne
            currentKey.set(lineRecordReader.getCurrentKey().get());
            // Récupération et nettoyage de la ligne lue
            String line = lineRecordReader.getCurrentValue().toString().trim();

            if (!line.isEmpty()) {
                // Séparation de la ligne pour extraire les deux éléments de la relation
                String[] parts = line.split(";")[0].split("<->");
                if (parts.length == 2) {
                     // Mise à jour des champs de l'objet Relationship
                    currentValue.setId1(parts[0].trim());
                    currentValue.setId2(parts[1].trim());
                } else {
                    // Gestion des lignes malformées
                    System.err.println("Malformed line: " + line);
                }
            }
        }
        return hasNext;
    }

    @Override
    public LongWritable getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    @Override
    public Relationship getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }
}
