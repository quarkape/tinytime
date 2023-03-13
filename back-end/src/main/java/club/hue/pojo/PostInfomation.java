package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfomation {

    private String postid;

    private String clientid;

    private int view;

    private int agree;

    private int collect;

    private String date;

}
