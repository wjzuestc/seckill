package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”的角度设计接口（使用者怎么调用你的接口）
 * 方法定义粒度，参数，返回类型（return 类型友好/异常）
 * Created by wjz on 2017/4/7.
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckill();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     * （防止拼接知道我们的秒杀url地址，提前秒杀）
     * @param seckillId
     * @return Exposer
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     *执行秒杀业务
     * @param seckillId
     * @param userphone
     * @param md5
     * @return SeckillExecution
     * @throws RepeatKillException
     * @throws SeckillCloseException
     * @throws SeckillException
     */
    SeckillExecution executeSeckill(long seckillId, long userphone, String md5)
            throws RepeatKillException,SeckillCloseException,SeckillException;

    /**
     * 执行秒杀业务 by 存储过程
     * @param seckillId
     * @param userphone
     * @param md5
     * @return SeckillExecution
     * @throws RepeatKillException
     * @throws SeckillCloseException
     * @throws SeckillException
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userphone, String md5);
}
