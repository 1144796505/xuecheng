package com.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.media.mapper.MediaFilesMapper;
import com.xuecheng.media.mapper.MediaProcessHistoryMapper;
import com.xuecheng.media.mapper.MediaProcessMapper;
import com.xuecheng.media.model.po.MediaFiles;
import com.xuecheng.media.model.po.MediaProcess;
import com.xuecheng.media.model.po.MediaProcessHistory;
import com.xuecheng.media.service.MediaFileProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: MediaFileProcessServiceImpl
 * Package: com.xuecheng.media.service.impl
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/12 - 13:19
 * @Version: v1.0
 */
@Service
public class MediaFileProcessServiceImpl implements MediaFileProcessService {
    @Autowired
    private MediaProcessMapper mediaProcessMapper;

    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Autowired
    private MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Transactional
    @Override
    public void saveProcessFinishStatus(int status, String fileId, String url, String errorMsg) {
        MediaProcess mediaProcess = mediaProcessMapper.selectOne(
                new LambdaQueryWrapper<MediaProcess>().eq(MediaProcess::getFileId, fileId));

            if (mediaProcess == null){
                return;
            }
        //处理失败
        if (status == 3){
            mediaProcess.setStatus("3");
            mediaProcess.setErrormsg(errorMsg);
            mediaProcessMapper.updateById(mediaProcess);
            return ;
        }
        //处理成功
        if(status == 2){
            MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
            if(mediaFiles!=null){
                mediaFiles.setUrl(url);
                mediaFilesMapper.updateById(mediaFiles);
            }
            mediaProcess.setUrl(url);
            mediaProcess.setStatus("2");
            mediaProcess.setFinishDate(LocalDateTime.now());
            mediaProcessMapper.updateById(mediaProcess);
//添加到历史记录
            MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
            BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
            mediaProcessHistoryMapper.insert(mediaProcessHistory);
//删除 mediaProcess
            mediaProcessMapper.deleteById(mediaProcess.getId());
        }
    }

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int
            shardTotal, int count) {
        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);
        return mediaProcesses;
    }
}

