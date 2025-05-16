package ru.smartidea;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeGoals = 0;
    private int awayGoals = 0;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    /**
     * Обновление результата матча
     * @param homeGoals количество голов хозяев
     * @param awayGoals количество голов гостей
     */
    public void updateScore(int homeGoals, int awayGoals) {
        if (homeGoals < 0 || awayGoals < 0) {
            throw new IllegalArgumentException("goals must be >= 0");
        }
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getTotalScore() {
        return homeGoals + awayGoals;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeGoals + " - " + awayGoals + " " + awayTeam;
    }

}
