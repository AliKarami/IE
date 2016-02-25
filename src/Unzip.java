import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    public Unzip(String zipFile,String classPath){
        unzip(zipFile,classPath);
    }

    public void unzip(String zipFile,String classPath){

        if(classPath == null)
            classPath = "";
        else
            classPath += File.separator;

        File classDirectory = new File(classPath);

        if(classDirectory.exists())
            classDirectory.delete();

        classDirectory.mkdir();
        
        try {
            
            ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));

            ZipEntry entry = null;
            int len;
            byte[] buffer = new byte[1024];
            
            while((entry = zip.getNextEntry()) != null){

                if(!entry.isDirectory()){
                   // System.out.println("-" + entry.getName());
                    
                    File file = new File(classPath + entry.getName());
                    
                    if(!new File(file.getParent()).exists())
                        new File(file.getParent()).mkdirs();
                    
                    FileOutputStream fos = new FileOutputStream(file);

                    while ((len = zip.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }

            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}