package utils;

import java.io.Serializable;

/**
 * Created by Arié on 12/10/2015.
 */
public class TweetWord implements Serializable {
    private static final long serialVersionUID = 1350092881346723536L;

    private String _word;
    private int    _ponderation;

    /**
     *
     * @param word {String} Mot selectionné
     * @param ponderation {int} : pondération du mot
     */
    public TweetWord(String word, int ponderation) {
        if (ponderation < -3 || ponderation > 100) // cas particulier de type des TweetWord
            if (ponderation == -1 || ponderation == -2) {
                this._word = null;
                this._ponderation = ponderation;
            } else { // cas illegal de pondération , envoi d'une erreur java
                throw new IllegalArgumentException("input error - illegal ponderation value");
            }
        else {  // constructeur par default
            this._word = word;
            this._ponderation = ponderation;
        }
    }

    public String getWord() {
        return this._word;
    }

    public int getPonderation() {
        return this._ponderation;
    }

    @Override
    public String toString() {
        return "TweetWord{word=" + _word + ", ponderation=" + _ponderation + "}\n";
    }

    public void setPonderation(int ponderation) {
        this._ponderation = ponderation;
    }
}
