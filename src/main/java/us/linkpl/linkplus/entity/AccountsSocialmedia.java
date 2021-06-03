package us.linkpl.linkplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accounts_socialMedia")
public class AccountsSocialmedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("accountId")
    private Integer accountid;

    @TableField("socialMediaId")
    private Integer socialmediaid;

    private String content;

    private Boolean enable;


}
