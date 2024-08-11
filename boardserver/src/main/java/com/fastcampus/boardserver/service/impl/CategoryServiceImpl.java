package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        log.info("register");
        if(accountId != null) {
            categoryMapper.register(categoryDTO);
        } else{
            log.error("register ERROR! {}" , categoryDTO);
            throw new RuntimeException("카테고리 등록 매서드를 확인해주세요");
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        log.info("update");
        if(categoryDTO != null) {
            categoryMapper.update(categoryDTO);
        } else{
            log.error("update ERROR! {}" , categoryDTO);
            throw new RuntimeException("카테고리 수정 매서드를 확인해주세요");
        }
    }

    @Override
    public void delete(int categoryId) {
        log.info("delete");
        if(categoryId != 0) {
            categoryMapper.delete(categoryId);
        } else{
            log.error("delete ERROR! {}" , categoryId);
            throw new RuntimeException("카테고리 삭제 매서드를 확인해주세요");
        }
    }



}
