package us.linkpl.linkplus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.mapper.AccountMapper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/linkplus/account")
public class AccountController {
    @Autowired
    AccountMapper accountMapper;

    @RequestMapping("/a")
    public Account get(){
        return accountMapper.selectById(1);
    }

}
