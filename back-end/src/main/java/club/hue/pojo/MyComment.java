package club.hue.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyComment {

    private String postid;

    private String type;

    private String detail;

    private String date;

    private String title;

}
