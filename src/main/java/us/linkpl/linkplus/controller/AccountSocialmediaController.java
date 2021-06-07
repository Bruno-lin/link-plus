package us.linkpl.linkplus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import us.linkpl.linkplus.mapper.AccountSocialmediaMapper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/api/account-socialmedia")
public class AccountSocialmediaController {

    @Autowired
    AccountSocialmediaMapper accountSocialmediaMapper;

    @PostMapping("")
    public ResponseEntity addAccountSocialMedia(){

    }

}
