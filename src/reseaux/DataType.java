package reseaux;

public enum DataType {
    PROPOSED_KEYWORD(1),        // string
    KEYWORD(2),                 // KeyWord
    START(3),                   // null
    WORD_FOUND(4),              // integer
    SCORE(5),                   // integer
    NICKNAME(6),                // string
    TEST(10);

    private int value = 1;

    DataType(int val) {
        this.value = val;
    }

    public int getvalue() {
        return value;
    }

};