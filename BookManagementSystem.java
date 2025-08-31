import java.util.Scanner;

public class BookManagementSystem {
    private static final int MAX_BOOKS = 5; // Array Size
    private static Book[] books = new Book[MAX_BOOKS];
    private static int count = 0;
    private static int nextId = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Book Management System ===");

        while (running) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Books by Category");
            System.out.println("4. Search Book by ID");
            System.out.println("5. Delete Book by ID");
            System.out.println("6. Update Book by ID");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1: addBook(sc); break;
                    case 2: viewBooks(); break;
                    case 3: searchBooksByCategory(sc); break;
                    case 4: searchBookById(sc); break;
                    case 5: deleteBook(sc); break;
                    case 6: updateBook(sc); break;
                    case 7:
                        running = false;
                        System.out.println("Exiting Book Management System...");
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid input!");
                sc.nextLine(); // clear invalid input
            }
        }

        sc.close();
    }

    // Add Book
    private static void addBook(Scanner sc) {
        try {
            if (count >= MAX_BOOKS) {
                throw new BookStorageFullException("Book storage is full. Cannot add more books.");
            }

            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author Name: ");
            String author = sc.nextLine();

            System.out.println("Available Categories:");
            for (BookCategory category : BookCategory.values()) {
                System.out.println("- " + category);
            }

            System.out.print("Enter Category: ");
            String categoryStr = sc.next().toUpperCase();
            BookCategory category = BookCategory.valueOf(categoryStr);

            books[count++] = new Book(nextId++, title, author, category);
            System.out.println("Book Added Successfully!");

        } catch (BookStorageFullException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Category!");
        }
    }

    // View All Books
    private static void viewBooks() {
        if (count == 0) {
            System.out.println("No books available.");
        } else {
            System.out.println("\nAll Books:");
            for (int i = 0; i < count; i++) {
                System.out.println(books[i]);
            }
        }
    }

    // Search by Category
    private static void searchBooksByCategory(Scanner sc) {
        try {
            System.out.println("Available Categories:");
            for (BookCategory category : BookCategory.values()) {
                System.out.println("- " + category);
            }

            System.out.print("Enter Category to Search: ");
            String categoryStr = sc.next().toUpperCase();
            BookCategory category = BookCategory.valueOf(categoryStr);

            System.out.println("\nBooks in " + category + ":");
            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (books[i].getCategory() == category) {
                    System.out.println(books[i]);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No books found in this category.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Category!");
        }
    }

    // Search by ID
    private static void searchBookById(Scanner sc) {
        try {
            if (count == 0) {
                throw new BookNotFoundException("No books available to search.");
            }

            System.out.print("Enter Book ID to Search: ");
            int id = sc.nextInt();

            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (books[i].getId() == id) {
                    System.out.println("Book Found: " + books[i]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete Book
    private static void deleteBook(Scanner sc) {
        try {
            if (count == 0) {
                throw new BookNotFoundException("No books available to delete.");
            }

            System.out.print("Enter Book ID to Delete: ");
            int id = sc.nextInt();

            boolean deleted = false;
            for (int i = 0; i < count; i++) {
                if (books[i].getId() == id) {
                    for (int j = i; j < count - 1; j++) {
                        books[j] = books[j + 1];
                    }
                    books[--count] = null;
                    deleted = true;
                    System.out.println("Book with ID " + id + " deleted successfully.");
                    break;
                }
            }

            if (!deleted) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update Book
    private static void updateBook(Scanner sc) {
        try {
            if (count == 0) {
                throw new BookNotFoundException("No books available to update.");
            }

            System.out.print("Enter Book ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            boolean updated = false;
            for (int i = 0; i < count; i++) {
                if (books[i].getId() == id) {
                    System.out.print("Enter New Title (leave blank to keep unchanged): ");
                    String newTitle = sc.nextLine();
                    if (!newTitle.trim().isEmpty()) {
                        books[i].setTitle(newTitle);
                    }

                    System.out.print("Enter New Author (leave blank to keep unchanged): ");
                    String newAuthor = sc.nextLine();
                    if (!newAuthor.trim().isEmpty()) {
                        books[i].setAuthor(newAuthor);
                    }

                    System.out.println("Available Categories:");
                    for (BookCategory category : BookCategory.values()) {
                        System.out.println("- " + category);
                    }

                    System.out.print("Enter New Category (leave blank to keep unchanged): ");
                    String categoryStr = sc.nextLine().toUpperCase();
                    if (!categoryStr.trim().isEmpty()) {
                        try {
                            BookCategory newCategory = BookCategory.valueOf(categoryStr);
                            books[i].setCategory(newCategory);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid Category! Keeping old value.");
                        }
                    }

                    System.out.println("Book Updated: " + books[i]);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                throw new BookNotFoundException("Book with ID " + id + " not found.");
            }
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}