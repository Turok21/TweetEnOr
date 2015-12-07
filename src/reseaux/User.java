package reseaux;

/**
 * Created by Arié on 25/11/2015.
 */
public interface User {

    void sendObject(DataType type, Object obj);

    void initData(String nickname, String mot);

    void updateStatus(int nbWordFound, int score);

    DataExchange getDataExchange();
}
