void main(){

		Tokenizer TO = new Tokenizer("e 2 ^ 5 * sin tan");
		
		Token [] t = TO.maker();

		FunctionResolver fr = new FunctionResolver();
		
		Token [] Z = fr.resolveAll(t);

		Plotter PO = new Plotter();

		PO.drawFunc(Z);

		UPNParser P = new UPNParser(Z);

		Expr g = P.parse();

		CalcTree U = new CalcTree(g);

		TreeMaker TM = new TreeMaker();

		String h = TM.planter(g);

 Clerk.clear();
    // Markdown 1
    Clerk.markdown(Text.fillOut("""
    # Interaktive LVP Demo

    ## Markdown
    Die Markdown-View erlaubt es, Markdown-Text direkt im Browser darzustellen. Der folgende Code zeigt ein einfaches Beispiel, wie Text im Markdown-Format 
    an den Browser gesendet und dort automatisch als HTML gerendert wird:
    ```java
    ${0}
    ```
    Der Aufruf `Clerk.markdown(text)` elaubt den einfachen Zugriff auf die Markdown-View.
    In diesem Beispiel werden zusätzlich zwei unterstützende Skills verwendet:
    - `Text.fillOut(...)`: Zum Befüllen von String-Vorlagen mit dynamischen Inhalten, indem Platzhalter (z.B. ${2}) durch die Auswertung von übergebenen Ausdrücken ersetzt werden.
    - `Text.codeBlock(...)`: Zum Einbinden von Codeabschnitten als interaktive Blöcke im Markdown-Text.

    ## Turtle
    Die Turtle-View ermöglicht das Zeichnen und Anzeigen von SVG-Grafiken im Browser. Diese können Schritt für Schritt aufgebaut werden:
    ```java
    ${1}
    ```
    """, Text.codeBlock("./demo.java", "// Markdown 1"), Text.codeBlock("./demo.java", "// Turtle 1"), "${0}"));
    // Markdown 1


    // Dot
    Dot dot = new Dot();
    dot.draw("digraph G {"+h+"}");
    // Dot
}


// Turtle triangle
void triangle(Turtle turtle, double size) {
    turtle.forward(size).right(60).backward(size).right(60).forward(size).right(60 + 180);
}

void drawing(Turtle turtle, double size) {
    for (int i = 1; i <= 18; i++) {
        turtle.color(255, i * 256 / 37, i * 256 / 37, 1); // turtle color
        turtle.width(1.0 - 1.0 / 36.0 * i);
        triangle(turtle, size + 1 - 2 * i);
        turtle.left(20).forward(5);
    }
}
