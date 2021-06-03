package us.linkpl.linkplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("account_socialMedia")
public class AccountSocialmedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer accountId;

    private Integer socialMediaId;

    private String content;

    private Boolean enable;


}
