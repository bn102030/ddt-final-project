package com.ddt.finalproject.utils;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.google.common.collect.Maps;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class DictionaryCsvUtil {

    public static Map<String, String> getBookkeepingMap() throws IOException {




        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        InputStream inputStream = DictionaryCsvUtil.class.getClassLoader().getResourceAsStream("csv" + File.separator + "bookkeeping_data.csv");
        if (null == inputStream){
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("src/main/resources/csv/bookkeeping_data.csv"), "UTF-8");

        Map<String, String> map = Maps.newHashMap();

        CsvData data = reader.read(inputStreamReader);

        List<CsvRow> rows = data.getRows();

        for (CsvRow csvRow : rows) {
            map.put(csvRow.get(0), csvRow.get(1));
        }
        return map;
    }


}
