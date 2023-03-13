package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertainPost {

    private String postid;

    private String authorid;

    private String type;

    private int anonymous;

    private String title;

    private int imgtype;

    private String imgurl;

    private String date;

    private String content;

    private int view;

    private int agree;

    private int collect;

    private int comment;

    private String name;

    private String mark;

    private String avatarurl;

}
