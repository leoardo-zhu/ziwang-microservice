package com.ziwang.resource.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_type")
@Data
@NoArgsConstructor
public class Type {

    @Id
    private Integer id;

    @Column(name = "table_1_id")
    private Integer type1Id;

    @Column(name = "table_1_name")
    private String type1Name;

    @Column(name = "table_2_id")
    private Integer type2Id;

    @Column(name = "table_2_name")
    private String type2Name;

    public Type(Integer type1Id, String type1Name) {
        this.type1Id = type1Id;
        this.type1Name = type1Name;
    }
}
