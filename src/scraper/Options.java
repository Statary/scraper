package scraper;

public class Options {

	private Boolean verbose;
	private Boolean word_number;
	private Boolean char_number;
	private Boolean show_sentences;

    public Boolean IsVerbose() {
        return verbose;
    }
    private void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }
    public Boolean IsWord_number() {
        return word_number;
    }
    private void setWord_number(Boolean word_number) {
        this.word_number = word_number;
    }
    public Boolean IsChar_number() {
        return char_number;
    }
    private void setChar_number(Boolean char_number) {
        this.char_number = char_number;
    }
    public Boolean IsShow_sentences() {
        return show_sentences;
    }
    private void setShow_sentences(Boolean show_sentences) {
        this.show_sentences = show_sentences;
    }

    public Options() {
        
    }
    public Options(String[] args) {
        ClearOptions();
        SetOptions(args);
    }

    private void ClearOptions() {
        setVerbose(false);
        setWord_number(false);
        setChar_number(false);
        setShow_sentences(false);
    }
    
    public void SetOptions(String[] args) {
        for (int i = 0; i < args.length; i++) {
            Option option = Option.CHAR_NUMBER.getOption(args[i]);
            switch (option){
                case VERBOSE: setVerbose(true); break;
                case WORD_NUMBER: setWord_number(true); break;
                case CHAR_NUMBER: setChar_number(true); break;
                case SHOW_SENTENCES: setShow_sentences(true); break;
            }
        }
    }
}
enum Option {
    VERBOSE("-v"), WORD_NUMBER("-w"), CHAR_NUMBER("-c"), SHOW_SENTENCES("-e");

	private final String option;
 
	private Option(String s) {
		option = s;
	}
 
	public Option getOption(String opt) {
		for (Option type: Option.values()) {
            if (type.option.equals(opt)) {
                return type;
            }
        }
        throw new RuntimeException("Unexisted Option");
	}
}
