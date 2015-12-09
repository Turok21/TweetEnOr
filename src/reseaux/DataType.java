package reseaux;

/**
 * Permet de d�finir le type de donn�e envoy� et recus
 * et permet ainsi de savoir quoi faire et comment r�utiliser ces donn�es � la reception
 */
public enum DataType {
    PROPOSED_KEYWORD(1),        // string
    KEYWORD(2),                 // KeyWord
    START(3),                   // null
    WORD_FOUND(4),              // integer
    SCORE(5),                   // integer
    NICKNAME(6),                // string
    LINE_LOADER(11),
    ERROR(12);

    private int value = 1;

    DataType(int val) {
        this.value = val;
    }

    public int getvalue() {
        return value;
    }
}