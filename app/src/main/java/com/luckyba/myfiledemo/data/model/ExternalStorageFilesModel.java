package com.luckyba.myfiledemo.data.model;

public class ExternalStorageFilesModel {
    private String fileName;
    private String filePath;
    private boolean selected;
    private boolean isDir;
    private boolean isCheckboxVisible;

    public ExternalStorageFilesModel() {
    }

    public ExternalStorageFilesModel(String fileName, String filePath, boolean isDir, boolean isSelected, boolean isCheckboxVisible) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.selected = isSelected;
        this.isCheckboxVisible = isCheckboxVisible;
        this.isDir = isDir;
    }

    public boolean isCheckboxVisible() {
        return isCheckboxVisible;
    }

    public void setCheckboxVisible(boolean checkboxVisible) {
        isCheckboxVisible = checkboxVisible;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }
    public void setIsDir(boolean isDir) {
        this.isDir = isDir;
    }
    public boolean isDir() {
        return isDir;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
