package us.linkpl.linkplus.mapper;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import us.linkpl.linkplus.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}
