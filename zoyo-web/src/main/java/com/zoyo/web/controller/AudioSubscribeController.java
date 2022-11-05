package com.zoyo.web.controller;

import com.zoyo.common.vo.PodcastSummary;
import com.zoyo.common.vo.Result;
import com.zoyo.web.service.IAudioSubscribeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 播客订阅表 前端控制器
 * </p>
 *
 * @author mxx
 */
@RestController
@RequestMapping("/as")
public class AudioSubscribeController {

    @Resource
    private IAudioSubscribeService subscribeService;

    /**
     * 解析 rss链接
     *
     * @return result
     */
    @PostMapping("/search")
    public Result<Object> searchPodCastSummary(@RequestBody String link) {
        return this.subscribeService.analysisPodcastRssLink(link);
    }

    /**
     * 订阅播客
     *
     * @param podcastSummary 播客信息
     * @return result
     */
    @PostMapping("/subscribe")
    public Result<Object> searchPodCastSummary(@RequestBody PodcastSummary podcastSummary) {
        return this.subscribeService.subscribePodcast(podcastSummary);
    }

    /**
     * 删除订阅
     *
     * @param id 播客id
     * @return result
     */
    @DeleteMapping("/del")
    public Result<Object> deleteSubscribe(@RequestBody String id) {
        return this.subscribeService.deleteSubscribePodcast(id);
    }

    /**
     * 获取订阅
     *
     * @return result
     */
    @GetMapping("/getPod")
    public Result<Object> getSubscribe() {
        return this.subscribeService.queryUserSubscribe();
    }
}
