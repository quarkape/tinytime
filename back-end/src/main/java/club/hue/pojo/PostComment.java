package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostComment {

    private int type;

    private String detail;

    private String quote;

    private String date;

    private String name;

    private String mark;

    private String avatarurl;
}
