package us.linkpl.linkplus.service.impl;

import us.linkpl.linkplus.entity.Accounts;
import us.linkpl.linkplus.mapper.AccountsMapper;
import us.linkpl.linkplus.service.IAccountsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements IAccountsService {

}
