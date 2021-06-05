package us.linkpl.linkplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.mapper.AccountMapper;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountMapper accountMapper;


    /**
     * 注册
     * @param map
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        String nickname = map.get("nickname");
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("username", username).or().eq("nickname",nickname);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size()>0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String encryption = DigestUtils.md5DigestAsHex(password.getBytes());
        Account account = new Account();
        account.setUsername(username);
        account.setSecretKey(encryption);
        account.setNickname(nickname);
        accountMapper.insert(account);
        return ResponseEntity.ok().build();
    }

    /**
     * 登录
     * @param map 请求信息
     * @param session session
     * @return 状态码
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String,String> map, HttpSession session){
        String username = map.get("username");
        String password = map.get("password");
        String encryption = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("username", username);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Account account = accounts.get(0);
        if (username.equals(account.getUsername())&&encryption.equals(account.getSecretKey())){
            session.setAttribute("accountId",account.getId());
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * 注销
     * @param session session
     * @return 状态码
     */
    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session){
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.removeAttribute("accountId");
        return ResponseEntity.ok().build();
    }

    /**
     * 删除账户
     * @param session session
     * @param id 要删除账户的id
     * @return 状态码
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(HttpSession session,@PathVariable("id") Long id){
        if (id==null){ //id为空
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Account account = accountMapper.selectById(id);
        if (account==null){ //要删除的账户不存在
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //session.removeAttribute("accountId");  //退出登录
        accountMapper.deleteById(id);  //删除账户
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/username")
    public ResponseEntity repeatUsername(@RequestParam("username") String username){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("username", username);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size()==0)return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/nickname")
    public ResponseEntity repeatNickname(@RequestParam("nickname") String nickname){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("nickname", nickname);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size()==0)return ResponseEntity.ok().build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
