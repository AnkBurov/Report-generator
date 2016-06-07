package org.github.ankburov.reportgenerator.settingshandler;

/**
 * Column class representing each column tag in the settings file
 */
public class Column {
    private String title;
    private int width;

    public Column() {
    }

    public Column(String title, int width) {
        this.title = title;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (width != column.width) return false;
        return title != null ? title.equals(column.title) : column.title == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + width;
        return result;
    }

    @Override
    public String toString() {
        return "Column{" +
                "title='" + title + '\'' +
                ", width=" + width +
                '}';
    }
}
