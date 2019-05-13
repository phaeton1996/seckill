package com.graduation.seckill.service;

import com.graduation.seckill.dao.AddrDao;
import com.graduation.seckill.entity.Addr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddrService {
    @Autowired
    private AddrDao addrDao;

    public List<Addr> getById(int userId){
        return addrDao.getByUserId(userId);
    }
}
