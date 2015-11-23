package reseaux;

public enum DataType {
	  PROPOSED_KEYWORD(1),
	  KEYWORD(2),
	  START(3),
	  WORD_FOUND(4),
	  SCORE(5),
	  NICKNAME(6),
	  TEST(10);

	  private int value = 1;
	  DataType(int val){
	    this.value = val;
	  }

	  public int getvalue(){
		  return value;
	  }

};