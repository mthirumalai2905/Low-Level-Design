import java.util.*;

enum MatchStatus { NOT_STARTED, IN_PROGRESS, COMPLETED }
enum BallType { DOT, SINGLE, DOUBLE, TRIPLE, FOUR, SIX, WICKET, WIDE, NO_BALL }

class CricketSystem {

    static class Player {
        private final int id;
        private final String name;
        private int runs;
        private int balls;
        private int wickets;
        private int overs;

        public Player(int id, String name) {
            this.id = id;
            this.name = name;
            this.runs = 0;
            this.balls = 0;
            this.wickets = 0;
            this.overs = 0;
        }

        public void addRuns(int runs) { this.runs += runs; this.balls++; }
        public void addWicket() { this.wickets++; }
        public void addOver() { this.overs++; }

        @Override
        public String toString() {
            return name + " - Runs: " + runs + ", Balls: " + balls + ", Wickets: " + wickets + ", Overs: " + overs;
        }
    }

    static class Team {
        private final String name;
        private final List<Player> players;
        private int totalRuns;
        private int wickets;

        public Team(String name, List<Player> players) {
            this.name = name;
            this.players = players;
            this.totalRuns = 0;
            this.wickets = 0;
        }

        public void updateScore(int runs, boolean isWicket) {
            totalRuns += runs;
            if (isWicket) wickets++;
        }

        @Override
        public String toString() {
            return name + " - Score: " + totalRuns + "/" + wickets;
        }
    }

    static class Commentary {
        private final List<String> logs = new ArrayList<>();

        public void addLog(String log) { logs.add(log); }

        public List<String> getLogs() { return logs; }
    }

    static class Match {
        private final int matchId;
        private final Team team1;
        private final Team team2;
        private MatchStatus status;
        private final Commentary commentary;

        public Match(int matchId, Team team1, Team team2) {
            this.matchId = matchId;
            this.team1 = team1;
            this.team2 = team2;
            this.status = MatchStatus.NOT_STARTED;
            this.commentary = new Commentary();
        }

        public void startMatch() { status = MatchStatus.IN_PROGRESS; }

        public void updateScore(Team team, Player player, BallType ballType) {
            int runs = switch (ballType) {
                case SINGLE -> 1;
                case DOUBLE -> 2;
                case TRIPLE -> 3;
                case FOUR -> 4;
                case SIX -> 6;
                case WIDE, NO_BALL -> 1;
                default -> 0;
            };

            boolean isWicket = (ballType == BallType.WICKET);
            team.updateScore(runs, isWicket);
            player.addRuns(runs);
            if (isWicket) player.addWicket();

            commentary.addLog("Ball: " + ballType + " | " + player.toString() + " | " + team.toString());
        }

        public void endMatch() { status = MatchStatus.COMPLETED; }

        public void displayCommentary() { commentary.getLogs().forEach(System.out::println); }
    }

    static class MatchRepository {
        private final Map<Integer, Match> matchMap = new HashMap<>();

        public void saveMatch(Match match) { matchMap.put(match.matchId, match); }
        public Match getMatch(int matchId) { return matchMap.get(matchId); }
    }

    static class MatchService {
        private final MatchRepository repository = new MatchRepository();

        public void createMatch(int id, Team team1, Team team2) {
            Match match = new Match(id, team1, team2);
            repository.saveMatch(match);
        }

        public void startMatch(int id) { repository.getMatch(id).startMatch(); }
        public void updateScore(int id, Team team, Player player, BallType ballType) {
            repository.getMatch(id).updateScore(team, player, ballType);
        }
        public void endMatch(int id) { repository.getMatch(id).endMatch(); }
        public void showCommentary(int id) { repository.getMatch(id).displayCommentary(); }
    }

    public static void main(String[] args) {
        MatchService matchService = new MatchService();

        Player p1 = new Player(1, "Virat Kohli");
        Player p2 = new Player(2, "Jasprit Bumrah");
        Team team1 = new Team("India", List.of(p1, p2));
        Team team2 = new Team("Australia", List.of(new Player(3, "Steve Smith"), new Player(4, "Mitchell Starc")));

        matchService.createMatch(1001, team1, team2);
        matchService.startMatch(1001);
        matchService.updateScore(1001, team1, p1, BallType.FOUR);
        matchService.updateScore(1001, team1, p1, BallType.SIX);
        matchService.updateScore(1001, team1, p1, BallType.WICKET);
        matchService.endMatch(1001);
        matchService.showCommentary(1001);
    }
}
