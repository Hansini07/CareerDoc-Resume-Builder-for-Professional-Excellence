import java.util.*;

// Resume Class
class Resume {
    String name;
    String email;
    String phone;
    String education;
    String skills;
    String projects;
    LinkedList<String> extraSections;

    Resume(String name, String email, String phone, String education, String skills, String projects) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.education = education;
        this.skills = skills;
        this.projects = projects;
        extraSections = new LinkedList<>();
    }

    void addSection(String section) {
        extraSections.add(section);
    }

    void display() {
        System.out.println("\n========== RESUME ==========");
        System.out.println("Name     : " + name);
        System.out.println("Email    : " + email);
        System.out.println("Phone    : " + phone);
        System.out.println("Education: " + education);
        System.out.println("\nSkills   : " + skills);
        System.out.println("\nProjects : " + projects);
        if (!extraSections.isEmpty()) {
            System.out.println("\nExtra Sections:");
            for (String s : extraSections) {
                System.out.println("- " + s);
            }
        }
        System.out.println("============================");
    }
}

// Resume Manager Class
class ResumeManager {
    ArrayList<Resume> resumes = new ArrayList<>();
    Stack<Resume> undoStack = new Stack<>();
    Stack<Resume> redoStack = new Stack<>();
    Resume currentResume = null;

    // Validate required fields
    boolean validate(String name, String email, String phone, String education) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || education.isEmpty()) {
            System.out.println("Name, Email, Phone, and Education are required!");
            return false;
        }
        return true;
    }

    void createResume(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine().replaceAll("[0-9]", "");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine().replaceAll("[^0-9]", "");

        System.out.print("Enter Education: ");
        String education = sc.nextLine();

        System.out.print("Enter Skills: ");
        String skills = sc.nextLine();

        System.out.print("Enter Projects: ");
        String projects = sc.nextLine();

        if (!validate(name, email, phone, education)) return;

        Resume r = new Resume(name, email, phone, education, skills, projects);

        System.out.print("Add extra section? (yes/no): ");
        String choice = sc.nextLine();
        while (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter section content: ");
            String sec = sc.nextLine();
            r.addSection(sec);
            System.out.print("Add another section? (yes/no): ");
            choice = sc.nextLine();
        }

        resumes.add(r);
        undoStack.push(r);
        redoStack.clear();
        currentResume = r;

        System.out.println("Resume Created Successfully!");
    }

    void showResumes() {
        if (resumes.isEmpty()) {
            System.out.println("No resumes available.");
            return;
        }
        System.out.println("\nSaved Resumes:");
        for (int i = 0; i < resumes.size(); i++) {
            System.out.println(i + " -> " + resumes.get(i).name);
        }
    }

    void openResume(int index) {
        if (index >= 0 && index < resumes.size()) {
            currentResume = resumes.get(index);
            currentResume.display();
        } else {
            System.out.println("Invalid index.");
        }
    }

    void deleteResume(int index) {
        if (index >= 0 && index < resumes.size()) {
            Resume removed = resumes.remove(index);
            undoStack.push(removed);
            System.out.println("Resume Deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    void undo() {
        if (!undoStack.isEmpty()) {
            Resume r = undoStack.pop();
            resumes.remove(r);
            redoStack.push(r);
            System.out.println("Undo Completed.");
        } else {
            System.out.println("Nothing to Undo.");
        }
    }

    void redo() {
        if (!redoStack.isEmpty()) {
            Resume r = redoStack.pop();
            resumes.add(r);
            undoStack.push(r);
            System.out.println("Redo Completed.");
        } else {
            System.out.println("Nothing to Redo.");
        }
    }
}

// Main Class
public class CareerDocDSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ResumeManager manager = new ResumeManager();

        while (true) {
            System.out.println("\n======= CareerDoc Resume Builder =======");
            System.out.println("1. Create Resume");
            System.out.println("2. View All Resumes");
            System.out.println("3. Open Resume");
            System.out.println("4. Delete Resume");
            System.out.println("5. Undo");
            System.out.println("6. Redo");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> manager.createResume(sc);
                case 2 -> manager.showResumes();
                case 3 -> {
                    manager.showResumes();
                    System.out.print("Enter resume index to open: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    manager.openResume(index);
                }
                case 4 -> {
                    manager.showResumes();
                    System.out.print("Enter resume index to delete: ");
                    int index = sc.nextInt();
                    sc.nextLine();
                    manager.deleteResume(index);
                }
                case 5 -> manager.undo();
                case 6 -> manager.redo();
                case 7 -> {
                    System.out.println("Thank you for using CareerDoc!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}