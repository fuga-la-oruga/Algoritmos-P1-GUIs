package dev.esteban.solitarieRelated.deckOfCards;
/**
 * Palos de cartas de una baraja inglesa.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-1)
 */
public enum Palo {
    TREBOL(1,"T","negro"),
    DIAMANTE(2,"D","rojo"),
    CORAZON(3,"C","rojo"),
    PICA(4,"P","negro");

    private final int peso;
    private final String figura;
    private final String color;

    Palo(int peso, String figura, String color) {
        this.peso = peso;
        this.figura = figura;
        this.color = color;
    }
    public int getPeso() {
        return peso;
    }
    public String getFigura() {
        return figura;
    }
    public String getColor() {
        return color;
    }
}