package pl.adrian_komuda.controllers.persistence;

import java.io.*;

public class PersistenceAccess {

    public static ObjectToLoad loadDataFromFile(ObjectToLoad objectToLoad) throws ClassNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(objectToLoad.getFileLocation());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        objectToLoad = (ObjectToLoad) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();

        return objectToLoad;
    }

    public static void saveDataToFile(ObjectToSave objectToSave) throws IOException {
        File directory = new File(objectToSave.getDirectoryLocation());
        directory.mkdirs();

        File file = new File(objectToSave.getFileLocation());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(objectToSave);
        objectOutputStream.close();
        fileOutputStream.close();
    }
}
