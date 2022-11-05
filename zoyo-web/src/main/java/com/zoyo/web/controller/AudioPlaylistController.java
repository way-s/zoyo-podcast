package com.zoyo.web.controller;

import com.zoyo.common.vo.NewEnvelopeVo;
import com.zoyo.common.vo.Result;
import com.zoyo.web.service.IAudioPlaylistService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 播放列表表 前端控制器
 * </p>
 *
 * @author mxx
 */
@RestController
@RequestMapping("/pl")
public class AudioPlaylistController {

    @Resource
    private IAudioPlaylistService playlistService;

    /**
     * 添加节目到播放列表
     *
     * @param vo vo
     * @return result
     */
    @PostMapping("/add")
    public Result<Object> addPlayList(@RequestBody NewEnvelopeVo vo) {
        return this.playlistService.addEpisodeToPlayList(vo);
    }

    /**
     * 获取播放列表
     *
     * @return result
     */
    @GetMapping("/list")
    public Object queryPlayList() {
        return this.playlistService.getPlayListByUserId();
    }

    /**
     * 删除
     *
     * @param vo vo
     * @return result
     */
    @PostMapping("/del")
    public Result<Object> del(@RequestBody NewEnvelopeVo vo) {
        return this.playlistService.delEpisode(vo);
    }

}
