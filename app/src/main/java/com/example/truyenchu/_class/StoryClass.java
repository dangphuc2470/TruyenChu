package com.example.truyenchu._class;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Lớp Truyen (Truyện)
public class StoryClass implements Serializable
{
    private final int id;
    private String name;
    private String time;
    private String author;
    private String status;
    private String description;
    private int numberOfChapter;
    //private Image image;
    private List<ChapterClass> chapters = new ArrayList<>(); // List để lưu danh sách các chương
    private List<String> genres;
    private int views;

    private String uri; // Retrive data from Firebase



    // Constructor
    public StoryClass(int id, String name, String time, String author, String status, String description, int numberOfChapter, List<String> genres, int views)
    {
        this.id = id;
        this.name = name;
        this.time = time;
        this.author = author;
        this.status = status;
        this.description = description;
        this.numberOfChapter = numberOfChapter;
        this.genres = genres;
        this.views = views;
    }

    public StoryClass(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getName(int cutOffWhenMoreThan)
    {
        if (name.length() > cutOffWhenMoreThan)
        {
            int lastIndex = name.lastIndexOf(' ', cutOffWhenMoreThan);
            if (lastIndex != -1)
            {
                return name.substring(0, lastIndex) + "...";
            }
        }
        return name;
    }


    public String getAuthor()
    {
        return author;
    }

    public String getStatus()
    {
        return status;
    }

    public int getNumberOfChapter()
    {
        return numberOfChapter;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public List<ChapterClass> getChapters()
    {
        return chapters;
    }

    public int getViews()
    {
        return views;
    }

    // Thêm chương vào danh sách của truyện
    public void addChapter(ChapterClass chapterClass)
    {
        chapters.add(chapterClass);
    }


    public String getTime()
    {
        return time;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUri()
    {
        return uri;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setNumberOfChapter(int numberOfChapter)
    {
        this.numberOfChapter = numberOfChapter;
    }

    public void setChapters(List<ChapterClass> chapters)
    {
        this.chapters = chapters;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public void setViews(int views)
    {
        this.views = views;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ID: ").append(id).append("\n");
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Time: ").append(time).append("\n");
        stringBuilder.append("Author: ").append(author).append("\n");
        stringBuilder.append("Status: ").append(status).append("\n");
        stringBuilder.append("Description: ").append(description).append("\n");
        stringBuilder.append("Number of Chapters: ").append(numberOfChapter).append("\n");
        stringBuilder.append("Genres: ").append(genres).append("\n");
        stringBuilder.append("Views: ").append(views).append("\n");
        stringBuilder.append("URI: ").append(uri).append("\n");

        // Append chapters information
        stringBuilder.append("Chapters: \n");
        for (ChapterClass chapter : chapters) {
            stringBuilder.append("\tChapter ID: ").append(chapter.getChapterId()).append(", Content: ").append(chapter.getContent()).append("\n");
        }

        return stringBuilder.toString();
    }
}