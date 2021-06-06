package us.linkpl.linkplus.entity.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountResponse<T> {

    int pageSize;
    int pageNum;
    long totalPage;
    List<T> list;

    public AccountResponse() {
        list = new ArrayList<>();
    }
}
