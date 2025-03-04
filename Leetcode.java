import java.util.*;

class CodingPlatform {
    private List<User> users;
    private List<Problem> problems;

    public CodingPlatform() {
        this.users = new ArrayList<>();
        this.problems = new ArrayList<>();
    }

    public User registerUser(String name) {
        User user = new User(users.size() + 1, name);
        users.add(user);
        return user;
    }

    public Problem addProblem(String title, String description) {
        Problem problem = new Problem(problems.size() + 1, title, description);
        problems.add(problem);
        return problem;
    }

    public void displaySubmissions(Problem problem) {
        System.out.println("\nSubmissions for Problem: " + problem.title);
        for (Submission sub : problem.getSubmissions()) {
            System.out.println("Submission ID: " + sub.getSubmissionId() + 
                               ", User: " + sub.getUser().getName() + 
                               ", Status: " + sub.getStatus());
        }
    }

    public static void main(String[] args) {
        CodingPlatform platform = new CodingPlatform();

        User user1 = platform.registerUser("Alice");
        User user2 = platform.registerUser("Bob");

        Problem problem1 = platform.addProblem("Two Sum", "Find indices of two numbers that add up to target.");
        Problem problem2 = platform.addProblem("Reverse String", "Reverse a given string.");

        user1.submitSolution(problem1, "public int[] twoSum() { return new int[]{1,2}; }", "Java");
        user2.submitSolution(problem1, "public int[] twoSum() { return null; }", "Java");
        user1.submitSolution(problem2, "public String reverse() { return \"abc\"; }", "Java");

        platform.displaySubmissions(problem1);
        platform.displaySubmissions(problem2);
    }
}

class User {
    private int userId;
    private String name;
    private List<Submission> submissions;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.submissions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void submitSolution(Problem problem, String code, String language) {
        Submission submission = new Submission(this, problem, code, language);
        submissions.add(submission);
        problem.addSubmission(submission);
        System.out.println(name + " submitted a solution for " + problem.getTitle());
    }
}

class Problem {
    private int problemId;
    private String title;
    private String description;
    private List<Submission> submissions;

    public Problem(int problemId, String title, String description) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.submissions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }
}

class Submission {
    private static int submissionCounter = 0;
    private int submissionId;
    private User user;
    private Problem problem;
    private String code;
    private String language;
    private String status;

    public Submission(User user, Problem problem, String code, String language) {
        this.submissionId = ++submissionCounter;
        this.user = user;
        this.problem = problem;
        this.code = code;
        this.language = language;
        this.status = evaluate();
    }

    private String evaluate() {
        return code.contains("return") ? "Accepted" : "Rejected";
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }
}
