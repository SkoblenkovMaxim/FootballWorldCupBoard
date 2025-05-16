package ru.smartidea;

import java.util.*;

public class ScoreBoard {

    private final Map<Integer, Match> matches = new LinkedHashMap<Integer, Match>();
    private int currentId = 0;

    /**
     * Начало матча, добавление его в список
     * @param homeTeam наименование команы хозяев
     * @param awayTeam наименование команы гостей
     * @return id матча
     */
    public int startMatch(String homeTeam, String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Команда хозяев и гостей не должны совпадать");
        }
        Match match = new Match(homeTeam, awayTeam);
        int matchId = currentId++;
        matches.put(matchId, match);
        return matchId;
    }

    /**
     * Обновление результата матча
     * @param matchId id матча
     * @param homeGoals количество голов хозяев
     * @param awayGoals количество голов гостей
     */
    public void updateMatch(int matchId, int homeGoals, int awayGoals) {
        if (!matches.containsKey(matchId)) {
            throw new NoSuchElementException("Матч с id=" + matchId + " не найден");
        }
        Match match = matches.get(matchId);
        match.updateScore(homeGoals, awayGoals);
    }

    /**
     * Завершение матча и удаление из списка
     * @param matchId id матча
     */
    public void finishMatch(int matchId) {
        if (!matches.containsKey(matchId)) {
            throw new NoSuchElementException("Матч с id=" + matchId + " не найден");
        }
        matches.remove(matchId);
    }

    /**
     * Список матчей
     * @return сортированный список матчей
     */
    public List<Match> getMatches() {
        List<Match> sortedMatches = new ArrayList<Match>(matches.values());
        sortedMatches.sort((m1, m2) -> {
            int scoreDiff = m2.getTotalScore() - m1.getTotalScore();
            if (scoreDiff != 0) {
                return scoreDiff;
            }

            Collection<Match> values = matches.values();
            int idx1 = new ArrayList<>(values).indexOf(m1);
            int idx2 = new ArrayList<>(values).indexOf(m2);
            return Integer.compare(idx2, idx1);

            //return m1.getHomeTeam().compareTo(m2.getHomeTeam());
        });

        return sortedMatches;
    }

}
