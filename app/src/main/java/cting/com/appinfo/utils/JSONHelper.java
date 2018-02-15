package cting.com.appinfo.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cting.com.appinfo.model.AppInfoItem;
import cting.com.robin.support.commom.utils.FileHelper;

public class JSONHelper {

    public static boolean exportToJSONFile(String fileName, ArrayList<AppInfoItem> dataList) {
        Wrapper items = new Wrapper(dataList);
        Gson gson = new Gson();
        String content = gson.toJson(items);
        return FileHelper.exportToFile(content, fileName);
    }

    public static List<AppInfoItem> importFromJSONFile(String fileName) {
        FileReader reader = null;
        try {
            File file = new File(fileName);
            reader = new FileReader(file);
            Gson gson = new Gson();
            Wrapper items = gson.fromJson(reader, Wrapper.class);
            return items.getDataList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    static class Wrapper {
        private List<AppInfoItem> dataList;

        public Wrapper(ArrayList<AppInfoItem> dataList) {
            this.dataList = dataList;
        }

        public List<AppInfoItem> getDataList() {
            return dataList;
        }

        public void setDataList(List<AppInfoItem> dataList) {
            this.dataList = dataList;
        }
    }
}
