import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test {

    private Kapcsolat4 jatek;

    @BeforeEach
    public void setUp() {
        jatek = new teszt("Piros", "Sárga");
    }

    @Test
    public void testKorongElhelyezese() {

        assertTrue(jatek.korongElhelyez(0, 'P'));
        assertEquals('P', jatek.tabla[5][0]);
    }

    @Test
    public void testErvenytelenKorongElhelyezes() {
        assertFalse(jatek.korongElhelyez(-1, 'P'));
        assertFalse(jatek.korongElhelyez(7, 'P'));
    }

    @Test
    public void testNyertVízszintesen() {
        for (int i = 0; i < 4; i++) {
            jatek.korongElhelyez(i, 'P');
        }
        assertTrue(jatek.nyertEllenoriz('P'));
    }

    @Test
    public void testNyertFuggolegesen() {
        for (int i = 0; i < 4; i++) {
            jatek.korongElhelyez(0, 'P');
        }
        assertTrue(jatek.nyertEllenoriz('P'));
    }

    @Test
    public void testDontetlen() {
        for (int i = 0; i < Kapcsolat4.SOROK; i++) {
            for (int j = 0; j < Kapcsolat4.OSZLOPOK; j++) {
                jatek.korongElhelyez(j, (i + j) % 2 == 0 ? 'P' : 'S');
            }
        }
        assertTrue(jatek.teleTabla());
        assertFalse(jatek.nyertEllenoriz('P'));
        assertFalse(jatek.nyertEllenoriz('S'));
    }
}
