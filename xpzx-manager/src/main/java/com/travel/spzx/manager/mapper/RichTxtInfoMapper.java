package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RichTxtInfoMapper {
    void save(RichTxtInfo data);

    void delete(Integer id);

    List<RichTxtInfo> findAll();

    void update(RichTxtInfo cityInfo);
}
