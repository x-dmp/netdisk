package edu.cuit.fs.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.cuit.fs.domain.file;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class userFiles {
    private List<file> files;
    private String ID;
    private Gson gson;

    public userFiles(String id){
        this.ID = id;
        gson = new Gson();
    }

    public userFiles setFiles(List<file> files){
        this.files = files;
        return this;
    }

    public List<file> getFiles(){
        return files;
    }

    public userFiles insertFiles(boolean isDir, String fileName, String location, String hash){
        file f = new file();
        f.setDir(isDir);
        f.setFilename(fileName);
        f.setLocation(location);
        f.setHash(hash);
        List<file> fs =  getFs(location);
        f.setLocation(location);
        fs.add(f);
        return this;
    }

    private List<file> getFilesInfo(String location){
        List<file> fs = files;
        String[] locations = location.split("_");
        System.out.println(files);
        System.out.println(locations);
        for (int i = 0; i < locations.length-1; i++){
            fs = fs.get(i).getList();
        }
        return fs;
    }

    private List<file> getFs(String location){
        List<file> fs = files;
        if (location.equals("")){
            return files;
        }
        String[] S = location.split("_");
        List<String> strings = new LinkedList<>();
        for (int i = 0; i < S.length-1; i++){
            strings.add(S[i]);
        }
        int i = 0;
        String loc = strings.get(i);
        i++;
        while (true){
            int f = 0;
            for (file l : fs){
                if (loc.equals(l.getLocation())){
                    fs = l.getList();
                    if (i < strings.size()) loc  = loc + "_" + strings.get(i++);
                    if (loc == location){
                        return fs;
                    }
                    f = 1;
                }
            }
            if (f == 0) break;
        }
        return fs;
    }

    public userFiles insertFiles(boolean isDir, String fileName, String location, Integer childNum){
        file f = new file();
        f.setDir(isDir);
        f.setFilename(fileName);
        f.setLocation(location);
        f.setChildNum(childNum);
        List<file> fs = getFs(location);
        f.setLocation(location);
        fs.add(f);
        return this;
    }

    public userFiles init(){
        String json = null;
        try {
            json = DbUtil.getData("userinfo", this.ID, "file", "files");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        files = gson.fromJson(json, new TypeToken<List<file>>() {}.getType());
        return this;
    }

    public void saveFileInfo(){
        try {
            DbUtil.insertData("userinfo", this.ID, "file", "files", toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }

    @Override
    public String toString() {
        String json = gson.toJson(files);
        return json;
    }
}
