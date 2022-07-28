package myhome.accountbook.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryKeySet implements Serializable {

    String type;
    String code;
    String subCode;
}
