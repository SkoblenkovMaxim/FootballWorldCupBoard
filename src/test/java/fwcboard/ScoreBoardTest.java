package fwcboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {
    ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @DisplayName("startMatch")
    @Test
    void startMatch() {
        scoreBoard.startMatch("Мексика", "Канада");
        assertEquals(1, scoreBoard.getMatches().size());
    }

    @DisplayName("startMatchWithValidMatch")
    @Test
    void startMatch_shouldReturnValidMatchId() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoard.startMatch("Team1", "Team1"));
    }

    @DisplayName("updateMatch")
    @Test
    void updateMatch() {
        int matchId = scoreBoard.startMatch("Мексика", "Канада");
        scoreBoard.updateMatch(matchId, 0, 5);
        Match match = scoreBoard.getMatches().get(matchId);
        assertEquals(0, match.getHomeGoals());
        assertEquals(5, match.getAwayGoals());
        assertEquals("Мексика 0 - 5 Канада", match.toString());
    }

    @DisplayName("finishMatch")
    @Test
    void finishMatch() {
        int matchId = scoreBoard.startMatch("Мексика", "Канада");
        scoreBoard.finishMatch(matchId);
        assertEquals(0, scoreBoard.getMatches().size());
    }

    @DisplayName("getMatches")
    @Test
    void getMatches() {
        int id1 = scoreBoard.startMatch("Мексика", "Канада");
        int id2 = scoreBoard.startMatch("Испания", "Бразилия");
        int id3 = scoreBoard.startMatch("Германия", "Франция");
        int id4 = scoreBoard.startMatch("Уругвай", "Италия");
        int id5 = scoreBoard.startMatch("Аргентина", "Австралия");

        scoreBoard.updateMatch(id1, 0, 5);
        scoreBoard.updateMatch(id2, 10, 2);
        scoreBoard.updateMatch(id3, 2, 2);
        scoreBoard.updateMatch(id4, 6, 6);
        scoreBoard.updateMatch(id5, 3, 1);

        List<Match> matches = scoreBoard.getMatches();
        assertEquals(5, matches.size());
        assertEquals("Мексика 0 - 5 Канада", matches.get(0).toString());
        assertEquals("Испания 10 - 2 Бразилия", matches.get(1).toString());
        assertEquals("Германия 2 - 2 Франция", matches.get(2).toString());
        assertEquals("Уругвай 6 - 6 Италия", matches.get(3).toString());
        assertEquals("Аргентина 3 - 1 Австралия", matches.get(4).toString());
    }
}
