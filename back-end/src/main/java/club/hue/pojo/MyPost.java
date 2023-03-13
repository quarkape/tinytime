package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPost {

    private String postid;

    private String authorid;

    private String type;

    private int anonymous;

    private String title;

    private int imgtype;

    private String imgurl;

    private String date;

    private String content;

}
