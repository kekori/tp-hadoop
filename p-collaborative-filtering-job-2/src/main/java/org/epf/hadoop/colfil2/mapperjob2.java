package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class mapperjob2 extends Mapper<Object, Text, UserPair, Text> {

    private final UserPair userPair = new UserPair();
    private final Text drapo = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Format attendu : user\tfriend1,friend2,...
        String[] valeur = value.toString().split("\t");
        if (valeur.length < 2) return;

        String user = valeur[0].trim();
        String[] friends = valeur[1].split(",");

        // Utiliser un Set pour éviter les doublons éventuels
        Set<String> uniqueFriends = new HashSet<>(Arrays.asList(friends));

        // Émettre les relations directes avec un flag "0"
        for (String friend : uniqueFriends) {
            if (!user.equals(friend)) {
                userPair.setUsers(user, friend);
                drapo.set("0");
                context.write(userPair, drapo);
            }
        }

        // Émettre les paires indirectes pour les amis de l'utilisateur
        String[] uniqueFriendsArray = uniqueFriends.toArray(new String[0]);
        for (int i = 0; i < uniqueFriendsArray.length; i++) {
            for (int j = i + 1; j < uniqueFriendsArray.length; j++) {
                userPair.setUsers(uniqueFriendsArray[i], uniqueFriendsArray[j]);
                drapo.set(user); // Ajouter l'utilisateur comme référence pour les relations communes
                context.write(userPair, drapo);
            }
        }
    }
}
