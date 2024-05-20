package com.travel.spzx.tour.guide.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import com.travel.spzx.tour.guide.mapper.GuideBatchMapper;
import com.travel.spzx.tour.guide.service.GuideBatchService;
import com.travel.xpzx.utils.AuthContextUtil;
import com.travel.xpzx.utils.BatchUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GuideBatchServiceImpl implements GuideBatchService {

    @Autowired
    private GuideBatchMapper guideBatchMapper;


    @Override
    public PageInfo<GuideBatchDetailVo> getLeaderBatchList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        TourUserInfo tourUserInfo = AuthContextUtil.getTourUserInfo();
        Long tourGuideId = tourUserInfo.getId();
        System.out.println("tourGuideId == " + tourGuideId);
        List<GuideBatchDetailVo> res = guideBatchMapper.getLeaderBatchList(tourGuideId, null)
                .stream().map(
                        guideBatch -> {

                            String duration = BatchUtils.computeDuration(guideBatch.getTime());
                            guideBatch.setDuration(duration);
                            guideBatch.setStartWeekStr(convertWeek(guideBatch.getStartWeek()));
                            guideBatch.setEndWeekStr(convertWeek(guideBatch.getEndWeek()));
                            guideBatch.setStatusStr(convertStatus(guideBatch));
                            return guideBatch;
                        }
                ).toList();

        PageInfo<GuideBatchDetailVo> pageInfo = new PageInfo<>(res);
        return pageInfo;
    }

    @Override
    public GuideBatchDetailVo getBatchInfo(String batchId) {
        TourUserInfo tourUserInfo = AuthContextUtil.getTourUserInfo();
        List<GuideBatchDetailVo> guideBatchDetailVos = guideBatchMapper.getLeaderBatchList(tourUserInfo.getId(), batchId)
                .stream().map(
                        guideBatch -> {
                            String duration = BatchUtils.computeDuration(guideBatch.getTime());
                            guideBatch.setDuration(duration);
                            guideBatch.setStartWeekStr(convertWeek(guideBatch.getStartWeek()));
                            guideBatch.setEndWeekStr(convertWeek(guideBatch.getEndWeek()));
                            guideBatch.setStatusStr(convertStatus(guideBatch));
                            return guideBatch;
                        }
                ).toList();
        return guideBatchDetailVos.isEmpty() ? null : guideBatchDetailVos.get(0);
    }

    @Override
    public PageInfo<TouristDetailVo> getCurrentBatchTripList(Integer page, Integer limit, String type, String batchId, String name, String carId) {
        PageHelper.startPage(page, limit);
        //type

        Integer checkStatusCode = Objects.equals(type, "all") ? null : Objects.equals(type, "wait") ? 0 : 1;
        System.out.println("typeCode == " + checkStatusCode);
        List<TouristDetailVo> res = guideBatchMapper.getCurrentBatchTripList(checkStatusCode, batchId, name, carId).stream().map(
                touristDetailVo -> {
                    touristDetailVo.setStatusStr(touristDetailVo.getStatusStr1());

                    return touristDetailVo;
                }
        ).toList();
        return new PageInfo<>(res);
    }


    @Override

    public Long touristSign(String batchId, String tripId, String orderItemId) {
        if (Strings.isEmpty(batchId) || Strings.isEmpty(tripId) || Strings.isEmpty(orderItemId)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        //todo 签到逻辑
        guideBatchMapper.touristSign(batchId, tripId, orderItemId);

        return 0L;
    }

    @Override
    public List<TourGuideVo> getCurrentBatchTourGuideList(String batchId,String carId) {
        List<TourGuideVo> res = guideBatchMapper.getCurrentBatchTourGuideList(batchId,carId);
        return res;
    }

    /**
     * 待出行，已结束，进行中，签到中，根据时间进行判断
     *
     * @param
     * @return
     */
    private String convertStatus(GuideBatchDetailVo guideBatchDetailVo) {
        //0 待出行，1 已完成，2 审核未通过，3 已发布，4 已取消
        //当前时间小于开始时间，则为待出行，guideBatchDetailVo.getStartTime()取年月日，+ guideBatchDetailVo.getAssembleTime()取时分秒
        Date startTime = guideBatchDetailVo.getStartTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(startTime);


        //当前时间大于集合时间，小于出发时间表示签到中
        //当前时间大于出发时间小于结束时间，表示进行中
        //当前时间大于结束时间，表示已结束

        return "待出行";
    }

    private String convertWeek(int startWeek) {

        String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        return week[startWeek - 1];
    }

}
