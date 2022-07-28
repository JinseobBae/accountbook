package myhome.accountbook.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@IdClass(CategoryKeySet.class)
public class Category implements Serializable {

    @Id
    String type;

    @Id
    String code;

    @Id
    String subCode;

    String name;

    String subName;
}
