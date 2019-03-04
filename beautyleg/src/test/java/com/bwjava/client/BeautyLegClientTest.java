package com.bwjava.client;

import com.alibaba.fastjson.JSON;
import com.bwjava.dto.ModelInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chenjing
 * @date 2019-02-24 15:25
 */
public class BeautyLegClientTest {


    @Test
    public void testGetModelInfo() {
//        client.getModelInfo();
    }

    @Test
    public void testGetPics() {
        BeautyPicClient client = new BeautyPicClient(5, Collections.emptyList());
//        List<String> picUrls = client.getPicUrls("https://www.meituri.com/a/25661/",43);
    }

    @Test
    public void testFetchAll() {
        String url = "https://www.meituri.com/a/25656/";
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setId(1L);
        modelInfo.setEntranceUrl("https://www.meituri.com/a/25656/");

        ModelInfo modelInfo1 = new ModelInfo();
        modelInfo1.setId(2L);
        modelInfo1.setEntranceUrl("https://www.meituri.com/a/25290/");

        ModelInfo modelInfo2 = new ModelInfo();
        modelInfo2.setId(3L);
        modelInfo2.setEntranceUrl("https://www.meituri.com/a/611/");

        List<ModelInfo> modelInfos1 = Arrays.asList(modelInfo, modelInfo1, modelInfo2);
        modelInfos1 = JSON.parseArray("[{\"entranceUrl\":\"https://www.meituri.com/a/25661/\",\"id\":299583561525100544},{\"entranceUrl\":\"https://www.meituri.com/a/25660/\",\"id\":299583569800462336},{\"entranceUrl\":\"https://www.meituri.com/a/25659/\",\"id\":299583569800462337},{\"entranceUrl\":\"https://www.meituri.com/a/25658/\",\"id\":299583569800462338},{\"entranceUrl\":\"https://www.meituri.com/a/25657/\",\"id\":299583569800462339},{\"entranceUrl\":\"https://www.meituri.com/a/25656/\",\"id\":299583569800462340},{\"entranceUrl\":\"https://www.meituri.com/a/25290/\",\"id\":299583569800462341},{\"entranceUrl\":\"https://www.meituri.com/a/25289/\",\"id\":299583569800462342},{\"entranceUrl\":\"https://www.meituri.com/a/25288/\",\"id\":299583569800462343},{\"entranceUrl\":\"https://www.meituri.com/a/25287/\",\"id\":299583569800462344},{\"entranceUrl\":\"https://www.meituri.com/a/25286/\",\"id\":299583569800462345},{\"entranceUrl\":\"https://www.meituri.com/a/25285/\",\"id\":299583569800462346},{\"entranceUrl\":\"https://www.meituri.com/a/25284/\",\"id\":299583569800462347},{\"entranceUrl\":\"https://www.meituri.com/a/25132/\",\"id\":299583569800462348},{\"entranceUrl\":\"https://www.meituri.com/a/25131/\",\"id\":299583569800462349},{\"entranceUrl\":\"https://www.meituri.com/a/25130/\",\"id\":299583569800462350},{\"entranceUrl\":\"https://www.meituri.com/a/25129/\",\"id\":299583569800462351},{\"entranceUrl\":\"https://www.meituri.com/a/25128/\",\"id\":299583569800462352},{\"entranceUrl\":\"https://www.meituri.com/a/25127/\",\"id\":299583569800462353},{\"entranceUrl\":\"https://www.meituri.com/a/25126/\",\"id\":299583569800462354}]", ModelInfo.class);
        BeautyPicClient client = new BeautyPicClient(5,
                modelInfos1);
        List<ModelInfo> modelInfos = client.doFetchAllUrls();
    }
}