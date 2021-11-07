package net.renfei.startd.inf.service;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import lombok.extern.slf4j.Slf4j;
import net.renfei.startd.inf.config.SystemConfig;
import net.renfei.startd.inf.dao.LeafAllocMapper;
import org.springframework.stereotype.Service;

/**
 * Leaf：美团分布式ID生成服务
 * Segment：数据库号段服务
 *
 * @author renfei
 */
@Slf4j
@Service
public class LeafService {
    private IDGen idGen;
    private final SystemConfig systemConfig;
    private final LeafAllocMapper mapper;

    LeafService(SystemConfig systemConfig,
                LeafAllocMapper mapper) {
        this.systemConfig = systemConfig;
        this.mapper = mapper;
        boolean segmentFlag = systemConfig.getLeaf().getSegment().getEnable();
        boolean snowflakeFlag = systemConfig.getLeaf().getSnowflake().getEnable();
        if (segmentFlag) {
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setMapper(mapper);
            if (idGen.init()) {
                log.info("Segment Service Init Successfully");
            } else {
                throw new RuntimeException("Segment Service Init Fail");
            }
        } else if (snowflakeFlag) {
            String zkAddress = systemConfig.getLeaf().getSnowflake().getZk().getAddress();
            int port = systemConfig.getLeaf().getSnowflake().getPort();
            idGen = new SnowflakeIDGenImpl(zkAddress, port);
            if (idGen.init()) {
                log.info("Snowflake Service Init Successfully");
            } else {
                throw new RuntimeException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
