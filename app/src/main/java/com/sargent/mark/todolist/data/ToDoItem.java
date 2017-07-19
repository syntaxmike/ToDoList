package com.sargent.mark.todolist.data;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoItem {

    /*
     *  Changes here include added a category selection and if task is completed to the database.
     */
    private String description;
    private String dueDate;
    private String category;
    private String completion;

    public ToDoItem(String description, String dueDate, String category, String completion) {
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.completion = completion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }
}
