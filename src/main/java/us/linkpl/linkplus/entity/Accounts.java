package us.linkpl.linkplus.entity;

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
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    @TableField("secretKey")
    private String secretkey;

    private String nickname;

    @TableField("displayNumber")
    private Integer displaynumber;

    private String background;

    private String avatar;


}
