package myhome.accountbook.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Result<T> {

    T body;
    int total;


    public Result(T body) {
        this.body = body;
        this.total = 0;
    }
}
