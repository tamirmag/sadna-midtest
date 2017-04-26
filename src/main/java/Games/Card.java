package Games;

public class Card implements Comparable<Card> {
    Color color;
    Number number;
    Suit suit;

    public Card(Color color, Number number, Suit suit) {
        this.color = color;
        this.number = number;
        this.suit = suit;
    }

    @Override
    public int compareTo(Card o) {
        return this.number.value - o.number.value;
    }
}
