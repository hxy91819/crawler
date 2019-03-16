package bwjava.service.util;

import bwjava.util.CommonUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @author chenjing
 * @date 2019-03-16 22:40
 */
@Log4j2
public class BeautyLegUtil {

    /**
     * 根据url获取图片排序号
     *
     * @param url 图片url
     * @return 排序号
     */
    public static int getPicSortNumber(String url) {
        try {
            int last = url.lastIndexOf('/');
            int endfixLength = ".jpg".length();
            String substring = url.substring(last + 1, url.length() - endfixLength);
            return Integer.valueOf(substring);
        } catch (Exception e) {
            log.error("getPicSortNumber exception:{}", CommonUtil.getStackTrace(e));
            return Integer.MAX_VALUE;
        }
    }

}
