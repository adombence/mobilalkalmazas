package hu.adombence.sajatdb;

public class Ember {
    private String nev;
    private int szev;

    public Ember(String nev, int szev) {
        this.nev = nev;
        this.szev = szev;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getSzev() {
        return szev;
    }

    public void setSzev(int szev) {
        this.szev = szev;
    }

    @Override
    public String toString() {
        return "nev=" + nev + '\n' +
                "szev=" + szev;
    }
}
