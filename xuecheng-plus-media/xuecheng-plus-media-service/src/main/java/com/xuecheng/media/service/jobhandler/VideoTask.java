package com.xuecheng.media.service.jobhandler;

import com.xuecheng.base.utils.Mp4VideoUtil;
import com.xuecheng.media.model.po.MediaProcess;
import com.xuecheng.media.service.MediaFileProcessService;
import com.xuecheng.media.service.MediaFileService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: VideoTask
 * Package: com.xuecheng.media.service.jobhandler
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/12 - 14:36
 * @Version: v1.0
 */
@Slf4j
@Component
public class VideoTask {
    @Autowired
    private MediaFileService mediaFileService;
    @Autowired
    private MediaFileProcessService mediaFileProcessService;

    //ffmpeg 绝对路径
    @Value("${videoprocess.ffmpegpath}")
    String ffmpegPath;

    /**
     * 视频处理
     */
    @XxlJob("videoJobHandler")
    public void videoJobHandler() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.debug("shardIndex=" + shardIndex + ",shardTotal=" + shardTotal);
//一次取出 2 条记录，可以调整此数据，一次处理的最大个数不要超过 cpu核心数
        List<MediaProcess> mediaProcesses = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, 2);
        //数据个数
        int size = mediaProcesses.size();
        log.debug("取出待处理视频记录" + size + "条");
        if (size <= 0) {
            return;
        }
        //启动 size 上线程数量的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(size);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);
        mediaProcesses.forEach(mediaProcess -> {
            threadPool.execute(() -> {
//桶
                String bucket = mediaProcess.getBucket();
//存储路径
                String filePath = mediaProcess.getFilePath();
//原始视频的 md5 值
                String fileId = mediaProcess.getFileId();
//原始文件名称
                String filename = mediaProcess.getFilename();
//下载文件
//先创建临时文件，为原始的视频文件
                File originalVideo = null;
//处理结束的 mp4 文件
                File mp4Video = null;
                try {
                    originalVideo = File.createTempFile("original", null);
                    mp4Video = File.createTempFile("mp4", ".mp4");
                } catch (IOException e) {
                    log.error("下载待处理的原始文件前创建临时文件失败");
                }
                try {
                    originalVideo = mediaFileService.downloadFileFromMinIO(originalVideo, bucket, filePath);
//开始处理视频
                    Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegPath, originalVideo.getAbsolutePath(), mp4Video.getName(), mp4Video.getAbsolutePath());
                    String result = mp4VideoUtil.generateMp4();
                    if (!result.equals("success")) {
//记录错误信息
                        log.error("generateMp4 error ,video_path is {},error msg is {}", bucket + filePath, result);
                        mediaFileProcessService.saveProcessFinishStatus(3, fileId, null, result);
                        return;
                    }
//将 mp4 上传至 minio
//文件路径
                    String objectName = getFilePath(fileId, ".mp4");
                    mediaFileService.addMediaFilesToMinIO(mp4Video.getAbsolutePath(), bucket, objectName);
//访问 url
                    String url = "/" + bucket + "/" + objectName;
//将 url 存储至数据，并更新状态为成功，并将待处理视频记录删除存入历史
                    mediaFileProcessService.saveProcessFinishStatus(2, fileId, url, null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//清理文件
                    if (originalVideo != null) {
                        try {
                            originalVideo.delete();
                        } catch (Exception e) {
                        }
                    }
                    if (mp4Video != null) {
                        try {
                            mp4Video.delete();
                        } catch (Exception e) {
                        }
                    }
                }
                countDownLatch.countDown();
            });
        });
//等待,给一个充裕的超时时间,防止无限等待，到达超时时间还没有处理完成则结束任务
        countDownLatch.await();
    }

    private String getFilePath(String fileMd5, String fileExt) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }
}


