package com.ponroy.florian.topquiz.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Florian PONROY - OpenClassrooms on 30/11/17.
 */

public class Ranking {

    public static final String PREF_KEY_RANKING = "PREF_KEY_RANKING";

    private Context mContext;//context c'est l'endroit où je me trouve dans l'App
    //il faut que je declare un objet du type Context si je veux appeller preferences
    // dans une classe dans le modèle, une classe qui n'est pas une activité
    private SharedPreferences mPreferences;
    private static final int RANKING_ENTRIES = 5;//le numero de scores que je veux afficher

    //
    public Ranking(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences("RANKING", MODE_PRIVATE);
    }
    // verifie s´il existe une valeur associée a PREF_KEY_RANKING dans les preferences
    //La méthode != est un opérateur qui vaut 'true' si l'expression devant(à gauche) est differente de
    // l'expression derrière(à droite), sinon l'operateur != vaut false.
    public boolean isEmpty() {

        return  mPreferences.getString(PREF_KEY_RANKING, null) != null;
    }

    public List<Score> loadScores() {
        // Fetch the current ranking
        String rankingAsString = mPreferences.getString(PREF_KEY_RANKING, null);

        List<Score> ranking = null;

        if (null == rankingAsString) {
            // Create a new score list, since it doesn't exist yet
            ranking = new ArrayList<>();
        } else {
            // Deserialize the score list
            //Type sert à savoir vers quel type l'objet gSon va devoir transformer le
            //infos presents dans le gSon au format text.
            Type targetClassType = new TypeToken<ArrayList<Score>>() { }.getType();
            ranking = new Gson().fromJson(rankingAsString, targetClassType);
        }

        return ranking;
        /* ranking est une variable de type "list<score>" ,
           une method retourne un objet , (et non pas un type)
           la methode loadScores doit retourner un objet de type "list<score>"

         */

    }

    //this method wants to save the scores in the preferences
    public void saveScores(List<Score> ranking) {
        // Sort the list, higher score first, to put in the order
        //new Comparator is a tool used by the method sort to compare deux objects of the list Score
        //between them. Although a List has more than two objects, Comparator only compare two each time
        //la methode sort prends deux parametres (une liste et un comparateur) parce qu'elle va comparer plusieurs
        // fois, deux objets dans la liste Score
        //ranking est une liste de score qui peut contenir plusieurs scores
        //Comparator avec le diamant <Score>, est un objet qui contient une méthode qui va comparer deux scores
        //et uniquement deux
        //new Comparator<Score>() oblige l'implementation de la méthode compare pour comparer les deux objets
        //compare cest une methode du type int qui retourne une valeur du type int. Avec cette valeur de retour du type int,
        //la methode .sort() de Collections va modifier la liste pour la mettre dans l'ordre, mais cela ne donne pas aucun
        //retour
        Collections.sort(ranking, new Comparator<Score>() {
            @Override
            //lhs and rhs are objets of the type Score. Although we may easily confound the parentheses and
            //imagine that new Comparator<Score>()is un parameter, we will note accurately that it is missing one ")"
            //this means that all above (public int compare) is defining new Comparator<Score>()...
            public int compare(Score lhs, Score rhs) {

                int score1 = lhs.getScore();
                int score2 = rhs.getScore();
                //condition ternaire:? = if  : = else
                //if score1 is bigger , put score1 in the beginning of the list
                //else if score1<score2, put score1 in the end of the list
                //else put both at the same level, but score1 will be the first anyway
                return (score1 > score2) ? -1 : (score1 < score2) ? 1 : 0;
            }
        });

        // Keep only the five bettor scores
        if (ranking.size() > RANKING_ENTRIES) {
            //sublist c'est une fonction qui me permet de recuperer un sous ensemble dans
            //la liste. Les parametres me permettend d'indiquer l'index dont je veux
            //recuperer . C'est moi qui a chosi
            ranking = ranking.subList(0, RANKING_ENTRIES);
        }

        // Serialize (transforms into a String) the score list and save it to the preferences
        //Gson - un type de fichier formaté d'une forme que
        //les preferences ne sauvegardent pas des objets. On peut sauvegarder dans les
        //preferences des variable type String, int, float, etc (les types primitives)
        //si je veux sauvegarder des objets, il faut convertir l'objet dans un format-fichier
        //du type Gson (ou Json);
        //pour stocker la liste sous la forme d'une String dans les Preferences, on converti la liste au format Json
        //en utilisant la methode toJson de l'objet Gson qui prendre en parametre une objet de type Object(une liste
        // par exemple), et qui te donne une String, parce que les preferences ne sauvegardent pas des objets
       //serialize = convert Objects into String
        //Json means Java script object notation 
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        mPreferences.edit().putString(PREF_KEY_RANKING, gson.toJson(ranking)).apply();
    }
    //Preference ne sauvegarde pas des objets. J'utilise gson pour convertir un objet
    //en une String
    // la methode toJson transforme un objet (une liste par exemple) en une String
    //la methode fromJson transfore une String en un Objet (une liste par exemple
}
