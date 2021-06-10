package us.linkpl.linkplus.entity.response;

import lombok.Data;

@Data
public class AccountInfo {

    private Long id;

    private String nickname;

    private Integer displayNumber;

    private String background;

    private String avatar;

    private String slogan;

    private int fans;

    private int follows;
}
