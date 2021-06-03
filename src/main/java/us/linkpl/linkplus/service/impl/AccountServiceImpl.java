package us.linkpl.linkplus.service.impl;

import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.mapper.AccountMapper;
import us.linkpl.linkplus.service.IAccountService;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
