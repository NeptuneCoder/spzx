package com.travel.spzx.tour.guide.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.order.OrderLog;
import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.status.OrderStateEnum;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import com.travel.spzx.tour.guide.mapper.GuideBatchMapper;
import com.travel.spzx.tour.guide.mapper.OrderLogMapper;
import com.travel.spzx.tour.guide.service.GuideBatchService;
import com.travel.xpzx.utils.AuthContextUtil;
import com.travel.xpzx.utils.BatchUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GuideBatchServiceImpl implements GuideBatchService {

    @Autowired
    private GuideBatchMapper guideBatchMapper;


    @Autowired
    private OrderLogMapper orderLogMapper;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long touristSign(String batchId, String tripId, String orderId, String orderItemId) {
        if (Strings.isEmpty(batchId) || Strings.isEmpty(tripId) || Strings.isEmpty(orderId) || Strings.isEmpty(orderItemId)) {
            throw new IllegalArgumentException("参数不能为空");
        }

        //更新订单签到状态，
        //如果order_item和order_info为一对一的关系，则order_info的status更新为已签到
        //如果order_item和order_info为多对一的关系 ，部分签到时则显示部分签到，全部签到时则显示已签到
        //todo 订单签到状态更新逻辑
        //todo 签到逻辑
        ;
        guideBatchMapper.touristSign(OrderStateEnum.SignInSuccess.getCode(),
                batchId,
                tripId,
                orderId,
                orderItemId,
                1);//1 表示签到成功
        //todo 增加日志记录

        //查询当前的order_item 是否还存在未签到的item，如果有则显示部分签到，如果没有则显示已签到
        List<OrderItem> res = guideBatchMapper.queryCurrentOrderIsAllSign(orderId, batchId);
        if (res.isEmpty()) {
            guideBatchMapper.updateOrderStatus(OrderStateEnum.SignInSuccess.getCode(), orderId);
            OrderLog orderLog = new OrderLog();
            orderLog.setOrderId(Long.parseLong(orderId));
            orderLog.setProcessStatus(OrderStateEnum.SignInSuccess.getCode());
            orderLogMapper.save(orderLog);
        } else {
            guideBatchMapper.updateOrderStatus(OrderStateEnum.PartSignIn.getCode(), orderId);
            OrderLog orderLog = new OrderLog();
            orderLog.setOrderId(Long.parseLong(orderId));
            orderLog.setProcessStatus(OrderStateEnum.PartSignIn.getCode());
            orderLog.setNote(orderItemId + " 签到成功");
            orderLogMapper.save(orderLog);
        }
        //TODO  增加日志记录
        return 0L;
    }

    @Override
    public List<TourGuideVo> getCurrentBatchTourGuideList(String batchId, String carId) {
        List<TourGuideVo> res = guideBatchMapper.getCurrentBatchTourGuideList(batchId, carId);
        return res;
    }

    @Override
    public void tripFinished(String batchId, String carId) {
        //该状态由领队触发，表示当前行程已经结束：OrderStateEnum.WaitComment
        //这里涉及到多车的问题，只有一车的领队才能进行该操作，所以这里需要判断当前用户是否为领队，如果不是则抛出异常
        TourUserInfo tourUserInfo = AuthContextUtil.getTourUserInfo();
        //TODO 通过获取当前批次的车次信息batch_bus_info，查出第一辆车，再根据第一辆车的id，从bus_tour_guide_info 查出第一个领队
        //如果当前用户不是领队，则抛出异常
        //判断当前用户是否为一车的领队，如果是则可以进行关闭该批次
//        guideBatchMapper.updateBatchStatus(batchId, OrderStateEnum.WaitComment.getCode());

        //更新订单状态为已完成，order_status
        //查询所有
        List<OrderInfo> orderInfoList = guideBatchMapper.queryCurBatchAllOrder(batchId);

        //更新该批次中所有订单状态为待评价，如果用户未签到则可以申请退款，如果部分签到了则可以申请部分退款，如果全部签到了则进入待评价状态

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
