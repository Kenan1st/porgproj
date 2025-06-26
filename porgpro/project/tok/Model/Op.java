package project.tok.Model;

public enum Op implements Token{
	ADD,
	SUB,
	DIV,
	MUL,
	MOD,
	POW;

@Override
    public String toString() {
        switch (this) {
            case ADD:
                return "+";    // Für Addition
            case SUB:
                return "-";    // Für Subtraktion
            case MUL:
                return "*";    // Für Multiplikation
            case DIV:
                return "/";    // Für Division
            case POW:
                return "^";    // Für Potenzierung
            default:
                throw new IllegalArgumentException("Unbekannter Operator: " + this);
        }
    }
}
