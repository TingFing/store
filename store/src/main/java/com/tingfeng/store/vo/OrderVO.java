package com.tingfeng.store.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private Integer id;
    private  Integer oid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String recvPhone;
    private Integer status;
    private Date orderTime;
    private String recvName;
    private String formatTime;
    private String image;
    private String title;
    private Integer isDelete;
}
