package com.zoyo.web.controller;

import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;
import com.zoyo.web.service.IAudioCollectsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 播客收藏表 前端控制器
 * </p>
 *
 * @author mxx
 */
@RestController
@RequestMapping("/ac")
public class AudioCollectsController {

    @Resource
    private IAudioCollectsService collectsService;

    /**
     * 添加收藏
     *
     * @param vo vo
     * @return result
     */
    @PostMapping("/add")
    public Result<Object> addPlayList(@RequestBody NewEnvelopeVo vo) {
        return this.collectsService.addEpisodeToCollectList(vo);
    }

    /**
     * 获取播放列表
     *
     * @return result
     */
    @GetMapping("/list")
    public Object queryPlayList() {
        return this.collectsService.getCollectListByUserId();
    }

    /**
     * 删除
     *
     * @param vo vo
     * @return result
     */
    @PostMapping("/del")
    public Result<Object> del(@RequestBody NewEnvelopeVo vo) {
        return this.collectsService.delEpisode(vo);
    }

}
