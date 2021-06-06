package us.linkpl.linkplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.entity.Follow;
import us.linkpl.linkplus.entity.response.AccountResponse;
import us.linkpl.linkplus.entity.response.SimpleAccount;
import us.linkpl.linkplus.mapper.AccountMapper;
import us.linkpl.linkplus.mapper.FollowMapper;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    FollowMapper followMapper;

    @Autowired
    AccountMapper accountMapper;

    /**
     * 关注
     *
     * @param id      关注的id
     * @param session
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<String> followById(@PathVariable("id") Long id, HttpSession session) {

        Account account = accountMapper.selectById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Long accountId = (Long) session.getAttribute("accountId");
        Follow follow = new Follow();
        follow.setAccountId(accountId.intValue());
        follow.setFollowId(id.intValue());
        followMapper.insert(follow);

        return ResponseEntity.ok().build();
    }

    /**
     * 分页获取关注列表
     *
     * @param params
     * @param session
     * @return
     */
    @GetMapping("")
    public ResponseEntity<AccountResponse> followList(@RequestParam Map<String, String> params, HttpSession session) {
        if (params.get("pageSize") == null || params.get("pageNum") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        int pageSize = Integer.parseInt(params.get("pageSize"));
        int pageNum =  Integer.parseInt(params.get("pageNum"));
        Long accountId = (Long) session.getAttribute("accountId");

        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accountId", accountId);
        Page page = new Page<>(pageNum, pageSize);
        IPage<Follow> mapIPage = followMapper.selectPage(page, queryWrapper);

        AccountResponse<SimpleAccount> accountResponse = new AccountResponse<>();
        accountResponse.setPageNum(pageNum);
        accountResponse.setPageSize(pageSize);
        accountResponse.setTotalPage(mapIPage.getTotal());

        List<Follow> followList = mapIPage.getRecords();
        for (Follow follow : followList) {
            Account account = accountMapper.selectById(follow.getFollowId());
            SimpleAccount simpleAccount = new SimpleAccount();
            simpleAccount.setId(account.getId());
            simpleAccount.setAvatar(account.getAvatar());
            simpleAccount.setNickname(account.getNickname());
            accountResponse.getList().add(simpleAccount);
        }

        return ResponseEntity.ok().body(accountResponse);
    }
}
