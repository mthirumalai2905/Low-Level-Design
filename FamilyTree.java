import java.util.*;

class Person {
    String name;
    String gender;
    List<Person> children;

    public Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.children = new ArrayList<>();
    }

    public void addChild(Person child) {
        children.add(child);
    }
}

class FamilyTree {
    private Person root;

    public FamilyTree(Person root) {
        this.root = root;
    }

    public void printTree() {
        printTreeHelper(root, 0);
    }

    private void printTreeHelper(Person person, int level) {
        if (person == null) return;
        System.out.println("  ".repeat(level) + "|-- " + person.name + " (" + person.gender + ")");
        for (Person child : person.children) {
            printTreeHelper(child, level + 1);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Person grandParent = new Person("John", "Male");
        Person parent1 = new Person("Alice", "Female");
        Person parent2 = new Person("Bob", "Male");
        Person child1 = new Person("Charlie", "Male");
        Person child2 = new Person("David", "Male");

        grandParent.addChild(parent1);
        grandParent.addChild(parent2);
        parent1.addChild(child1);
        parent2.addChild(child2);

        FamilyTree familyTree = new FamilyTree(grandParent);
        familyTree.printTree();
    }
}
