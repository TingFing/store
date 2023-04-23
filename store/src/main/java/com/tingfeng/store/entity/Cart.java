package com.tingfeng.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity implements Serializable {

    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}
