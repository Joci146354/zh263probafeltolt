package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Connect44 {
    private static final int OSZLOPOK = 7;
    private static final char URES = '.';
    private static final char JATEKOS1 = 'P';
    private static final char JATEKOS2 = 'S';
    private char[][] tabla = new char[6][7];
    private String jatekos1Nev;
    private String jatekos2Nev;

    public Connect44(String jatekos1Nev, String jatekos2Nev, String fajlNev) {
        this.jatekos1Nev = jatekos1Nev;
        this.jatekos2Nev = jatekos2Nev;
        if (!tablaBeolvas(fajlNev)) {
            tablaUres();
        }
    }

    private void tablaUres() {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                this.tabla[i][j] = URES;
            }
        }
    }


    public void tablaKiir() {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                System.out.print(this.tabla[i][j] + " ");
            }
            System.out.println();
        }
    }


    private boolean tablaBeolvas(String fajlNev) {
        File fajl = new File(fajlNev);
        try (Scanner fajlOlvaso = new Scanner(fajl)) {
            for (int i = 0; i < 6; ++i) {
                String sor = fajlOlvaso.nextLine();
                for (int j = 0; j < 7; ++j) {
                    this.tabla[i][j] = sor.charAt(j);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Nem található a fájl, üres táblát hozunk létre.");
            return false;
        }
    }


    public void tablaMent(String fajlNev) {
        try (FileWriter iras = new FileWriter(fajlNev)) {
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 7; ++j) {
                    iras.write(this.tabla[i][j]);
                }
                iras.write("\n");
            }
            System.out.println("Tábla mentése sikeres.");
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl írása közben.");
        }
    }

    public boolean korongElhelyez(int oszlop, char jatekos) {
        if (oszlop >= 0 && oszlop < 7 && this.tabla[0][oszlop] == URES) {
            for (int i = 5; i >= 0; --i) {
                if (this.tabla[i][oszlop] == URES) {
                    this.tabla[i][oszlop] = jatekos;
                    return true;
                }
            }
            return false;
        } else {
            System.out.println("Érvénytelen lépés!");
            return false;
        }
    }

    public boolean nyertEllenoriz(char jatekos) {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (this.tabla[i][j] == jatekos && this.tabla[i][j + 1] == jatekos && this.tabla[i][j + 2] == jatekos && this.tabla[i][j + 3] == jatekos) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (this.tabla[i][j] == jatekos && this.tabla[i + 1][j] == jatekos && this.tabla[i + 2][j] == jatekos && this.tabla[i + 3][j] == jatekos) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (this.tabla[i][j] == jatekos && this.tabla[i + 1][j + 1] == jatekos && this.tabla[i + 2][j + 2] == jatekos && this.tabla[i + 3][j + 3] == jatekos) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 3; j < 7; ++j) {
                if (this.tabla[i][j] == jatekos && this.tabla[i + 1][j - 1] == jatekos && this.tabla[i + 2][j - 2] == jatekos && this.tabla[i + 3][j - 3] == jatekos) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean teleTabla() {
        for (int j = 0; j < 7; ++j) {
            if (this.tabla[0][j] == URES) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner beolvaso = new Scanner(System.in);
        System.out.print("Add meg az 1. játékos nevét (piros): ");
        String jatekos1Nev = beolvaso.nextLine();
        System.out.print("Add meg a 2. játékos nevét (sárga): ");
        String jatekos2Nev = beolvaso.nextLine();


        String fajlNev = "tabla.txt";
        Connect44 jatek = new Connect44(jatekos1Nev, jatekos2Nev, fajlNev);
        char jelenlegiJatekos = JATEKOS1;
        boolean nyertE = false;
        System.out.println("Üdv a Kapcsolat 4 játékban!");
        jatek.tablaKiir();

        while (!nyertE && !jatek.teleTabla()) {
            String jatekosNev = jelenlegiJatekos == JATEKOS1 ? jatek.jatekos1Nev : jatek.jatekos2Nev;
            System.out.println("Jelenlegi játékos: " + jatekosNev);
            System.out.println("Adj meg egy oszlopot (0-6): ");
            int oszlop = beolvaso.nextInt();

            if (jatek.korongElhelyez(oszlop, jelenlegiJatekos)) {
                jatek.tablaKiir();
                if (jatek.nyertEllenoriz(jelenlegiJatekos)) {
                    nyertE = true;
                    System.out.println(jatekosNev + " nyert!");
                } else {
                    jelenlegiJatekos = (jelenlegiJatekos == JATEKOS1) ? JATEKOS2 : JATEKOS1;
                }
            }
        }

        if (!nyertE) {
            System.out.println("Döntetlen! A tábla tele van.");
        }


        jatek.tablaMent(fajlNev);

        beolvaso.close();
    }
}
