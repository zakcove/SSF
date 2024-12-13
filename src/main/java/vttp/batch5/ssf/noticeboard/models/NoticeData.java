package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NoticeData {
    @Size(min = 3, max = 128, message = "Must be between 3 and 128 characters long.")
    private String title;

    @Email(message = "Invalid format.")
    private String poster;

    @Future(message = "Post date must be in the future.")
    private LocalDate postDate;

    @NotEmpty(message = "At least one category is required.")
    private List<String> categories;

    @NotEmpty(message = "Cannot be empty.")
    private String text;
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
