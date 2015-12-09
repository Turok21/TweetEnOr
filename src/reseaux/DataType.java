package reseaux;

/**
 * Permet de définir le type de donnée envoyé et recus
 * et permet ainsi de savoir quoi faire et comment réutiliser ces données à la reception
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