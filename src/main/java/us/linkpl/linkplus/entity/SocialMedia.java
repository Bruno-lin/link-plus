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
public class SocialMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    @TableField("logoUrl")
    private String logourl;


}
