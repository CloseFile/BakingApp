package com.ctapk.bakingapp.db;

import android.arch.persistence.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class ListConverter {
    @TypeConverter
    public String fromList(List<String> stringList) {
        String data = null;
        for (String str : stringList) { data.concat(str + "\n"); }
//                stringList.stream().collect(Collectors.joining("\n"));
        return data;
    }
    @TypeConverter
    public List<String> toList(String data) {
        return Arrays.asList(data.split("\n"));
    }
}