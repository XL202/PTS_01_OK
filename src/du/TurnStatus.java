package du;

public class TurnStatus {
    private int actions;
    private int buys;
    private int coins;

    public TurnStatus() {
        this.actions = 1;
        this.buys = 1;
        this.coins = 0;
    }

    public int getBuys() {
        return buys;
    }

    public void setBuys(int buys) {
        this.buys = buys;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + actions;
        result = prime * result + buys;
        result = prime * result + coins;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TurnStatus other = (TurnStatus) obj;
        if (actions != other.actions)
            return false;
        if (buys != other.buys)
            return false;
        return coins == other.coins;
    }
}
