package us.linkpl.linkplus.entity.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class FollowResponse {

    int pageSize;
    int pageNum;
    long totalPage;
    List<SimpleAccount> follows;

    public FollowResponse() {
        follows = new ArrayList<>();
    }
}
