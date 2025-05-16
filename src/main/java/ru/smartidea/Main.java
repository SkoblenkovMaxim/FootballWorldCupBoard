package ru.smartidea;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск тестов...");

        testStartMatch();
        testStartMatchWithSameTeams();
        testUpdateMatch();
        testUpdateInvalidMatch();
        testFinishMatch();
        testFinishInvalidMatch();
        testSummaryOrdering();

        System.out.println("Все тесты выполнены.");
    }

    private static void testStartMatch() {
        ScoreBoard board = new ScoreBoard();
        int matchId = board.startMatch("Мексика", "Канада");
        assertEqual("testStartMatch: ID должен быть 0", 0, matchId);
    }

    private static void testStartMatchWithSameTeams() {
        ScoreBoard board = new ScoreBoard();
        try {
            board.startMatch("Team A", "Team A");
            fail("testStartMatchWithSameTeams: должно выбросить исключение");
        } catch (IllegalArgumentException ignored) {
            // OK
        }
    }

    private static void testUpdateMatch() {
        ScoreBoard board = new ScoreBoard();
        int id = board.startMatch("Германия", "Франция");
        board.updateMatch(id, 2, 3);

        Match match = board.getMatches().get(0);
        assertEqual("testUpdateMatch: домашняя команда", "Германия", match.getHomeTeam());
        assertEqual("testUpdateMatch: гостевая команда", "Франция", match.getAwayTeam());
        assertEqual("testUpdateMatch: счет дома", 2, match.getHomeGoals());
        assertEqual("testUpdateMatch: счет гостей", 3, match.getAwayGoals());
    }

    private static void testUpdateInvalidMatch() {
        ScoreBoard board = new ScoreBoard();
        try {
            board.updateMatch(999, 1, 1);
            fail("testUpdateInvalidMatch: должно выбросить исключение");
        } catch (NoSuchElementException ignored) {
            // OK
        }
    }

    private static void testFinishMatch() {
        ScoreBoard board = new ScoreBoard();
        int id = board.startMatch("Бразилия", "Аргентина");
        board.finishMatch(id);
        assertTrue("testFinishMatch: список матчей не пуст", board.getMatches().isEmpty());
    }

    private static void testFinishInvalidMatch() {
        ScoreBoard board = new ScoreBoard();
        try {
            board.finishMatch(999);
            fail("testFinishInvalidMatch: должно выбросить исключение");
        } catch (NoSuchElementException ignored) {
            // OK
        }
    }

    private static void testSummaryOrdering() {
        ScoreBoard board = new ScoreBoard();

        int id1 = board.startMatch("Мексика", "Канада");
        int id2 = board.startMatch("Испания", "Бразилия");
        int id3 = board.startMatch("Германия", "Франция");
        int id4 = board.startMatch("Уругвай", "Италия");
        int id5 = board.startMatch("Аргентина", "Австралия");

        board.updateMatch(id1, 0, 5);
        board.updateMatch(id2, 10, 2);
        board.updateMatch(id3, 2, 2);
        board.updateMatch(id4, 6, 6);
        board.updateMatch(id5, 3, 1);

        List<Match> summary = board.getMatches();

        assertEqual("testSummaryOrdering: позиция 0", "Уругвай 6 - 6 Италия", summary.get(0).toString());
        assertEqual("testSummaryOrdering: позиция 1", "Испания 10 - 2 Бразилия", summary.get(1).toString());
        assertEqual("testSummaryOrdering: позиция 2", "Мексика 0 - 5 Канада", summary.get(2).toString());
        assertEqual("testSummaryOrdering: позиция 3", "Аргентина 3 - 1 Австралия", summary.get(3).toString());
        assertEqual("testSummaryOrdering: позиция 4", "Германия 2 - 2 Франция", summary.get(4).toString());
    }

    // Вспомогательные методы для проверки
    private static void assertEqual(String message, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            fail(message + ": ожидается <" + expected + ">, получено <" + actual + ">");
        } else {
            System.out.println("Тест пройден: " + message);
        }
    }

    private static void assertEqual(String message, int expected, int actual) {
        if (expected != actual) {
            fail(message + ": ожидается <" + expected + ">, получено <" + actual + ">");
        } else {
            System.out.println("Тест пройден: " + message);
        }
    }

    private static void assertTrue(String message, boolean condition) {
        if (!condition) {
            fail(message);
        } else {
            System.out.println("Тест пройден: " + message);
        }
    }

    private static void fail(String message) {
        System.err.println("Ошибка: " + message);
        System.exit(1);
    }
}
