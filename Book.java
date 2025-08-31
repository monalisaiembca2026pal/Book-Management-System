class Book {
	private int id;
	private String title;
	private String author;
	private BookCategory category;
	public Book(int id, String title, String author, BookCategory category) {
	this.id = id;
	this.title = title;
	this.author = author;
	this.category = category;
 }
public int getId() { 
	return id;
}
public String getTitle() { 
	return title; 
}
public String getAuthor() { 
	return author;
}
public BookCategory getCategory() { 
	return category;
}
public void setTitle(String title) { 
	this.title = title; 
}
public void setAuthor(String author) { 
	this.author = author; 
}
public void setCategory(BookCategory category) { 
	this.category =category; 
}
 @Override
public String toString() {
	return "[" + id + "] " + title + " by " + author + " (" + category + ")";
}
}