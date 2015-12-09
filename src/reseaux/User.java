/**
 * Created by Ari� on 25/11/2015.
 */
package reseaux;

/**
 * Interface User d�finissant les prototypes des fonctions de AbstractUser (pour Client et Server)
 */
public interface User {
    /**
     * Permet d'envoyer des donn�es aux utilisateurs connect�
     *
     * @param type {DataType} type de donner � envoyer
     * @param obj  {Object}
     */
    void sendObject(DataType type, Object obj);

    /**
     * Permet d'initialiser les variables de base de l'application en multijoueur
     *
     * @param nickname {String} Pseudo de l'utilisateur
     * @param mot      {String} Mot selectionner sur lequel l'utilisateur veux jouer
     */
    void initData(String nickname, String mot);

    /**
     * @param nbWordFound {int} Nombre de mot actuellement trouv�
     * @param score       {int} Score de l'utilisateur
     */
    void updateStatus(int nbWordFound, int score);

    /**
     * @return {DataExchange}
     */
    DataExchange getDataExchange();
}
