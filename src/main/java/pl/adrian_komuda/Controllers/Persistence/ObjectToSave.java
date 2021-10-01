package pl.adrian_komuda.Controllers.Persistence;

import java.io.Serializable;

public interface ObjectToSave extends Serializable {
    String getFileLocation();
    String getDirectoryLocation();
}
