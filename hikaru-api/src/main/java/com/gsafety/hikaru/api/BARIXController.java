package com.gsafety.hikaru.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import savvy.wit.framework.core.facility.client.pa.PaFacility;

import javax.annotation.Resource;

@Api("PA设备控制器")
@RestController
@RequestMapping("/pow/arms/v1/pa/barix")
public class BARIXController {

    @Resource
    @Qualifier("BARIXClient")
    private PaFacility paFacility;

    @ApiOperation(value = "播放", notes = "播放")
    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public void play() {
        paFacility.play();
    }

    @ApiOperation(value = "暂停", notes = "暂停")
    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    public void pause() {
        paFacility.pause();
    }

    @ApiOperation(value = "设置音量百分比0-100%", notes = "0-100%")
    @RequestMapping(value = "/setVolPresent/{num}", method = RequestMethod.GET)
    public Object setVolPresent(@PathVariable @ApiParam("音量") int num) {
        return paFacility.setVolPresent(num);
    }

    @ApiOperation(value = "设置音量等级0-20", notes = "0-20")
    @RequestMapping(value = "/setVolLevel/{num}", method = RequestMethod.GET)
    public Object setVolLevel(@PathVariable @ApiParam("音量") int num) {
        return paFacility.setVolLevel(num);
    }

    @ApiOperation(value = "增大音量+1", notes = "增大音量")
    @RequestMapping(value = "/volup/{num}", method = RequestMethod.GET)
    public Object volUp(@PathVariable @ApiParam("音量") int num) {
        return paFacility.volUp(num);
    }

    @ApiOperation(value = "减少音量-1", notes = "减少音量")
    @RequestMapping(value = "/voldown/{num}", method = RequestMethod.GET)
    public Object volDown(@PathVariable @ApiParam("音量") int num) {
        return paFacility.volDown(num);
    }

    @ApiOperation(value = "获取音量", notes = "获取音量")
    @RequestMapping(value = "/getvol", method = RequestMethod.GET)
    public Object getVol() {
        return paFacility.getVol();
    }

    @ApiOperation(value = "关闭资源", notes = "关闭资源")
    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public Object close() {
        paFacility.close();
        return "OKey";
    }

    @ApiOperation(value = "重新获取资源", notes = "重新获取资源")
    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public Object connect() {
        paFacility.start();
        return "OKey";
    }


}
