package pl.adrian_komuda.Controllers.Persistence;

import java.io.*;

public class PersistenceAccess {

    public static ObjectToLoad loadDataFromFile(ObjectToLoad objectToLoad) {
        try {
            FileInputStream fileInputStream = new FileInputStream(objectToLoad.getFileLocation());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToLoad = (ObjectToLoad) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return objectToLoad;
    }

    public static void saveDataToFile(ObjectToSave objectToSave) {
        try {
            File directory = new File(objectToSave.getDirectoryLocation());
            directory.mkdirs();

            File file = new File(objectToSave.getFileLocation());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objectToSave);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
