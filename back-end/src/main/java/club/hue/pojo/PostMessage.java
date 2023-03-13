package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMessage {

    private String postid;

    private String messageid;

    private String rolea;

    private String roleb;

    private String type;

    private int status;

    private String date;

    private String title;

    private String name;

}
