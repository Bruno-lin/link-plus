package us.linkpl.linkplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.entity.AccountSocialmedia;
import us.linkpl.linkplus.entity.Follow;
import us.linkpl.linkplus.entity.SocialMedia;
import us.linkpl.linkplus.entity.response.AccountInfo;
import us.linkpl.linkplus.entity.response.AccountPage;
import us.linkpl.linkplus.entity.response.Media;
import us.linkpl.linkplus.mapper.AccountMapper;
import us.linkpl.linkplus.mapper.AccountSocialmediaMapper;
import us.linkpl.linkplus.mapper.FollowMapper;
import us.linkpl.linkplus.mapper.SocialMediaMapper;
import us.linkpl.linkplus.service.impl.AccountSocialmediaServiceImpl;
import us.linkpl.linkplus.service.impl.FollowServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin
public class SearchController {

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
     * 查询用户信息
     *
     * @return
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<AccountPage> getAccountById(@PathVariable("id") Long id) {
        Account account = accountMapper.selectById(id);

        AccountInfo accountInfo = new AccountInfo();
        BeanUtils.copyProperties(account, accountInfo);

        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("followId", id);
        int fans = followService.count(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountId", id);
        int follows = followService.count(queryWrapper);
        accountInfo.setFans(fans);
        accountInfo.setFollows(follows);

        List<Media> medias = new ArrayList<>();
        QueryWrapper<AccountSocialmedia> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("accountId", id);
        List<AccountSocialmedia> accountSocialmedia = accountSocialmediaMapper.selectList(queryWrapper1);
        for (AccountSocialmedia a : accountSocialmedia) {
            Media media = new Media();
            int socialMediaId = a.getSocialMediaId();
            SocialMedia socialMedia = socialMediaMapper.selectById(socialMediaId);
            media.setId(a.getId());
            media.setContent(a.getContent());
            media.setMediaName(socialMedia.getName());
            media.setLogoUrl(socialMedia.getLogoUrl());
            medias.add(media);
        }

        AccountPage accountPage = new AccountPage();
        accountPage.setAccountInfo(accountInfo);
        accountPage.setMedias(medias);
        return ResponseEntity.ok(accountPage);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<AccountPage> getAccountByName(@PathVariable("name") String nickname) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("nickname", nickname + "%");
    }
}
