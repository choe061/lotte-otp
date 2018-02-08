package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 2. 8. PM 10:22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUserVO {
    private int b_id;
    private String id;
    private String ip;
    private int count;
    private String block_date;

    public BlockUserVO(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public BlockUserVO(String id, String ip, int count) {
        this.id = id;
        this.ip = ip;
        this.count = count;
    }

    public BlockUserVO(String id, String ip, String block_date) {
        this.id = id;
        this.ip = ip;
        this.block_date = block_date;
    }

    public BlockUserVO(int b_id, String id, String ip, String block_date) {
        this.b_id = b_id;
        this.id = id;
        this.ip = ip;
        this.block_date = block_date;
    }
}
