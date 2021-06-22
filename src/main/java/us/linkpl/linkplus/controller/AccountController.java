package us.linkpl.linkplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import us.linkpl.linkplus.commom.Consts;
import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.entity.AccountSocialmedia;
import us.linkpl.linkplus.entity.Follow;
import us.linkpl.linkplus.entity.response.*;
import us.linkpl.linkplus.mapper.AccountMapper;
import us.linkpl.linkplus.mapper.AccountSocialmediaMapper;
import us.linkpl.linkplus.mapper.FollowMapper;
import us.linkpl.linkplus.mapper.SocialMediaMapper;
import us.linkpl.linkplus.service.impl.AccountSocialmediaServiceImpl;
import us.linkpl.linkplus.service.impl.FollowServiceImpl;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    FollowServiceImpl followService;

    @Autowired
    AccountSocialmediaServiceImpl accountSocialmediaService;

    @Autowired
    SocialMediaMapper socialMediaMapper;

    @Autowired
    AccountSocialmediaMapper accountSocialmediaMapper;

    @Autowired
    FollowMapper followMapper;

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        String nickname = map.get("nickname");

        if (username == null || password == null || nickname == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!isAvalid("username", username)) {
            return ResponseEntity.ok("Invalid_Username");
        }
        if (!isAvalid("nickname", nickname) || nickname.length() > 32) {
            return ResponseEntity.ok("Invalid_Nickname");
        }

        //用户名,8到20位（字母，数字，下划线，减号）
        String nameReg = "^\\w{8,20}$";
        if (!Pattern.matches(nameReg, username)) {
            return ResponseEntity.ok("Invalid_Username");
        }

        //密码以英文开头，只能包含数组字幕下划线，长度6-20
        String reg = "^[a-zA-Z]\\w{8,20}$";
        if (!Pattern.matches(reg, password)) {
            return ResponseEntity.ok("Invalid_Password");
        }


        String[] ava = {"0.png", "1.png", "2.png", "3.png", "4.png"};
        String[] background = {"0.png", "1.png", "2.png"};


        String AVATAR = "/linkplus/avatar/";
        String BACKGROUND = "/linkplus/background/";

        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("username", username).or().eq("nickname", nickname);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size() > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String encryption = DigestUtils.md5DigestAsHex(password.getBytes());
        Random avaRandom = new Random();
        Random bgRandom = new Random();
        String avatar = AVATAR + ava[avaRandom.nextInt(4)];
        String backg = BACKGROUND + background[bgRandom.nextInt(2)];

        Account account = new Account();
        account.setUsername(username);
        account.setSecretKey(encryption);
        account.setNickname(nickname);
        account.setAvatar(avatar);
        account.setBackground(backg);
        accountMapper.insert(account);
        return ResponseEntity.ok("OK");
    }

    /**
     * 登录
     *
     * @param map     请求信息
     * @param session session
     * @return 状态码
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> map, HttpServletResponse response, HttpSession session) {

        String username = map.get("username");
        String password = map.get("password");
        if (username == null || password == null) {
            return ResponseEntity.ok("FAILED");
        }
        String encryption = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        queryWrapper.eq("username", username);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        if (accounts.size() == 0) {
            return ResponseEntity.ok("FAILED");
        }
        Account account = accounts.get(0);
        if (username.equals(account.getUsername()) && encryption.equals(account.getSecretKey())) {
            String token = String.valueOf(System.currentTimeMillis());
            session.setAttribute(String.valueOf(account.getId()), token);

            MyCookie myCookie = new MyCookie();
            myCookie.setId(account.getId());
            myCookie.setAvatar(account.getAvatar());
            myCookie.setNickname(account.getNickname());
            myCookie.setToken(token);
            return ResponseEntity.ok(myCookie);
        } else {
            return ResponseEntity.ok("FAILED");
        }
    }

    /**
     * 注销
     *
     * @return 状态码
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, @CookieValue("id") String id) {
        session.removeAttribute(id);
        return ResponseEntity.ok("OK");
    }

    /**
     * 删除账户
     *
     * @param id 要删除账户的id
     * @return 状态码
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @CookieValue("id") String cookieId) {
        if (id == null) { //id为空
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!String.valueOf(id).equals(cookieId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        Account account = accountMapper.selectById(id);
        if (account == null) { //要删除的账户不存在
            return ResponseEntity.ok("ACCOUNT_NOT_FOUND");
        }
        //session.removeAttribute("accountId");  //退出登录
        accountMapper.deleteById(id);  //删除账户

        QueryWrapper<AccountSocialmedia> queryWrapper = new QueryWrapper<>(); //删除社交媒体中间表中的对应数据
        queryWrapper.eq("accountId", id);
        accountSocialmediaMapper.delete(queryWrapper);

        QueryWrapper<Follow> queryWrapper1 = new QueryWrapper<>(); //删除关注列表和被关注列表
        queryWrapper1.eq("accountId", id).or().eq("followId", id);
        followMapper.delete(queryWrapper1);

        return ResponseEntity.ok("OK");
    }

    /**
     * 验证username是否重复
     *
     * @param username
     * @return
     */
    @GetMapping("/username")
    public ResponseEntity<String> repeatUsername(@RequestParam("username") String username) {
        boolean isUnique = isAvalid("username", username);
        if (isUnique) return ResponseEntity.ok("OK");
        else return ResponseEntity.ok("Invalid_Username");
    }

    /**
     * 查询是否重复
     *
     * @param column
     * @param username
     * @return
     */
    private boolean isAvalid(String column, String username) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, username);
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        return accounts.size() == 0;
    }


    /**
     * 验证nickname是否重复
     *
     * @param nickname
     * @return
     */
    @GetMapping("/nickname")
    public ResponseEntity<String> repeatNickname(@RequestParam("nickname") String nickname) {
        boolean isUnique = isAvalid("nickname", nickname);
        if (isUnique) return ResponseEntity.ok("OK");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid_Nickname");
    }

    /**
     * 编辑个人信息
     *
     * @param account
     * @return
     */
    @PutMapping("/me")
    public ResponseEntity<Slogan> editAccount(@RequestBody Account account, @CookieValue("id") String id) {
        if (!String.valueOf(account.getId()).equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Slogan slogan = new Slogan();
        accountMapper.updateById(account);
        BeanUtils.copyProperties(account, slogan);
        return ResponseEntity.status(HttpStatus.OK).body(slogan);
    }


    /**
     * 随机获取用户
     *
     * @param num
     * @return
     */
    @GetMapping("/show")
    public ResponseEntity showAccounts(@RequestParam("num") Integer num) {
        if (num == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }
        List<Account> accounts = accountMapper.selectRandomAccount(num);
        List<SimpleAccount> response = new ArrayList<>();
        for (Account account : accounts) {
            SimpleAccount sa = new SimpleAccount();
            sa.setAvatar(account.getAvatar());
            sa.setNickname(account.getNickname());
            sa.setId(account.getId());
            response.add(sa);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * 分页展示用户
     *
     * @param params
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity showAllAccount(@RequestParam Map<String, Object> params) {
        if (params.get("pageSize") == null || params.get("pageNum") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }

        int pageSize = Integer.parseInt((String) params.get("pageSize"));
        int pageNum = Integer.parseInt((String) params.get("pageNum"));

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        Page page = new Page<>(pageNum, pageSize);
        IPage<Account> mapIPage = accountMapper.selectPage(page, queryWrapper);
        List<Account> followList = mapIPage.getRecords();

        AccountResponse<Account> accountResponse = new AccountResponse<>();
        accountResponse.setPageNum(pageNum);
        accountResponse.setPageNum(pageSize);
        accountResponse.setTotalPage(mapIPage.getTotal());
        accountResponse.setList(followList);

        return ResponseEntity.ok().body(accountResponse);
    }

    /**
     * 上传用户头像，路径为与项目文件同级的images文件夹
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/avatar")
    public ResponseEntity postAvatar(MultipartFile file, @CookieValue("id") String id) throws IOException {
        Long accountId = Long.valueOf(id);
        if (Objects.isNull(file) || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String pic = Consts.FILE_ROOT+"/images/accounts/avatar/" + accountId + "_" + suffix;
        String name = accountId + "_" + suffix;

        Path path = Paths.get(pic);
        if (!Files.isWritable(path)) {
            Files.createDirectories(Paths.get(Consts.FILE_ROOT+"/images/"));
        }
        Files.write(path, bytes);
        Account account = accountMapper.selectById(accountId);
        account.setAvatar("/accounts/avatar/" + name);
        accountMapper.updateById(account);
        return ResponseEntity.ok().body("OK");
    }

    /**
     * 上传用户背景图片，路径为与项目文件同级的images文件夹
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/background")
    public ResponseEntity postBackground(MultipartFile file, @CookieValue("id") String id) throws IOException {
        Long accountId = Long.valueOf(id);
        if (Objects.isNull(file) || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        }
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String pic = Consts.FILE_ROOT+"/accounts/background/" + accountId + "_" + suffix;
        String name = accountId + "_" + suffix;

        Path path = Paths.get(pic);
        if (!Files.isWritable(path)) {
            Files.createDirectories(Paths.get(Consts.FILE_ROOT+"/images/"));
        }
        Files.write(path, bytes);
        Account account = accountMapper.selectById(accountId);
        account.setAvatar("/accounts/background/" + name);
        accountMapper.updateById(account);
        return ResponseEntity.ok().body("OK");
    }
}
