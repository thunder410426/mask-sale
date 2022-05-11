package com.example.masksale.controller;

import com.example.masksale.entity.RecordOutbound;
import com.example.masksale.response.Result;
import com.example.masksale.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 刘京毫
 * @Date: 2022/05/10/20:46
 * @Description:
 */
@RestController
public class ReduceInventionController {

    @Resource
    InventoryService inventoryService;


//    出库
    @PostMapping("/reduce")
    public Result<Void> reduce(@RequestParam String equipmentId, @RequestParam String nickName, @RequestParam Integer
            isSale){
        if (inventoryService.queryInventory(equipmentId) <= 0){
            return Result.failure("对不起，库存不足");
        }else {
            RecordOutbound recordOutbound = new RecordOutbound();

            recordOutbound.setEquipmentId(equipmentId);
            Date date =new Date();
            recordOutbound.setSaleTime(date);
            recordOutbound.setIsDone("1");
            UUID uuid = UUID.randomUUID();
            recordOutbound.setOrderNumber(uuid.toString());
            recordOutbound.setBuyersName(nickName);
            recordOutbound.setBuyersId("1");
            recordOutbound.setIsSale(isSale);
            recordOutbound.setSaleByn("111");

//            出货记录
            inventoryService.outBoundRecord(recordOutbound);
//            减少库存
            inventoryService.reduce(equipmentId);
            return Result.success();
        }
    }
}
