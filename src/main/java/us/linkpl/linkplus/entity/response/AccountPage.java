package us.linkpl.linkplus.entity.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountPage {

    AccountInfo accountInfo;
    List<Media> medias;
}
