package Games;


import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CardActions
{

    public CardActions() {
    }

    public boolean isFlush(ArrayList<Card> cards)
    {
        boolean ans = false;
        Hashtable<Suit,ArrayList<Card>> table = new Hashtable<>();
        for(Suit s : Suit.values())
        {
            table.put(s,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.suit).add(c);
        }

        for(Suit n : Suit.values())
        {
            if(table.get(n).size() >=5) ans=true;

        }
        return ans;

    }

    public int getMaxValue(ArrayList<Card> cards)
    {
        return Collections.max(cards).number.value;
    }

    public int NumOfPairs(ArrayList<Card> cards)
    {
        int numOfPairs= 0;
        Hashtable<Number,ArrayList<Card>> table = new Hashtable<>();
        for(Number n : Number.values())
        {
            table.put(n,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.number).add(c);
        }

        for(Number n : Number.values())
        {
            if(table.get(n).size() ==2) numOfPairs +=1;
        }
        return numOfPairs;
    }

    public boolean isFullHouse(ArrayList<Card> cards)
    {
        int numOfPairs= 0;
        int numOfTrio=0;
        Hashtable<Number,ArrayList<Card>> table = new Hashtable<>();
        for(Number n : Number.values())
        {
            table.put(n,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.number).add(c);
        }

        for(Number n : Number.values())
        {
            if(table.get(n).size() ==2) numOfPairs +=1;
            if(table.get(n).size() ==3) numOfTrio +=1;
        }
        return (numOfPairs==1 && numOfTrio ==1);
    }

    public boolean isPair(ArrayList<Card> cards)
    {
        return NumOfPairs(cards) ==1;
    }
    public boolean isTwoPair(ArrayList<Card> cards)
    {
        return NumOfPairs(cards) ==2;
    }

    public boolean isStraight(ArrayList<Card> cards)
    {
        int numOfSuccessives =0;
        ArrayList<Integer> vals = new ArrayList<>();
        for(Card c : cards)
            vals.add( c.number.value);

        Set<Integer> ans = new HashSet<>(vals);
        if(ans.size()<5) return false;
        else
        {
            vals = new ArrayList<>(ans);
            Collections.sort(vals); //from the minimal to the maxiaml
            for(int i=0; i< vals.size()-4; i++)
            {
                if((vals.get(i+1) - vals.get(i) ==1)&&
                        (vals.get(i+2) - vals.get(i+1) ==1)&&
                        (vals.get(i+3) - vals.get(i+2) ==1)&&
                        (vals.get(i+4) - vals.get(i+3) ==1)) numOfSuccessives+=1;
            }
        }
        return (numOfSuccessives > 0);
    }

    public boolean isRoyalFlush(ArrayList<Card> cards)
    {
        boolean ans = false;
        Hashtable<Suit,ArrayList<Card>> table = new Hashtable<>();
        Collections.sort(cards); //this promise me that the cards are inserted to the table in increasing order

        for(Suit s : Suit.values())
        {
            table.put(s,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.suit).add(c);
        }

        for(Suit n : Suit.values())
        {
            if((table.get(n).size() >= 5) && checkRoyalFlush(table.get(n))) ans=true;

        }
        return ans;
    }

    private boolean checkRoyalFlush(ArrayList<Card> cards) {
        int isTen = 0;
        int isJack = 0;
        int isQueen = 0;
        int isKing = 0;
        int isAce = 0;

        for (Card c : cards) {
            switch (c.number.value) {
                case 10:
                    isTen++;
                    break;
                case 11:
                    isJack++;
                    break;
                case 12:
                    isQueen++;
                    break;
                case 13:
                    isKing++;
                    break;
                case 14:
                    isAce++;
                    break;
            }
        }
        return((isTen > 0) && (isAce > 0) && (isJack > 0) && (isQueen > 0) && (isKing > 0));
    }

    public boolean isStraightFlush(ArrayList<Card> cards)
    {
        boolean ans = false;
        Hashtable<Suit,ArrayList<Card>> table = new Hashtable<>();
        for(Suit s : Suit.values())
        {
            table.put(s,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.suit).add(c);
        }

        for(Suit n : Suit.values())
        {
            if((table.get(n).size() >=5)&& isFlush(table.get(n))) ans=true;

        }
        return ans;
    }
    public boolean isFourOfAKind(ArrayList<Card> cards)
    {
        return is_OfAKind(cards,4);
    }
    public boolean isThreeOfAKind(ArrayList<Card> cards)
    {
        return is_OfAKind(cards,3);
    }

    private boolean is_OfAKind(ArrayList<Card> cards , int howMuch)
    {
        boolean ans = false;
        Hashtable<Number,ArrayList<Card>> table = new Hashtable<>();
        for(Number n : Number.values())
        {
            table.put(n,new ArrayList<Card>());
        }
        for(Card c : cards)
        {
            table.get(c.number).add(c);
        }

        for(Number n : Number.values())
        {
            if(table.get(n).size()== howMuch) ans=true;

        }
        return ans;
    }


}
