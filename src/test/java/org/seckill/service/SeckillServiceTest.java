package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wjz on 2017/4/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckill() throws Exception {
        List<Seckill> list = seckillService.getSeckill();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
//        logger.info("seckill={ }", seckill); //占位符不能有空格
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
//        秒杀开启输出
//        exposer=Exposer{exposed=true, md5='755c13f83dbf1e4e555a2568c396657a', seckillId=1000, now=0, start=0, end=0
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phone = 1395494570;
        String md5 = "755c13f83dbf1e4e555a2568c396657a";
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("execution={}", execution);
//        execution=SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功',
//                successKilled=SuccessKilled{seckilledId=1000, userPhone=1575494571, state=0, createTime=Fri Apr 07 23:33:30 CST 2017}}
    }

    //整合测试代码逻辑，业务完整性
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        //秒杀开启了
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 1375494570;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("execution={}", execution);
            } catch (RepeatKillException e) {
                //可接受的异常类
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

    /**
     * 测试存储过程
     */
    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1000L;
        long phone = 18359315884L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info("execution:" + execution);
        }
    }
}