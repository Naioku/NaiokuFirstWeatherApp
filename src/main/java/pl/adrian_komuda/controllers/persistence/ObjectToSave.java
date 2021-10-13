package pl.adrian_komuda.controllers.persistence;

import java.io.Serializable;

public interface ObjectToSave extends Serializable {
    String getFileLocation();
    String getDirectoryLocation();
}
