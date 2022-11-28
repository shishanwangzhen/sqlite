package com.corewell.sqlite.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 863586395
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName
public class Doatable {

  private String stime;
  private Double dovalue;
  private Double satvalue;
  private Double tempvalue;
  private Double phvalue;
}
