package com.travel.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.listener.ExcelListener;
import com.travel.spzx.manager.mapper.CategoryMapper;
import com.travel.spzx.manager.service.CategoryService;
import com.travel.spzx.model.entity.product.Category;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {


        // 根据分类id查询它下面的所有的子分类数据
        List<Category> categoryList = categoryMapper.selectByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {

            // 遍历分类的集合，获取每一个分类数据
            categoryList.forEach(item -> {

                // 查询该分类下子分类的数量
                Integer count = categoryMapper.countByParentId(item.getId());
                item.setHasChildren(count > 0);
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {

            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            //设置头信息是为了支持文件以下载的方式打开
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // 查询数据库中的数据
            List<Category> categoryList = categoryMapper.selectAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }

            // 写出数据到浏览器端
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);

        } catch (IOException e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            //创建监听器对象，传递mapper对象
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
            //调用read方法读取excel数据
            EasyExcel.read(file.getInputStream(),
                    CategoryExcelVo.class,
                    excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
