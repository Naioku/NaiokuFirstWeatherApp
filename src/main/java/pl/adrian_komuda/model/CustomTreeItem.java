package pl.adrian_komuda.model;

import javafx.scene.control.TreeItem;

public class CustomTreeItem extends TreeItem {
    private String treeLevel;

    public CustomTreeItem(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public CustomTreeItem(Object value, String treeLevel) {
        super(value);
        this.treeLevel = treeLevel;
    }

    public String getTreeLevel() {
        return treeLevel;
    }
}
