package reseaux;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 1350092881346723569L;

    private DataType _type;
    private Object   _content;

    /**
     * @param type    {DataType} Type de donn�e
     * @param content {Object} Valeur de la donn�e
     */
    public Data(DataType type, Object content) {
        this._type = type;
        this._content = content;
    }

    public DataType get_type() {
        return _type;
    }

    public Object get_content() {
        return _content;
    }

}
