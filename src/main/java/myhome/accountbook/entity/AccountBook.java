package myhome.accountbook.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private Long totalAmount;

    private String bank;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accountBook")
//    private List<Content> contentList;

    @Transient
    public void plusAmount(long amount){
        this.totalAmount += amount;
    }

    @Transient
    public void minusAmount(long amount){
        this.totalAmount -= amount;
    }

}
