package reseaux;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 1350092881346723569L;

    private DataType _type;
    private Object   _content;

    public Data(DataType type, Object content) {
        this._type = type;
        this._content = content;
    }

    public DataType get_type() {
        return _type;
    }

    public void set_type(DataType _type) {
        this._type = _type;
    }

    public Object get_content() {
        return _content;
    }

    public void set_content(Object _content) {
        this._content = _content;
    }

}
