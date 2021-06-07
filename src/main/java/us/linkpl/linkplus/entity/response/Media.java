package us.linkpl.linkplus.entity.response;

import lombok.Data;

@Data
public class Media {

    Long Id;
    Long mediaId;
    String mediaName;
    String content;
    String logoUrl;
}
