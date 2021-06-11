package us.linkpl.linkplus.entity.response;

import lombok.Data;

@Data
public class Media {

    Long id;
    Long mediaId;
    String mediaName;
    String content;
    String logoUrl;
}
