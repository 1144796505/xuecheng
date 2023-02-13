package com.xuecheng.media.service;

import com.xuecheng.media.model.po.MediaProcess;

import java.util.List;

/**
 * ClassName: MediaFileProcessService
 * Package: com.xuecheng.media.service
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/12 - 12:46
 * @Version: v1.0
 * 媒资文件处理业务方法
 */
public interface MediaFileProcessService {
    public void saveProcessFinishStatus(int status,String fileId, String url,String errorMsg);

    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);
}
